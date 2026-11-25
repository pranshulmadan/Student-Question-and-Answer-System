import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Talks to QuestionServer for reviews. Has the same public methods as
 * ReviewManager, so the GUIs can use either one.
 */
public class ReviewClient {

    private final String host;
    private final int port;

    public ReviewClient() {

        this(
                QuestionClient.DEFAULT_HOST,
                QuestionClient.DEFAULT_PORT
        );
    }

    public ReviewClient(String host, int port) {

        this.host = host;
        this.port = port;
    }

    /*
     * CONNECT TO SERVER
     */

    private Socket connect() throws IOException {

        Socket socket = new Socket();

        try {

            socket.connect(
                    new InetSocketAddress(host, port),
                    5000
            );

            socket.setSoTimeout(5000);

            return socket;

        } catch (IOException e) {

            socket.close();

            throw e;
        }
    }

    /*
     * ADD REVIEW REMOTELY
     */

    public Review addReview(
            Answer answer,
            String description,
            String reviewer) {

        if (answer == null
                || description == null
                || description.trim().isEmpty()
                || reviewer == null
                || reviewer.trim().isEmpty()) {

            return null;
        }

        try (
                Socket socket = connect();

                DataOutputStream output =
                        new DataOutputStream(
                                socket.getOutputStream()
                        );

                DataInputStream input =
                        new DataInputStream(
                                socket.getInputStream()
                        )
        ) {

            output.writeUTF("ADD_REVIEW");

            output.writeInt(answer.getAnswerId());
            output.writeUTF(description.trim());
            output.writeUTF(reviewer.trim());

            output.flush();

            boolean success = input.readBoolean();

            if (success) {

                return readReview(input);
            }

            return null;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "submit review to server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * GET REVIEWS FOR ANSWER
     */

    public ArrayList<Review> getReviewsForAnswer(
            Answer answer) {

        if (answer == null) {

            return new ArrayList<Review>();
        }

        return requestReviews(
                "GET_REVIEWS_FOR_ANSWER",
                answer.getAnswerId()
        );
    }

    /*
     * GET ALL REVIEWS
     */

    public ArrayList<Review> getReviews() {

        return requestReviews(
                "GET_REVIEWS",
                -1
        );
    }

    /*
     * REQUEST REVIEWS FROM SERVER
     */

    private ArrayList<Review> requestReviews(
            String command,
            int answerId) {

        try (
                Socket socket = connect();

                DataOutputStream output =
                        new DataOutputStream(
                                socket.getOutputStream()
                        );

                DataInputStream input =
                        new DataInputStream(
                                socket.getInputStream()
                        )
        ) {

            output.writeUTF(command);

            if (answerId >= 0) {

                output.writeInt(answerId);
            }

            output.flush();

            int count = input.readInt();

            ArrayList<Review> reviews =
                    new ArrayList<Review>();

            for (int i = 0; i < count; i++) {

                reviews.add(
                        readReview(input)
                );
            }

            return reviews;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "retrieve reviews from server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * READ REVIEW FROM SERVER
     */

    private Review readReview(
            DataInputStream input)
            throws IOException {

        return new Review(
                input.readInt(),
                input.readInt(),
                input.readUTF(),
                input.readUTF()
        );
    }
}
