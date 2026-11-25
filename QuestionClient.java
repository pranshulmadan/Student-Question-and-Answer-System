import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class QuestionClient {

    // Change IP to what ever network you are on, if you are on the same network as the server, use the server's local IP address. If you are on a different network, use the server's public IP address.
    // ReviewClient uses these too.
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 5000;

    private final String host;
    private final int port;

    public QuestionClient() {

        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public QuestionClient(String host, int port) {

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
     * GET ALL QUESTIONS
     */

    public ArrayList<Question> getQuestions() {

        return requestQuestions(
                "GET_QUESTIONS",
                null
        );
    }

    /*
     * GET QUESTIONS BY SUBJECT
     */

    public ArrayList<Question> getQuestionsBySubject(
            Subject subject) {

        return requestQuestions(
                "GET_BY_SUBJECT",
                subject
        );
    }

    /*
     * GET UNRESOLVED QUESTIONS
     */

    public ArrayList<Question> getUnresolvedQuestions() {

        return requestQuestions(
                "GET_UNRESOLVED",
                null
        );
    }

    /*
     * REQUEST QUESTIONS FROM SERVER
     */

    private ArrayList<Question> requestQuestions(
            String command,
            Subject subject) {

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

            if (subject != null) {

                output.writeUTF(subject.name());
            }

            output.flush();

            int count = input.readInt();

            ArrayList<Question> questions =
                    new ArrayList<Question>();

            for (int i = 0; i < count; i++) {

                questions.add(
                        readQuestion(input)
                );
            }

            return questions;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "retrieve questions from server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * ADD QUESTION REMOTELY
     */

    public Question addQuestion(
            String title,
            String description,
            String author,
            Subject subject) {

        if (subject == null) {

            subject = Subject.MISCELLANEOUS;
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

            output.writeUTF("ADD_QUESTION");

            output.writeUTF(title);
            output.writeUTF(description);
            output.writeUTF(author);
            output.writeUTF(subject.name());

            output.flush();

            boolean success = input.readBoolean();

            if (success) {

                return readQuestion(input);
            }

            return null;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "submit question to server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * ADD ANSWER REMOTELY
     */

    public Answer addAnswer(
            Question question,
            String description,
            String author) {

        if (question == null) {

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

            output.writeUTF("ADD_ANSWER");

            output.writeInt(question.getQuestionId());
            output.writeUTF(description);
            output.writeUTF(author);

            output.flush();

            boolean success = input.readBoolean();

            if (success) {

                Answer answer = readAnswer(input);

                question.addAnswer(answer);

                return answer;
            }

            return null;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "submit answer to server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * RESOLVE QUESTION REMOTELY
     */

    public boolean resolveQuestion(
            Question question,
            Answer answer,
            String username) {

        if (question == null || answer == null) {

            return false;
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

            output.writeUTF("RESOLVE_QUESTION");

            output.writeInt(question.getQuestionId());
            output.writeInt(answer.getAnswerId());
            output.writeUTF(username);

            output.flush();

            boolean success = input.readBoolean();

            if (success) {

                question.setResolved(true);

                question.setAcceptedAnswerId(
                        answer.getAnswerId()
                );
            }

            return success;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "resolve question on server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * READ QUESTION FROM SERVER
     */

    private Question readQuestion(
            DataInputStream input)
            throws IOException {

        int id = input.readInt();

        String title = input.readUTF();

        String description = input.readUTF();

        String author = input.readUTF();

        Subject subject =
                Subject.valueOf(input.readUTF());

        Question question =
                new Question(
                        id,
                        title,
                        description,
                        author,
                        subject
                );

        question.setResolved(
                input.readBoolean()
        );

        question.setAcceptedAnswerId(
                input.readInt()
        );

        int answerCount = input.readInt();

        for (int i = 0; i < answerCount; i++) {

            question.addAnswer(
                    readAnswer(input)
            );
        }

        return question;
    }

    /*
     * READ ANSWER FROM SERVER
     */

    private Answer readAnswer(
            DataInputStream input)
            throws IOException {

        return new Answer(
                input.readInt(),
                input.readUTF(),
                input.readUTF()
        );
    }

    /*
     * TEST CLIENT CONNECTION
     */

    public static void main(String[] args) {

        try {

            QuestionClient client =
                    new QuestionClient();

            ArrayList<Question> questions =
                    client.getQuestions();

            System.out.println(
                    "Successfully connected to server!"
            );

            System.out.println(
                    "Questions received: "
                    + questions.size()
            );

            for (Question question : questions) {

                System.out.println(
                        "\nQuestion #"
                        + question.getQuestionId()
                );

                System.out.println(
                        "Title: " + question.getTitle()
                );

                System.out.println(
                        "Subject: " + question.getSubject()
                );

                System.out.println(
                        "Author: " + question.getAuthor()
                );

                System.out.println(
                        "Description: "
                        + question.getDescription()
                );

                for (Answer answer :
                        question.getAnswers()) {

                    System.out.println(
                            "Answer: "
                            + answer.getDescription()
                    );

                    System.out.println(
                            "Answered by: "
                            + answer.getAuthor()
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Connection failed: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}