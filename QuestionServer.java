import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class QuestionServer {

    private static final int PORT = 5000;

    private final QuestionManager questionManager;

    public QuestionServer() {

        questionManager = new QuestionManager();
    }

    public void start() throws IOException {

        try (ServerSocket serverSocket =
                new ServerSocket(
                        PORT,
                        50,
                        InetAddress.getByName("0.0.0.0")
                )) {

            System.out.println(
                    "Question Server is running!"
            );

            System.out.println(
                    "Listening on port " + PORT
            );

            while (true) {

                Socket client = serverSocket.accept();

                System.out.println(
                        "Client connected: "
                        + client.getInetAddress()
                );

                handleClient(client);
            }
        }
    }

    /*
     * HANDLE CLIENT REQUESTS
     */

    private void handleClient(Socket client) {

        try (
                Socket socket = client;

                DataInputStream input =
                        new DataInputStream(
                                socket.getInputStream()
                        );

                DataOutputStream output =
                        new DataOutputStream(
                                socket.getOutputStream()
                        )
        ) {

            socket.setSoTimeout(5000);

            String command = input.readUTF();

            switch (command) {

                case "GET_QUESTIONS":

                    writeQuestions(
                            output,
                            questionManager.getQuestions()
                    );

                    break;

                case "GET_BY_SUBJECT":

                    Subject selectedSubject =
                            Subject.valueOf(input.readUTF());

                    writeQuestions(
                            output,
                            questionManager.getQuestionsBySubject(
                                    selectedSubject
                            )
                    );

                    break;

                case "GET_UNRESOLVED":

                    writeQuestions(
                            output,
                            questionManager.getUnresolvedQuestions()
                    );

                    break;

                case "ADD_QUESTION":

                    String title = input.readUTF();
                    String description = input.readUTF();
                    String author = input.readUTF();

                    Subject subject =
                            Subject.valueOf(input.readUTF());

                    Question newQuestion =
                            questionManager.addQuestion(
                                    title,
                                    description,
                                    author,
                                    subject
                            );

                    output.writeBoolean(
                            newQuestion != null
                    );

                    if (newQuestion != null) {

                        writeQuestion(output, newQuestion);
                    }

                    break;

                case "ADD_ANSWER":

                    int questionId = input.readInt();

                    String answerDescription =
                            input.readUTF();

                    String answerAuthor =
                            input.readUTF();

                    Question question =
                            findQuestion(questionId);

                    Answer newAnswer = null;

                    if (question != null
                            && !question.isResolved()) {

                        newAnswer =
                                questionManager.addAnswer(
                                        question,
                                        answerDescription,
                                        answerAuthor
                                );
                    }

                    output.writeBoolean(
                            newAnswer != null
                    );

                    if (newAnswer != null) {

                        writeAnswer(output, newAnswer);
                    }

                    break;

                case "RESOLVE_QUESTION":

                    int resolveQuestionId =
                            input.readInt();

                    int acceptedAnswerId =
                            input.readInt();

                    String username =
                            input.readUTF();

                    Question selectedQuestion =
                            findQuestion(resolveQuestionId);

                    boolean resolved = false;

                    if (selectedQuestion != null) {

                        for (Answer answer :
                                selectedQuestion.getAnswers()) {

                            if (answer.getAnswerId()
                                    == acceptedAnswerId) {

                                resolved =
                                        questionManager.resolveQuestion(
                                                selectedQuestion,
                                                answer,
                                                username
                                        );

                                break;
                            }
                        }
                    }

                    output.writeBoolean(resolved);

                    break;

                default:

                    output.writeBoolean(false);

                    System.out.println(
                            "Unknown command: " + command
                    );
            }

            output.flush();

            System.out.println(
                    "Processed request: " + command
            );

        } catch (IOException | RuntimeException e) {

            System.out.println(
                    "Client error: " + e.getMessage()
            );
        }
    }

    /*
     * FIND QUESTION BY ID
     */

    private Question findQuestion(int questionId) {

        for (Question question :
                questionManager.getQuestions()) {

            if (question.getQuestionId() == questionId) {

                return question;
            }
        }

        return null;
    }

    /*
     * SEND QUESTIONS TO CLIENT
     */

    private void writeQuestions(
            DataOutputStream output,
            ArrayList<Question> questions)
            throws IOException {

        output.writeInt(questions.size());

        for (Question question : questions) {

            writeQuestion(output, question);
        }
    }

    private void writeQuestion(
            DataOutputStream output,
            Question question)
            throws IOException {

        output.writeInt(question.getQuestionId());
        output.writeUTF(question.getTitle());
        output.writeUTF(question.getDescription());
        output.writeUTF(question.getAuthor());
        output.writeUTF(question.getSubject().name());

        output.writeBoolean(question.isResolved());
        output.writeInt(question.getAcceptedAnswerId());

        ArrayList<Answer> answers = question.getAnswers();

        output.writeInt(answers.size());

        for (Answer answer : answers) {

            writeAnswer(output, answer);
        }
    }

    private void writeAnswer(
            DataOutputStream output,
            Answer answer)
            throws IOException {

        output.writeInt(answer.getAnswerId());
        output.writeUTF(answer.getDescription());
        output.writeUTF(answer.getAuthor());
    }

    /*
     * START SERVER
     */

    public static void main(String[] args) {

        try {

            QuestionServer server =
                    new QuestionServer();

            server.start();

        } catch (IOException e) {

            System.out.println(
                    "Server error: " + e.getMessage()
            );
        }
    }
}