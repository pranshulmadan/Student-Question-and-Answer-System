
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginGUI {

    private static final Color BLUE = new Color(23, 101, 209);
    private static final Color DARK = new Color(20, 44, 82);
    private static final Color GRAY = new Color(96, 114, 139);
    private static final Color BACKGROUND = new Color(244, 247, 252);

    public static void main(String[] args) {

        UserManager userManager = new UserManager();

        SwingUtilities.invokeLater(() -> showLogin(userManager));
    }

    public static void showLogin(UserManager userManager) {

        JFrame frame = new JFrame("Student Q&A System");
        frame.setSize(450, 570);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND);
        frame.add(mainPanel);

        // Centered login card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 35, 30, 35));
        card.setPreferredSize(new Dimension(370, 465));

        mainPanel.add(card);

        // Book icon
        JLabel iconLabel = new JLabel("\uD83D\uDCD6");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(iconLabel);

        card.add(Box.createVerticalStrut(10));

        // Title
        JLabel title = new JLabel("Student Q&A System");
        title.setFont(new Font("SansSerif", Font.BOLD, 23));
        title.setForeground(DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        card.add(Box.createVerticalStrut(8));

        JLabel subtitle = new JLabel("Sign in to continue");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitle);

        card.add(Box.createVerticalStrut(30));

        // Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        usernameLabel.setForeground(DARK);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(usernameLabel);

        card.add(Box.createVerticalStrut(6));

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 38));
        usernameField.setPreferredSize(new Dimension(300, 38));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(usernameField);

        card.add(Box.createVerticalStrut(18));

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passwordLabel.setForeground(DARK);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passwordLabel);

        card.add(Box.createVerticalStrut(6));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 38));
        passwordField.setPreferredSize(new Dimension(300, 38));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passwordField);

        card.add(Box.createVerticalStrut(25));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(BLUE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setMaximumSize(new Dimension(300, 42));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(loginButton);

        card.add(Box.createVerticalStrut(12));

        // Register button
        JButton registerButton = new JButton(
                "Don't have an account? Register"
        );

        registerButton.setForeground(BLUE);
        registerButton.setBackground(Color.WHITE);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(registerButton);

        card.add(Box.createVerticalStrut(18));

        JLabel footer = new JLabel("Ask  \u2022  Learn  \u2022  Share");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.setForeground(GRAY);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(footer);

        // LOGIN BUTTON ACTION
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText().trim();
                String password =
                        new String(passwordField.getPassword());

                User user = userManager.login(username, password);

                if (user == null) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Invalid username or password.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                ArrayList<Role> roles = user.getRoles();
                Role selectedRole;

                // Automatically select the user's only role
                if (roles.size() == 1) {

                    selectedRole = roles.get(0);

                } else if (roles.size() > 1) {

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

frame.dispose();

if (selectedRole == Role.ADMIN) {

    new AdminGUI(user, userManager);

} else {

    showHomeScreen(user, selectedRole, userManager);
}
            }
        });

        // REGISTER BUTTON ACTION
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showRegister(userManager, frame);
            }
        });

        // Press Enter to log in
        frame.getRootPane().setDefaultButton(loginButton);

        frame.setVisible(true);
    }

    // Registration window
    private static void showRegister(
            UserManager userManager, JFrame loginFrame) {

        JDialog dialog = new JDialog(
                loginFrame, "Create Account", true
        );

        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(loginFrame);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));
        panel.setBackground(Color.WHITE);

        dialog.add(panel);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(25));

        // Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameLabel);

        panel.add(Box.createVerticalStrut(6));

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(300, 38));
        usernameField.setPreferredSize(new Dimension(300, 38));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(15));

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passwordLabel);

        panel.add(Box.createVerticalStrut(6));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 38));
        passwordField.setPreferredSize(new Dimension(300, 38));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(25));

        JButton createButton = new JButton("Create Account");
        createButton.setBackground(BLUE);
        createButton.setForeground(Color.WHITE);
        createButton.setOpaque(true);
        createButton.setBorderPainted(false);
        createButton.setFocusPainted(false);
        createButton.setMaximumSize(new Dimension(300, 42));
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(createButton);

        createButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password =
                    new String(passwordField.getPassword());

            User newUser = userManager.registerUser(
                    username, password
            );

            if (newUser != null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Account created successfully!\n"
                        + "Assigned role: " + newUser.getRoles()
                        + "\nPlease log in."
                );

                dialog.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Registration failed.\n"
                        + "Check the username and password.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        dialog.getRootPane().setDefaultButton(createButton);

        dialog.setVisible(true);
    }

    // Temporary role home screen
    private static void showHomeScreen(
            User user, Role role, UserManager userManager) {

        JFrame homeFrame = new JFrame(role + " Home");
        homeFrame.setSize(450, 350);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));

        homeFrame.add(panel);

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + user.getUsername()
        );

        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcomeLabel.setForeground(DARK);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        panel.add(Box.createVerticalStrut(10));

        JLabel roleLabel = new JLabel("Role: " + role);
        roleLabel.setForeground(GRAY);
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(roleLabel);

        panel.add(Box.createVerticalStrut(25));

        // Admin-only role assignment
        if (role == Role.ADMIN) {

            JButton assignRoleButton =
                    new JButton("Assign User Role");

            assignRoleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(assignRoleButton);

            assignRoleButton.addActionListener(e -> {

                String username = JOptionPane.showInputDialog(
                        homeFrame,
                        "Enter the username:"
                );

                if (username == null || username.trim().isEmpty()) {
                    return;
                }

                Role newRole = (Role)
                        JOptionPane.showInputDialog(
                                homeFrame,
                                "Select a role to assign:",
                                "Assign Role",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                Role.values(),
                                Role.STUDENT
                        );

                if (newRole == null) {
                    return;
                }

                boolean success = userManager.addRoleToUser(
                        username.trim(), newRole
                );

                JOptionPane.showMessageDialog(
                        homeFrame,
                        success
                                ? "Role assigned successfully!"
                                : "User not found."
                );
            });
        }

        panel.add(Box.createVerticalStrut(20));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoutButton);

        logoutButton.addActionListener(e -> {

            homeFrame.dispose();

            showLogin(userManager);
        });

        homeFrame.setVisible(true);
    }
}