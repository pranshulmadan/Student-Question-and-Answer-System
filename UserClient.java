import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Admin user management through QuestionServer: viewing users,
 * assigning/removing roles, removing users and creating invitations.
 *
 * Also handles login and registration: accounts and passwords live
 * on the server. When an admin logs in, the server hands out a session token
 * that the admin requests must carry; it is kept until logout.
 */
public class UserClient {

    /** Thrown when the server rejects the session token. */
    public static class NotAuthorizedException
            extends RuntimeException {

        public NotAuthorizedException() {

            super("The server rejected this admin session.");
        }
    }

    public enum LoginStatus {
        OK,
        NO_SUCH_USER,
        WRONG_PASSWORD
    }

    /** Result of login(). user is set only when status is OK. */
    public static class LoginResult {

        public final LoginStatus status;
        public final User user;

        LoginResult(LoginStatus status, User user) {

            this.status = status;
            this.user = user;
        }
    }

    public enum RegisterStatus {
        OK,
        INVALID_INVITATION,
        FAILED
    }

    /** Result of register(). user is set only when status is OK. */
    public static class RegisterResult {

        public final RegisterStatus status;
        public final User user;

        RegisterResult(RegisterStatus status, User user) {

            this.status = status;
            this.user = user;
        }
    }

    private static volatile String sessionToken;
    private static volatile String sessionUsername;

    private final String host;
    private final int port;

    public UserClient() {

        this(
                QuestionClient.DEFAULT_HOST,
                QuestionClient.DEFAULT_PORT
        );
    }

    public UserClient(String host, int port) {

        this.host = host;
        this.port = port;
    }

    /*
     * SESSION
     */

    /*
     * Checks the username and password against the server's users.
     * The returned user carries username and roles only. For admins
     * this also starts the admin session used by the methods below.
     */
    public LoginResult login(String username, String password) {

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

            output.writeUTF("LOGIN");
            output.writeUTF(username);
            output.writeUTF(password);
            output.flush();

            int status = input.readByte();

            if (status == 1) {

                return new LoginResult(
                        LoginStatus.NO_SUCH_USER,
                        null
                );
            }

            if (status != 0) {

                return new LoginResult(
                        LoginStatus.WRONG_PASSWORD,
                        null
                );
            }

            String name = input.readUTF();

            int roleCount = input.readInt();

            ArrayList<Role> roles =
                    new ArrayList<Role>();

            for (int i = 0; i < roleCount; i++) {

                roles.add(
                        Role.valueOf(input.readUTF())
                );
            }

            if (input.readBoolean()) {

                sessionToken = input.readUTF();
                sessionUsername = name;
            }

            return new LoginResult(
                    LoginStatus.OK,
                    User.fromSavedData(name, "", roles)
            );

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "log in to server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * REGISTER
     *
     * Creates an account on the server. Pass an empty invitation code
     * for a normal registration; with a code, the account gets the
     * invitation's roles and the code is used up.
     */

    public RegisterResult register(
            String username,
            String password,
            String invitationCode) {

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

            output.writeUTF("REGISTER");
            output.writeUTF(username);
            output.writeUTF(password);
            output.writeUTF(
                    invitationCode == null ? "" : invitationCode
            );
            output.flush();

            int status = input.readByte();

            if (status == 1) {

                return new RegisterResult(
                        RegisterStatus.INVALID_INVITATION,
                        null
                );
            }

            if (status != 0) {

                return new RegisterResult(
                        RegisterStatus.FAILED,
                        null
                );
            }

            String name = input.readUTF();

            int roleCount = input.readInt();

            ArrayList<Role> roles =
                    new ArrayList<Role>();

            for (int i = 0; i < roleCount; i++) {

                roles.add(
                        Role.valueOf(input.readUTF())
                );
            }

            return new RegisterResult(
                    RegisterStatus.OK,
                    User.fromSavedData(name, "", roles)
            );

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "register on server",
                    host,
                    port,
                    e
            );
        }
    }

    public static boolean isSignedIn(String username) {

        return sessionToken != null
                && username != null
                && username.equals(sessionUsername);
    }

    public static void signOut() {

        sessionToken = null;
        sessionUsername = null;
    }

    /*
     * GET USERS
     *
     * Returned users carry usernames and roles only (no password data).
     */

    public ArrayList<User> getUsers() {

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

            output.writeUTF("GET_USERS");
            output.writeUTF(requireToken());
            output.flush();

            checkAuthorized(input);

            int count = input.readInt();

            ArrayList<User> users =
                    new ArrayList<User>();

            for (int i = 0; i < count; i++) {

                String username = input.readUTF();

                int roleCount = input.readInt();

                ArrayList<Role> roles =
                        new ArrayList<Role>();

                for (int j = 0; j < roleCount; j++) {

                    roles.add(
                            Role.valueOf(input.readUTF())
                    );
                }

                users.add(
                        User.fromSavedData(
                                username,
                                "",
                                roles
                        )
                );
            }

            return users;

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "retrieve users from server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * ASSIGN / REMOVE ROLE
     */

    public boolean addRoleToUser(String username, Role role) {

        return changeRole("ASSIGN_ROLE", username, role);
    }

    public boolean removeRoleFromUser(String username, Role role) {

        return changeRole("REMOVE_ROLE", username, role);
    }

    private boolean changeRole(
            String command,
            String username,
            Role role) {

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
            output.writeUTF(requireToken());
            output.writeUTF(username);
            output.writeUTF(role.name());
            output.flush();

            checkAuthorized(input);

            return input.readBoolean();

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "update roles on server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * CREATE INVITATION (admin)
     *
     * Returns the new invitation, or null if the server refused it
     * (no roles, or a deadline in the past).
     */

    public Invitation createInvitation(
            ArrayList<Role> roles,
            long expirationTime) {

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

            output.writeUTF("CREATE_INVITATION");
            output.writeUTF(requireToken());

            output.writeInt(roles.size());

            for (Role role : roles) {

                output.writeUTF(role.name());
            }

            output.writeLong(expirationTime);
            output.flush();

            checkAuthorized(input);

            if (!input.readBoolean()) {

                return null;
            }

            return new Invitation(
                    input.readUTF(),
                    roles,
                    expirationTime
            );

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "create invitation on server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * REMOVE USER
     */

    public boolean removeUser(String username) {

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

            output.writeUTF("REMOVE_USER");
            output.writeUTF(requireToken());
            output.writeUTF(username);
            output.flush();

            checkAuthorized(input);

            return input.readBoolean();

        } catch (IOException e) {

            throw new ServerUnavailableException(
                    "remove user on server",
                    host,
                    port,
                    e
            );
        }
    }

    /*
     * HELPERS
     */

    private String requireToken() {

        String token = sessionToken;

        if (token == null) {

            throw new NotAuthorizedException();
        }

        return token;
    }

    // Server restarted or the account lost ADMIN: drop the session.
    private void checkAuthorized(DataInputStream input)
            throws IOException {

        if (!input.readBoolean()) {

            signOut();

            throw new NotAuthorizedException();
        }
    }

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
}
