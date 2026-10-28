import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginGUI {

    private static final Color BLUE =
            new Color(23, 101, 209);

    private static final Color DARK =
            new Color(20, 44, 82);

    private static final Color GRAY =
            new Color(96, 114, 139);

    private static final Color BACKGROUND =
            new Color(244, 247, 252);

    /*
     * Set to true for presentation/demo mode.
     * Set to false to hide the demo login button.
     */
    private static final boolean DEMO_MODE = true;

    public static void main(String[] args) {

        UserManager userManager =
                new UserManager();

        SwingUtilities.invokeLater(() ->
                showLogin(userManager)
        );
    }

    /*
     * LOGIN SCREEN
     */

    public static void showLogin(
            UserManager userManager) {

        JFrame frame =
                new JFrame(
                        "Student Q&A System"
                );

        frame.setSize(
                800,
                520
        );

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Color lightBackground =
                new Color(
                        245,
                        248,
                        252
                );

        /*
         * MAIN PANEL
         */

        JPanel mainPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2
                        )
                );

        frame.add(
                mainPanel
        );

        /*
         * LEFT SIDE
         */

        JPanel leftPanel =
                new JPanel();

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        leftPanel.setBackground(
                BLUE
        );

        leftPanel.setBorder(
                BorderFactory
                        .createEmptyBorder(
                                80,
                                45,
                                60,
                                45
                        )
        );

        JLabel logo =
                new JLabel(
                        "Student Q&A"
                );

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        logo.setForeground(
                Color.WHITE
        );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        leftPanel.add(
                logo
        );

        leftPanel.add(
                Box.createVerticalStrut(
                        15
                )
        );

        JLabel slogan =
                new JLabel(
                        "Ask. Learn. Share."
                );

        slogan.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        18
                )
        );

        slogan.setForeground(
                Color.WHITE
        );

        slogan.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        leftPanel.add(
                slogan
        );

        leftPanel.add(
                Box.createVerticalStrut(
                        50
                )
        );

        JLabel feature1 =
                createFeatureLabel(
                        "Ask questions"
                );

        JLabel feature2 =
                createFeatureLabel(
                        "Learn from other students"
                );

        JLabel feature3 =
                createFeatureLabel(
                        "Share your knowledge"
                );

        leftPanel.add(
                feature1
        );

        leftPanel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        leftPanel.add(
                feature2
        );

        leftPanel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        leftPanel.add(
                feature3
        );

        /*
         * RIGHT SIDE
         */

        JPanel rightPanel =
                new JPanel();

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        rightPanel.setBackground(
                lightBackground
        );

        rightPanel.setBorder(
                BorderFactory
                        .createEmptyBorder(
                                45,
                                55,
                                35,
                                55
                        )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome Back"
                );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        welcomeLabel.setForeground(
                DARK
        );

        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                welcomeLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Sign in to your account"
                );

        subtitle.setForeground(
                GRAY
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                subtitle
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        25
                )
        );

        /*
         * USERNAME
         */

        JLabel usernameLabel =
                new JLabel(
                        "Username"
                );

        usernameLabel.setForeground(
                DARK
        );

        usernameLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        usernameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                usernameLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        7
                )
        );

        JTextField usernameField =
                new JTextField();

        usernameField.setMaximumSize(
                new Dimension(
                        280,
                        42
                )
        );

        usernameField.setPreferredSize(
                new Dimension(
                        280,
                        42
                )
        );

        usernameField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        usernameField.setHorizontalAlignment(
                JTextField.CENTER
        );

        usernameField.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                usernameField
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        15
                )
        );

        /*
         * PASSWORD
         */

        JLabel passwordLabel =
                new JLabel(
                        "Password"
                );

        passwordLabel.setForeground(
                DARK
        );

        passwordLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        passwordLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                passwordLabel
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        7
                )
        );

        JPasswordField passwordField =
                new JPasswordField();

        passwordField.setMaximumSize(
                new Dimension(
                        280,
                        42
                )
        );

        passwordField.setPreferredSize(
                new Dimension(
                        280,
                        42
                )
        );

        passwordField.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        passwordField.setHorizontalAlignment(
                JTextField.CENTER
        );

        passwordField.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                passwordField
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        /*
         * SHOW PASSWORD
         */

        JCheckBox showPassword =
                new JCheckBox(
                        "Show password"
                );

        showPassword.setBackground(
                lightBackground
        );

        showPassword.setForeground(
                GRAY
        );

        showPassword.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        char defaultEcho =
                passwordField
                        .getEchoChar();

        showPassword
                .addActionListener(
                        e -> {

                            if (showPassword
                                    .isSelected()) {

                                passwordField
                                        .setEchoChar(
                                                (char) 0
                                        );

                            } else {

                                passwordField
                                        .setEchoChar(
                                                defaultEcho
                                        );
                            }
                        }
                );

        rightPanel.add(
                showPassword
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        /*
         * LOGIN BUTTON
         */

        JButton loginButton =
                new JButton(
                        "LOGIN"
                );

        loginButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        loginButton.setBackground(
                BLUE
        );

        loginButton.setForeground(
                Color.WHITE
        );

        loginButton.setOpaque(
                true
        );

        loginButton.setBorderPainted(
                false
        );

        loginButton.setFocusPainted(
                false
        );

        loginButton.setMaximumSize(
                new Dimension(
                        280,
                        44
                )
        );

        loginButton.setPreferredSize(
                new Dimension(
                        280,
                        44
                )
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                loginButton
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        /*
         * DEMO LOGIN BUTTON
         */

        JButton demoButton =
                new JButton(
                        "DEMO LOGIN"
                );

        demoButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        demoButton.setBackground(
                DARK
        );

        demoButton.setForeground(
                Color.WHITE
        );

        demoButton.setOpaque(
                true
        );

        demoButton.setBorderPainted(
                false
        );

        demoButton.setFocusPainted(
                false
        );

        demoButton.setMaximumSize(
                new Dimension(
                        280,
                        44
                )
        );

        demoButton.setPreferredSize(
                new Dimension(
                        280,
                        44
                )
        );

        demoButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        demoButton.setVisible(
                DEMO_MODE
        );

        rightPanel.add(
                demoButton
        );

        rightPanel.add(
                Box.createVerticalStrut(
                        12
                )
        );

        /*
         * REGISTER
         */

        JLabel accountLabel =
                new JLabel(
                        "Don't have an account?"
                );

        accountLabel.setForeground(
                GRAY
        );

        accountLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                accountLabel
        );

        JButton registerButton =
                new JButton(
                        "Create an Account"
                );

        registerButton.setForeground(
                BLUE
        );

        registerButton.setBackground(
                lightBackground
        );

        registerButton.setBorderPainted(
                false
        );

        registerButton.setFocusPainted(
                false
        );

        registerButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        rightPanel.add(
                registerButton
        );

        /*
         * ADD BOTH SIDES
         */

        mainPanel.add(
                leftPanel
        );

        mainPanel.add(
                rightPanel
        );

        /*
         * DEMO LOGIN ACTION
         */

        demoButton.addActionListener(
                e -> {

                    User user =
                            userManager
                                    .getUserByUsername(
                                            "Test"
                                    );

                    if (user == null) {

                        JOptionPane
                                .showMessageDialog(
                                        frame,
                                        "Demo user 'Test' does not exist.",
                                        "Demo Login",
                                        JOptionPane.ERROR_MESSAGE
                                );

                        return;
                    }

                    java.util.ArrayList<Role> roles =
                            user.getRoles();

                    if (roles.size() == 1) {

                        Role selectedRole =
                                roles.get(0);

                        frame.dispose();

                        openRoleScreen(
                                selectedRole,
                                user,
                                userManager
                        );

                    } else if (
                            roles.size() > 1) {

                        frame.dispose();

                        showRoleSelection(
                                user,
                                userManager,
                                roles
                        );

                    } else {

                        JOptionPane
                                .showMessageDialog(
                                        frame,
                                        "Demo user has no role assigned."
                                );
                    }
                }
        );

        /*
         * NORMAL LOGIN ACTION
         */

        loginButton
                .addActionListener(
                        e -> {

                            String username =
                                    usernameField
                                            .getText()
                                            .trim();

                            String password =
                                    new String(
                                            passwordField
                                                    .getPassword()
                                    );

                            User user =
                                    userManager.login(
                                            username,
                                            password
                                    );

                            if (user == null) {

                                JOptionPane
                                        .showMessageDialog(
                                                frame,
                                                "Invalid username or password.",
                                                "Login Failed",
                                                JOptionPane.ERROR_MESSAGE
                                        );

                                return;
                            }

                            java.util.ArrayList<Role> roles =
                                    user.getRoles();

                            /*
                             * ONE ROLE
                             */

                            if (roles.size() == 1) {

                                Role selectedRole =
                                        roles.get(0);

                                frame.dispose();

                                openRoleScreen(
                                        selectedRole,
                                        user,
                                        userManager
                                );

                            }

                            /*
                             * MULTIPLE ROLES
                             */

                            else if (
                                    roles.size() > 1) {

                                frame.dispose();

                                showRoleSelection(
                                        user,
                                        userManager,
                                        roles
                                );

                            }

                            /*
                             * NO ROLE
                             */

                            else {

                                JOptionPane
                                        .showMessageDialog(
                                                frame,
                                                "No role assigned."
                                        );
                            }
                        }
                );

        /*
         * REGISTER ACTION
         */

        registerButton
                .addActionListener(
                        e -> {

                            showRegister(
                                    userManager,
                                    frame
                            );
                        }
                );

        /*
         * ENTER KEY LOGIN
         */

        frame.getRootPane()
                .setDefaultButton(
                        loginButton
                );

        frame.setVisible(
                true
        );
    }

    /*
     * FEATURE LABEL
     */

    private static JLabel createFeatureLabel(
            String text) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        16
                )
        );

        label.setForeground(
                Color.WHITE
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }

    /*
     * ROLE SELECTION SCREEN
     */

    private static void showRoleSelection(
            User user,
            UserManager userManager,
            java.util.ArrayList<Role> roles) {

        JFrame roleFrame =
                new JFrame(
                        "Select Role"
                );

        roleFrame.setSize(
                500,
                480
        );

        roleFrame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        roleFrame.setLocationRelativeTo(
                null
        );

        roleFrame.setResizable(
                false
        );

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS
                )
        );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory
                        .createEmptyBorder(
                                45,
                                60,
                                40,
                                60
                        )
        );

        roleFrame.add(
                mainPanel
        );

        /*
         * TITLE
         */

        JLabel title =
                new JLabel(
                        "Choose Your Role"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                DARK
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(
                title
        );

        mainPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        /*
         * WELCOME
         */

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                        + user.getUsername()
                );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        welcomeLabel.setForeground(
                GRAY
        );

        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(
                welcomeLabel
        );

        mainPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        JLabel instruction =
                new JLabel(
                        "Select how you would like to continue."
                );

        instruction.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        instruction.setForeground(
                GRAY
        );

        instruction.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(
                instruction
        );

        mainPanel.add(
                Box.createVerticalStrut(
                        35
                )
        );

        /*
         * ROLE BUTTONS
         */

        for (Role role : roles) {

            JButton roleButton =
                    createRoleButton(
                            role
                    );

            roleButton.addActionListener(
                    e -> {

                        roleFrame.dispose();

                        openRoleScreen(
                                role,
                                user,
                                userManager
                        );
                    }
            );

            mainPanel.add(
                    roleButton
            );

            mainPanel.add(
                    Box.createVerticalStrut(
                            15
                    )
            );
        }

        /*
         * BACK TO LOGIN
         */

        mainPanel.add(
                Box.createVerticalGlue()
        );

        JButton backButton =
                new JButton(
                        "Back to Login"
                );

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        backButton.setForeground(
                BLUE
        );

        backButton.setBackground(
                BACKGROUND
        );

        backButton.setBorderPainted(
                false
        );

        backButton.setFocusPainted(
                false
        );

        backButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        backButton
                .addActionListener(
                        e -> {

                            roleFrame.dispose();

                            showLogin(
                                    userManager
                            );
                        }
                );

        mainPanel.add(
                backButton
        );

        roleFrame.setVisible(
                true
        );
    }

    /*
     * ROLE BUTTON DESIGN
     */

    private static JButton createRoleButton(
            Role role) {

        JButton button =
                new JButton();

        if (role == Role.ADMIN) {

            button.setText(
                    "Admin Dashboard"
            );

        } else if (
                role == Role.STUDENT) {

            button.setText(
                    "Student Dashboard"
            );

        } else if (
                role == Role.REVIEWER) {

            button.setText(
                    "Reviewer Dashboard"
            );

        } else {

            button.setText(
                    role.toString()
            );
        }

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        button.setBackground(
                BLUE
        );

        button.setForeground(
                Color.WHITE
        );

        button.setOpaque(
                true
        );

        button.setBorderPainted(
                false
        );

        button.setFocusPainted(
                false
        );

        button.setMaximumSize(
                new Dimension(
                        320,
                        52
                )
        );

        button.setPreferredSize(
                new Dimension(
                        320,
                        52
                )
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return button;
    }

    /*
     * OPEN SELECTED ROLE
     */

private static void openRoleScreen(
        Role role,
        User user,
        UserManager userManager) {

    if (role == Role.ADMIN) {

        new AdminGUI(
                user,
                userManager
        );

    } else if (
            role == Role.STUDENT) {

        new StudentGUI(
                user,
                userManager
        );

    } else if (
            role == Role.REVIEWER) {

        new ReviewerGUI(
                user,
                userManager
        );
    }
}

    /*
     * REGISTRATION WINDOW
     */

private static void showRegister(
        UserManager userManager,
        JFrame loginFrame) {

    JDialog dialog =
            new JDialog(
                    loginFrame,
                    "Create Account",
                    true
            );

    dialog.setSize(
            400,
            500
    );

    dialog.setLocationRelativeTo(
            loginFrame
    );

    dialog.setResizable(
            false
    );

    JPanel panel =
            new JPanel();

    panel.setLayout(
            new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
            )
    );

    panel.setBorder(
            new EmptyBorder(
                    25,
                    35,
                    25,
                    35
            )
    );

    panel.setBackground(
            Color.WHITE
    );

    dialog.add(
            panel
    );

    /*
     * TITLE
     */

    JLabel title =
            new JLabel(
                    "Create Account"
            );

    title.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    22
            )
    );

    title.setForeground(
            DARK
    );

    title.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            title
    );

    panel.add(
            Box.createVerticalStrut(
                    25
            )
    );

    /*
     * USERNAME
     */

    JLabel usernameLabel =
            new JLabel(
                    "Username"
            );

    usernameLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            usernameLabel
    );

    panel.add(
            Box.createVerticalStrut(
                    6
            )
    );

    JTextField usernameField =
            new JTextField();

    usernameField.setMaximumSize(
            new Dimension(
                    300,
                    38
            )
    );

    usernameField.setPreferredSize(
            new Dimension(
                    300,
                    38
            )
    );

    usernameField.setHorizontalAlignment(
            JTextField.CENTER
    );

    usernameField.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            usernameField
    );

    panel.add(
            Box.createVerticalStrut(
                    15
            )
    );

    /*
     * PASSWORD
     */

    JLabel passwordLabel =
            new JLabel(
                    "Password"
            );

    passwordLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            passwordLabel
    );

    panel.add(
            Box.createVerticalStrut(
                    6
            )
    );

    JPasswordField passwordField =
            new JPasswordField();

    passwordField.setMaximumSize(
            new Dimension(
                    300,
                    38
            )
    );

    passwordField.setPreferredSize(
            new Dimension(
                    300,
                    38
            )
    );

    passwordField.setHorizontalAlignment(
            JTextField.CENTER
    );

    passwordField.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            passwordField
    );

    panel.add(
            Box.createVerticalStrut(
                    15
            )
    );

    /*
     * INVITATION CODE
     */

    JLabel invitationLabel =
            new JLabel(
                    "Invitation Code"
            );

    invitationLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            invitationLabel
    );

    panel.add(
            Box.createVerticalStrut(
                    6
            )
    );

    JTextField invitationField =
            new JTextField();

    invitationField.setMaximumSize(
            new Dimension(
                    300,
                    38
            )
    );

    invitationField.setPreferredSize(
            new Dimension(
                    300,
                    38
            )
    );

    invitationField.setHorizontalAlignment(
            JTextField.CENTER
    );

    invitationField.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            invitationField
    );

    panel.add(
            Box.createVerticalStrut(
                    8
            )
    );

    JLabel invitationHelp =
            new JLabel(
                    "Leave blank for normal registration."
            );

    invitationHelp.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    11
            )
    );

    invitationHelp.setForeground(
            GRAY
    );

    invitationHelp.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            invitationHelp
    );

    panel.add(
            Box.createVerticalStrut(
                    25
            )
    );

    /*
     * CREATE ACCOUNT BUTTON
     */

    JButton createButton =
            new JButton(
                    "Create Account"
            );

    createButton.setBackground(
            BLUE
    );

    createButton.setForeground(
            Color.WHITE
    );

    createButton.setOpaque(
            true
    );

    createButton.setBorderPainted(
            false
    );

    createButton.setFocusPainted(
            false
    );

    createButton.setMaximumSize(
            new Dimension(
                    300,
                    42
            )
    );

    createButton.setPreferredSize(
            new Dimension(
                    300,
                    42
            )
    );

    createButton.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

    panel.add(
            createButton
    );

    /*
     * CREATE ACCOUNT ACTION
     */

    createButton.addActionListener(
            e -> {

                String username =
                        usernameField
                                .getText()
                                .trim();

                String password =
                        new String(
                                passwordField
                                        .getPassword()
                        );

                String invitationCode =
                        invitationField
                                .getText()
                                .trim();

                User newUser;

                /*
                 * INVITED REGISTRATION
                 */

                if (!invitationCode.isEmpty()) {

                    InvitationManager invitationManager =
                            new InvitationManager();

                    Invitation invitation =
                            invitationManager
                                    .validateInvitation(
                                            invitationCode
                                    );

                    /*
                     * Invalid / expired / used
                     */

                    if (invitation == null) {

                        JOptionPane.showMessageDialog(
                                dialog,
                                "The invitation code is invalid, "
                                + "expired, or already used.",
                                "Invalid Invitation",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }

                    /*
                     * Register with invitation roles
                     */

                    newUser =
                            userManager
                                    .registerInvitedUser(
                                            username,
                                            password,
                                            invitation.getRoles()
                                    );

                    /*
                     * Only mark invitation used
                     * after account creation succeeds.
                     */

                    if (newUser != null) {

                        invitationManager
                                .useInvitation(
                                        invitationCode
                                );
                    }

                }

                /*
                 * NORMAL REGISTRATION
                 */

                else {

                    newUser =
                            userManager
                                    .registerUser(
                                            username,
                                            password
                                    );
                }

                /*
                 * SUCCESS
                 */

                if (newUser != null) {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Account created successfully!\n"
                            + "Assigned role(s): "
                            + newUser.getRoles()
                            + "\nPlease log in."
                    );

                    dialog.dispose();

                }

                /*
                 * FAILURE
                 */

                else {

                    JOptionPane.showMessageDialog(
                            dialog,
                            "Registration failed.\n"
                            + "The username may already exist "
                            + "or the username/password is invalid.",
                            "Registration Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
    );

    dialog.getRootPane()
            .setDefaultButton(
                    createButton
            );

    dialog.setVisible(
            true
    );
}
}