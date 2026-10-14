import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminGUI {

    private JFrame frame;
    private User admin;
    private UserManager userManager;

    private static final Color BLUE =
            new Color(23, 101, 209);

    private static final Color DARK =
            new Color(20, 44, 82);

    private static final Color BACKGROUND =
            new Color(244, 247, 252);

    private static final Color GRAY =
            new Color(105, 115, 130);

    public AdminGUI(
            User admin,
            UserManager userManager) {

        this.admin = admin;
        this.userManager = userManager;

        frame = new JFrame(
                "Student Q&A - Admin Dashboard"
        );

        frame.setSize(900, 560);
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        frame.add(mainPanel);

        /*
         * LEFT SIDEBAR
         */

        JPanel sidebar = new JPanel();

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setPreferredSize(
                new Dimension(230, 560)
        );

        sidebar.setBackground(BLUE);

        sidebar.setBorder(
                new EmptyBorder(
                        35,
                        20,
                        30,
                        20
                )
        );

        JLabel systemTitle =
                new JLabel("Student Q&A");

        systemTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        systemTitle.setForeground(
                Color.WHITE
        );

        systemTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(systemTitle);

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        JLabel adminLabel =
                new JLabel("Admin Portal");

        adminLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        adminLabel.setForeground(
                new Color(
                        220,
                        230,
                        245
                )
        );

        adminLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(adminLabel);

        sidebar.add(
                Box.createVerticalStrut(45)
        );

        JButton viewUsersButton =
                createSidebarButton(
                        "View Users"
                );

        JButton assignRoleButton =
                createSidebarButton(
                        "Assign Role"
                );

        JButton removeRoleButton =
                createSidebarButton(
                        "Remove Role"
                );

        JButton removeUserButton =
                createSidebarButton(
                        "Remove User"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        sidebar.add(viewUsersButton);

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        sidebar.add(assignRoleButton);

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        sidebar.add(removeRoleButton);

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        sidebar.add(removeUserButton);

        sidebar.add(
                Box.createVerticalGlue()
        );

        sidebar.add(logoutButton);

        /*
         * RIGHT CONTENT AREA
         */

        JPanel contentPanel =
                new JPanel();

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        contentPanel.setBackground(
                BACKGROUND
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        55,
                        65,
                        45,
                        65
                )
        );

        JLabel title =
                new JLabel(
                        "Admin Dashboard"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(DARK);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(title);

        contentPanel.add(
                Box.createVerticalStrut(8)
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back, "
                        + admin.getUsername()
                );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        welcomeLabel.setForeground(GRAY);

        welcomeLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(welcomeLabel);

        contentPanel.add(
                Box.createVerticalStrut(35)
        );

        /*
         * MAIN CARD
         */

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory
                                .createLineBorder(
                                        new Color(
                                                220,
                                                225,
                                                235
                                        )
                                ),

                        new EmptyBorder(
                                30,
                                35,
                                30,
                                35
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        540,
                        280
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel cardTitle =
                new JLabel(
                        "User Management"
                );

        cardTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        cardTitle.setForeground(DARK);

        cardTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(cardTitle);

        card.add(
                Box.createVerticalStrut(10)
        );

        JLabel description =
                new JLabel(
                        "<html>"
                        + "Manage registered users, "
                        + "assign roles, remove roles, "
                        + "and delete accounts."
                        + "</html>"
                );

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(GRAY);

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(description);

        card.add(
                Box.createVerticalStrut(25)
        );

        JLabel statusLabel =
                new JLabel(
                        "Available Admin Tools"
                );

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        statusLabel.setForeground(DARK);

        statusLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(statusLabel);

        card.add(
                Box.createVerticalStrut(12)
        );

        JLabel toolsLabel =
                new JLabel(
                        "<html>"
                        + "• View registered users<br>"
                        + "• Assign additional roles<br>"
                        + "• Remove existing roles<br>"
                        + "• Remove user accounts"
                        + "</html>"
                );

        toolsLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        toolsLabel.setForeground(GRAY);

        toolsLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(toolsLabel);

        contentPanel.add(card);

        /*
         * ADD MAIN PANELS
         */

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        /*
         * BUTTON ACTIONS
         */

        viewUsersButton.addActionListener(
                e -> viewUsers()
        );

        assignRoleButton.addActionListener(
                e -> assignRole()
        );

        removeRoleButton.addActionListener(
                e -> removeRole()
        );

        removeUserButton.addActionListener(
                e -> removeUser()
        );

        logoutButton.addActionListener(e -> {

            frame.dispose();

            LoginGUI.showLogin(
                    userManager
            );
        });

        frame.setVisible(true);
    }

    /*
     * SIDEBAR BUTTON STYLE
     */

    private JButton createSidebarButton(
            String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                new Color(
                        23,
                        101,
                        209
                )
        );

        button.setOpaque(true);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setMaximumSize(
                new Dimension(
                        190,
                        42
                )
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return button;
    }

    /*
     * DISPLAY ALL USERS
     */

    private void viewUsers() {

        ArrayList<User> users =
                userManager.getUsers();

        StringBuilder message =
                new StringBuilder();

        for (User user : users) {

            message.append(
                    "Username: "
            );

            message.append(
                    user.getUsername()
            );

            message.append(
                    "\nRoles: "
            );

            message.append(
                    user.getRoles()
            );

            message.append(
                    "\n\n"
            );
        }

        JTextArea textArea =
                new JTextArea(
                        message.toString(),
                        12,
                        30
                );

        textArea.setEditable(false);

        textArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JOptionPane.showMessageDialog(
                frame,
                new JScrollPane(
                        textArea
                ),
                "Registered Users",
                JOptionPane
                        .INFORMATION_MESSAGE
        );
    }

    /*
     * ASSIGN ROLE
     */

    private void assignRole() {

        String username =
                selectUser(
                        "Assign Role"
                );

        if (username == null) {
            return;
        }

        Role role =
                (Role)
                JOptionPane.showInputDialog(
                        frame,
                        "Select a role to assign:",
                        "Assign Role",
                        JOptionPane
                                .QUESTION_MESSAGE,
                        null,
                        Role.values(),
                        Role.STUDENT
                );

        if (role == null) {
            return;
        }

        boolean success =
                userManager
                        .addRoleToUser(
                                username,
                                role
                        );

        JOptionPane.showMessageDialog(
                frame,
                success
                        ? "Role assigned successfully!"
                        : "Unable to assign role."
        );
    }

    /*
     * REMOVE ROLE
     */

    private void removeRole() {

        String username =
                selectUser(
                        "Remove Role"
                );

        if (username == null) {
            return;
        }

        User selectedUser = null;

        for (User user :
                userManager.getUsers()) {

            if (user.getUsername()
                    .equals(username)) {

                selectedUser = user;
                break;
            }
        }

        if (selectedUser == null) {
            return;
        }

        ArrayList<Role> roles =
                selectedUser.getRoles();

        if (roles.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "This user has no "
                    + "assigned roles."
            );

            return;
        }

        Role role =
                (Role)
                JOptionPane.showInputDialog(
                        frame,
                        "Select a role to remove:",
                        "Remove Role",
                        JOptionPane
                                .QUESTION_MESSAGE,
                        null,
                        roles.toArray(),
                        roles.get(0)
                );

        if (role == null) {
            return;
        }

        int confirm =
                JOptionPane
                        .showConfirmDialog(
                                frame,
                                "Remove "
                                + role
                                + " from "
                                + username
                                + "?",
                                "Confirm Role Removal",
                                JOptionPane
                                        .YES_NO_OPTION
                        );

        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }

        boolean success =
                userManager
                        .removeRoleFromUser(
                                admin,
                                username,
                                role
                        );

        JOptionPane.showMessageDialog(
                frame,
                success
                        ? "Role removed successfully!"
                        : "Unable to remove role.\n"
                        + "You cannot remove "
                        + "the last role or "
                        + "the last administrator."
        );
    }

    /*
     * REMOVE USER
     */

    private void removeUser() {

        String username =
                selectUser(
                        "Remove User"
                );

        if (username == null) {
            return;
        }

        int confirm =
                JOptionPane
                        .showConfirmDialog(
                                frame,
                                "Are you sure you want "
                                + "to delete "
                                + username
                                + "?",
                                "Confirm User Removal",
                                JOptionPane
                                        .YES_NO_OPTION
                        );

        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }

        boolean success =
                userManager.removeUser(
                        admin,
                        username
                );

        JOptionPane.showMessageDialog(
                frame,
                success
                        ? "User removed successfully!"
                        : "Unable to remove user.\n"
                        + "You cannot remove "
                        + "your own account "
                        + "or the last administrator."
        );
    }

    /*
     * USER SELECTION
     */

    private String selectUser(
            String title) {

        ArrayList<User> users =
                userManager.getUsers();

        if (users.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "No registered users found."
            );

            return null;
        }

        String[] usernames =
                new String[
                        users.size()
                ];

        for (int i = 0;
             i < users.size();
             i++) {

            usernames[i] =
                    users.get(i)
                            .getUsername();
        }

        return (String)
                JOptionPane
                        .showInputDialog(
                                frame,
                                "Select a user:",
                                title,
                                JOptionPane
                                        .QUESTION_MESSAGE,
                                null,
                                usernames,
                                usernames[0]
                        );
    }
}