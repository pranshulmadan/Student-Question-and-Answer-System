
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