
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

    public static void main(String[] args) {

        UserManager userManager = new UserManager();

        showLogin(userManager);
    }

    public static void showLogin(UserManager userManager) {

        JFrame frame = new JFrame(
                "Student Question and Answer System"
        );

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField(15);
        panel.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        // Register button
        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        // Message label
        JLabel messageLabel = new JLabel("");
        panel.add(messageLabel);

        // REGISTER BUTTON
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField newUsername = new JTextField();
                JPasswordField newPassword =
                        new JPasswordField();

                Object[] fields = {
                        "Username:", newUsername,
                        "Password:", newPassword
                };

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        fields,
                        "Register Account",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (result == JOptionPane.OK_OPTION) {

                    String username = newUsername.getText();
                    String password =
                            new String(newPassword.getPassword());

                    User newUser = userManager.registerUser(
                            username,
                            password
                    );

                    if (newUser != null) {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Account created!\n"
                                + "Assigned role: "
                                + newUser.getRoles()
                                + "\nPlease log in."
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Registration failed.\n"
                                + "Check your username and password."
                        );
                    }
                }
            }
        });

        // LOGIN BUTTON
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();

                String password =
                        new String(passwordField.getPassword());

                User user = userManager.login(
                        username,
                        password
                );

                if (user == null) {

                    messageLabel.setText(
                            "Invalid username or password."
                    );

                    return;
                }

                // Get all roles assigned to the user
                java.util.ArrayList<Role> roles =
                        user.getRoles();

                Role selectedRole;

                // Automatically select role if user has one
                if (roles.size() == 1) {

                    selectedRole = roles.get(0);

                } else if (roles.size() > 1) {

                    // Let user choose their role
                    selectedRole = (Role)
                            JOptionPane.showInputDialog(
                                    frame,
                                    "Select your role:",
                                    "Role Selection",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    roles.toArray(),
                                    roles.get(0)
                            );

                    if (selectedRole == null) {
                        return;
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            frame,
                            "No role assigned to this account."
                    );

                    return;
                }

                // Show selected role
                JOptionPane.showMessageDialog(
                        frame,
                        "Welcome, " + user.getUsername()
                        + "!\nYou are logged in as: "
                        + selectedRole
                );

                // Open temporary role home screen
                showHomeScreen(
                        user,
                        selectedRole,
                        userManager,
                        frame
                );
            }
        });

        frame.setVisible(true);
    }

    // Temporary home screen for testing roles
    private static void showHomeScreen(
            User user,
            Role role,
            UserManager userManager,
            JFrame loginFrame) {

        loginFrame.setVisible(false);

        JFrame homeFrame = new JFrame(role + " Home");

        homeFrame.setSize(400, 300);
        homeFrame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        homeFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        homeFrame.add(panel);

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + user.getUsername()
                + " | Role: " + role
        );

        panel.add(welcomeLabel);

        // Admin-only role assignment
        if (role == Role.ADMIN) {

            JButton assignRoleButton =
                    new JButton("Assign User Role");

            panel.add(assignRoleButton);

            assignRoleButton.addActionListener(
                    new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    String username =
                            JOptionPane.showInputDialog(
                                    homeFrame,
                                    "Enter the username:"
                            );

                    if (username == null
                            || username.trim().isEmpty()) {
                        return;
                    }

                    Role selectedRole = (Role)
                            JOptionPane.showInputDialog(
                                    homeFrame,
                                    "Select a role to assign:",
                                    "Assign Role",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    Role.values(),
                                    Role.STUDENT
                            );

                    if (selectedRole == null) {
                        return;
                    }

                    boolean success =
                            userManager.addRoleToUser(
                                    username,
                                    selectedRole
                            );

                    if (success) {

                        JOptionPane.showMessageDialog(
                                homeFrame,
                                "Role assigned successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                homeFrame,
                                "User not found."
                        );
                    }
                }
            });

            
JButton removeUserButton = new JButton("Remove User");

JButton removeRoleButton = new JButton("Remove Role");

panel.add(removeRoleButton);

removeRoleButton.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {

        // Get all registered users
        java.util.ArrayList<User> users =
                userManager.getUsers();

        String[] usernames = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            usernames[i] = users.get(i).getUsername();
        }

        // Select the user
        String selectedUsername = (String)
                JOptionPane.showInputDialog(
                        homeFrame,
                        "Select a user:",
                        "Remove Role",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        usernames,
                        usernames[0]
                );

        if (selectedUsername == null) {
            return;
        }

        // Find the selected user
        User selectedUser = null;

        for (User currentUser : users) {

            if (currentUser.getUsername()
                    .equals(selectedUsername)) {

                selectedUser = currentUser;
                break;
            }
        }

        if (selectedUser == null) {
            return;
        }

        // Display only roles the user currently has
        java.util.ArrayList<Role> roles =
                selectedUser.getRoles();

        if (roles.isEmpty()) {

            JOptionPane.showMessageDialog(
                    homeFrame,
                    "This user has no assigned roles."
            );

            return;
        }

        Role selectedRole = (Role)
                JOptionPane.showInputDialog(
                        homeFrame,
                        "Select a role to remove:",
                        "Remove Role",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        roles.toArray(),
                        roles.get(0)
                );

        if (selectedRole == null) {
            return;
        }

        // Confirm role removal
        int confirm = JOptionPane.showConfirmDialog(
                homeFrame,
                "Remove " + selectedRole
                        + " from " + selectedUsername + "?",
                "Confirm Role Removal",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = userManager.removeRoleFromUser(
                user,
                selectedUsername,
                selectedRole
        );

        if (success) {

            JOptionPane.showMessageDialog(
                    homeFrame,
                    "Role removed successfully!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    homeFrame,
                    "Unable to remove role.\n"
                    + "A user must have at least one role, "
                    + "and the last ADMIN role "
                    + "cannot be removed."
            );
        }
    }
});
panel.add(removeUserButton);

removeUserButton.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {

        // Get the usernames of all registered users
        java.util.ArrayList<User> users =
                userManager.getUsers();

        String[] usernames = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            usernames[i] = users.get(i).getUsername();
        }

        // Let the admin select an account
        String selectedUsername = (String)
                JOptionPane.showInputDialog(
                        homeFrame,
                        "Select a user to remove:",
                        "Remove User",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        usernames,
                        usernames[0]
                );

        if (selectedUsername == null) {
            return;
        }

        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(
                homeFrame,
                "Are you sure you want to remove "
                        + selectedUsername + "?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = userManager.removeUser(
                user,
                selectedUsername
        );

        if (success) {

            JOptionPane.showMessageDialog(
                    homeFrame,
                    "User removed successfully!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    homeFrame,
                    "Unable to remove user.\n"
                    + "You cannot delete your own account "
                    + "or the last administrator."
            );
        }
    }
});
        }

        // Logout
        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                homeFrame.dispose();

                loginFrame.setVisible(true);
            }
        });

        homeFrame.setVisible(true);
    }
}