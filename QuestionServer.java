import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class QuestionServer {

    private final int port;

    private final QuestionManager questionManager;

    private final ReviewManager reviewManager;

    private final UserManager userManager;

    private final InvitationManager invitationManager;

    // Admin session token -> username. Cleared when the server restarts.
    private final HashMap<String, String> adminSessions =
            new HashMap<String, String>();

    private final SecureRandom random = new SecureRandom();

    public QuestionServer() {

        this(QuestionClient.DEFAULT_PORT);
    }

    public QuestionServer(int port) {

        this.port = port;

        questionManager = new QuestionManager();

        reviewManager = new ReviewManager();

        userManager = new UserManager();

        invitationManager = new InvitationManager();
    }

    public void start() throws IOException {

        try (ServerSocket serverSocket =
                new ServerSocket(
                        port,
                        50,
                        InetAddress.getByName("0.0.0.0")
                )) {

            System.out.println(
                    "Question Server is running!"
            );

            System.out.println(
                    "Listening on port " + port
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

                case "ADD_REVIEW":

                    int reviewAnswerId =
                            input.readInt();

                    String reviewDescription =
                            input.readUTF();

                    String reviewer =
                            input.readUTF();

                    Answer reviewedAnswer =
                            findAnswer(reviewAnswerId);

                    Review newReview = null;

                    if (reviewedAnswer != null) {

                        newReview =
                                reviewManager.addReview(
                                        reviewedAnswer,
                                        reviewDescription,
                                        reviewer
                                );
                    }

                    output.writeBoolean(
                            newReview != null
                    );

                    if (newReview != null) {

                        writeReview(output, newReview);
                    }

                    break;

                case "GET_REVIEWS":

                    writeReviews(
                            output,
                            reviewManager.getReviews()
                    );

                    break;

                case "GET_REVIEWS_FOR_ANSWER":

                    int lookupAnswerId =
                            input.readInt();

                    ArrayList<Review> answerReviews =
                            new ArrayList<Review>();

                    for (Review review :
                            reviewManager.getReviews()) {

                        if (review.getAnswerId()
                                == lookupAnswerId) {

                            answerReviews.add(review);
                        }
                    }

                    writeReviews(
                            output,
                            answerReviews
                    );

                    break;

                /*
                 * ADMIN USER MANAGEMENT
                 *
                 * Registration, invitations and user removal still
                 * write users.txt locally, so reload before every
                 * user command to avoid overwriting those changes.
                 */

                /*
                 * LOGIN
                 *
                 * Replies with a status byte (UserClient.LoginStatus):
                 * 0 = OK, 1 = no such user, 2 = wrong password.
                 * On OK: username, roles, and for admins a session
                 * token for the admin commands below.
                 */

                case "LOGIN":

                    String loginName = input.readUTF();
                    String loginPassword = input.readUTF();

                    userManager.reload();

                    User account = null;

                    // Exact match, same rule as UserManager.login.
                    for (User candidate : userManager.getUsers()) {

                        if (candidate.getUsername().equals(loginName)) {

                            account = candidate;
                            break;
                        }
                    }

                    if (account == null) {

                        output.writeByte(1);

                    } else if (!account.checkPassword(loginPassword)) {

                        output.writeByte(2);

                    } else {

                        output.writeByte(0);

                        output.writeUTF(account.getUsername());

                        ArrayList<Role> accountRoles =
                                account.getRoles();

                        output.writeInt(accountRoles.size());

                        for (Role role : accountRoles) {

                            output.writeUTF(role.name());
                        }

                        boolean isAdmin =
                                account.hasRole(Role.ADMIN);

                        output.writeBoolean(isAdmin);

                        if (isAdmin) {

                            String token = newSessionToken();

                            adminSessions.put(
                                    token,
                                    account.getUsername()
                            );

                            output.writeUTF(token);
                        }
                    }

                    break;

                /*
                 * REGISTER
                 *
                 * Open to anyone. An empty invitation code means a
                 * normal (STUDENT) registration. Replies with a status
                 * byte (UserClient.RegisterStatus): 0 = OK, then the
                 * username and roles; 1 = invalid/expired/used
                 * invitation; 2 = username taken or invalid input.
                 */

                case "REGISTER":

                    String newName = input.readUTF();
                    String newPassword = input.readUTF();
                    String inviteCode = input.readUTF().trim();

                    userManager.reload();
                    invitationManager.reload();

                    User registered = null;

                    if (!inviteCode.isEmpty()) {

                        Invitation invitation =
                                invitationManager.validateInvitation(
                                        inviteCode
                                );

                        if (invitation == null) {

                            output.writeByte(1);

                            break;
                        }

                        registered =
                                userManager.registerInvitedUser(
                                        newName,
                                        newPassword,
                                        invitation.getRoles()
                                );

                        // Only use up the code once the account exists.
                        if (registered != null) {

                            invitationManager.useInvitation(
                                    inviteCode
                            );
                        }

                    } else {

                        registered =
                                userManager.registerUser(
                                        newName,
                                        newPassword
                                );
                    }

                    if (registered == null) {

                        output.writeByte(2);

                        break;
                    }

                    output.writeByte(0);

                    output.writeUTF(registered.getUsername());

                    writeRoles(output, registered.getRoles());

                    break;

                case "CREATE_INVITATION":

                    User invitingAdmin =
                            authorizeAdmin(input.readUTF());

                    ArrayList<Role> inviteRoles =
                            readRoles(input);

                    long expiration = input.readLong();

                    output.writeBoolean(invitingAdmin != null);

                    if (invitingAdmin != null) {

                        invitationManager.reload();

                        Invitation created =
                                invitationManager.createInvitation(
                                        inviteRoles,
                                        expiration
                                );

                        output.writeBoolean(created != null);

                        if (created != null) {

                            output.writeUTF(created.getCode());
                        }
                    }

                    break;

                case "GET_USERS":

                    User viewingAdmin =
                            authorizeAdmin(input.readUTF());

                    output.writeBoolean(viewingAdmin != null);

                    if (viewingAdmin != null) {

                        writeUsers(
                                output,
                                userManager.getUsers()
                        );
                    }

                    break;

                case "ASSIGN_ROLE":
                case "REMOVE_ROLE":

                    User actingAdmin =
                            authorizeAdmin(input.readUTF());

                    String targetUser = input.readUTF();

                    Role targetRole =
                            Role.valueOf(input.readUTF());

                    output.writeBoolean(actingAdmin != null);

                    if (actingAdmin != null) {

                        boolean changed =
                                command.equals("ASSIGN_ROLE")
                                ? userManager.addRoleToUser(
                                        targetUser,
                                        targetRole
                                )
                                : userManager.removeRoleFromUser(
                                        actingAdmin,
                                        targetUser,
                                        targetRole
                                );

                        output.writeBoolean(changed);
                    }

                    break;

                case "REMOVE_USER":

                    User removingAdmin =
                            authorizeAdmin(input.readUTF());

                    String removedUser = input.readUTF();

                    output.writeBoolean(removingAdmin != null);

                    if (removingAdmin != null) {

                        // UserManager blocks removing yourself
                        // or the last administrator.
                        output.writeBoolean(
                                userManager.removeUser(
                                        removingAdmin,
                                        removedUser
                                )
                        );
                    }

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
     * ADMIN SESSIONS
     */

    private String newSessionToken() {

        byte[] bytes = new byte[24];

        random.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    // Returns the admin for a valid token, reloading users.txt first.
    // Null if the token is unknown or the account is no longer an admin.
    private User authorizeAdmin(String token) {

        userManager.reload();

        String username = adminSessions.get(token);

        if (username == null) {

            return null;
        }

        for (User user : userManager.getUsers()) {

            if (user.getUsername().equals(username)
                    && user.hasRole(Role.ADMIN)) {

                return user;
            }
        }

        adminSessions.remove(token);

        return null;
    }

    private void writeRoles(
            DataOutputStream output,
            ArrayList<Role> roles)
            throws IOException {

        output.writeInt(roles.size());

        for (Role role : roles) {

            output.writeUTF(role.name());
        }
    }

    private ArrayList<Role> readRoles(
            DataInputStream input)
            throws IOException {

        int count = input.readInt();

        ArrayList<Role> roles = new ArrayList<Role>();

        for (int i = 0; i < count; i++) {

            roles.add(Role.valueOf(input.readUTF()));
        }

        return roles;
    }

    // Usernames and roles only: password hashes never leave the server.
    private void writeUsers(
            DataOutputStream output,
            ArrayList<User> users)
            throws IOException {

        output.writeInt(users.size());

        for (User user : users) {

            output.writeUTF(user.getUsername());

            ArrayList<Role> roles = user.getRoles();

            output.writeInt(roles.size());

            for (Role role : roles) {

                output.writeUTF(role.name());
            }
        }
    }

    /*
     * FIND ANSWER BY ID
     */

    private Answer findAnswer(int answerId) {

        for (Question question :
                questionManager.getQuestions()) {

            for (Answer answer :
                    question.getAnswers()) {

                if (answer.getAnswerId() == answerId) {

                    return answer;
                }
            }
        }

        return null;
    }

    /*
     * SEND REVIEWS TO CLIENT
     */

    private void writeReviews(
            DataOutputStream output,
            ArrayList<Review> reviews)
            throws IOException {

        output.writeInt(reviews.size());

        for (Review review : reviews) {

            writeReview(output, review);
        }
    }

    private void writeReview(
            DataOutputStream output,
            Review review)
            throws IOException {

        output.writeInt(review.getReviewId());
        output.writeInt(review.getAnswerId());
        output.writeUTF(review.getDescription());
        output.writeUTF(review.getReviewer());
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

            // Optional: java QuestionServer <port>
            int port =
                    args.length > 0
                    ? Integer.parseInt(args[0])
                    : QuestionClient.DEFAULT_PORT;

            QuestionServer server =
                    new QuestionServer(port);

            server.start();

        } catch (IOException e) {

            System.out.println(
                    "Server error: " + e.getMessage()
            );
        }
    }
}