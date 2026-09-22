
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminGUI {

    private JFrame frame;
    private User admin;
    private UserManager userManager;

    private static final Color BLUE = new Color(23, 101, 209);
    private static final Color DARK = new Color(20, 44, 82);
    private static final Color BACKGROUND =
            new Color(244, 247, 252);

    public AdminGUI(User admin, UserManager userManager) {

        this.admin = admin;
        this.userManager = userManager;

        frame = new JFrame("Admin Home");

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(35, 60, 35, 60));

        frame.add(panel);

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);

        panel.add(Box.createVerticalStrut(10));

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + admin.getUsername()
        );

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        panel.add(Box.createVerticalStrut(30));

        JButton viewUsersButton = createButton("View Users");
        JButton assignRoleButton = createButton("Assign Role");
        JButton removeRoleButton = createButton("Remove Role");
        JButton removeUserButton = createButton("Remove User");
        JButton logoutButton = createButton("Logout");

        panel.add(viewUsersButton);
        panel.add(Box.createVerticalStrut(12));

        panel.add(assignRoleButton);
        panel.add(Box.createVerticalStrut(12));

        panel.add(removeRoleButton);
        panel.add(Box.createVerticalStrut(12));

        panel.add(removeUserButton);
        panel.add(Box.createVerticalStrut(12));

        panel.add(logoutButton);

        viewUsersButton.addActionListener(e -> viewUsers());

        assignRoleButton.addActionListener(e -> assignRole());

        removeRoleButton.addActionListener(e -> removeRole());

        removeUserButton.addActionListener(e -> removeUser());

        logoutButton.addActionListener(e -> {

            frame.dispose();

            LoginGUI.showLogin(userManager);
        });

        frame.setVisible(true);
    }

    // Create buttons with matching design
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(BLUE);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(350, 42));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    // Display all registered users
    private void viewUsers() {

        ArrayList<User> users = userManager.getUsers();

        StringBuilder message = new StringBuilder();

        for (User user : users) {

            message.append("Username: ")
                    .append(user.getUsername())
                    .append("\nRoles: ")
                    .append(user.getRoles())
                    .append("\n\n");
        }

        JTextArea textArea = new JTextArea(
                message.toString(), 12, 30
        );

        textArea.setEditable(false);

        JOptionPane.showMessageDialog(
                frame,
                new JScrollPane(textArea),
                "Registered Users",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Assign an additional role
    private void assignRole() {

        String username = selectUser("Assign Role");

        if (username == null) {
            return;
        }

        Role role = (Role) JOptionPane.showInputDialog(
                frame,
                "Select a role to assign:",
                "Assign Role",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Role.values(),
                Role.STUDENT
        );

        if (role == null) {
            return;
        }

        boolean success = userManager.addRoleToUser(
                username, role
        );

        JOptionPane.showMessageDialog(
                frame,
                success
                        ? "Role assigned successfully!"
                        : "Unable to assign role."
        );
    }

    // Remove a role from an existing user
    private void removeRole() {

        String username = selectUser("Remove Role");

        if (username == null) {
            return;
        }

        User selectedUser = null;

        for (User user : userManager.getUsers()) {

            if (user.getUsername().equals(username)) {

                selectedUser = user;
                break;
            }
        }

        if (selectedUser == null) {
            return;
        }

        ArrayList<Role> roles = selectedUser.getRoles();

        if (roles.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "This user has no assigned roles."
            );

            return;
        }

        Role role = (Role) JOptionPane.showInputDialog(
                frame,
                "Select a role to remove:",
                "Remove Role",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles.toArray(),
                roles.get(0)
        );

        if (role == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Remove " + role + " from " + username + "?",
                "Confirm Role Removal",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = userManager.removeRoleFromUser(
                admin, username, role
        );

        JOptionPane.showMessageDialog(
                frame,
                success
                        ? "Role removed successfully!"
                        : "Unable to remove role.\n"
                        + "You cannot remove the last role "
                        + "or the last administrator."
        );
    }

    // Delete an existing user
    private void removeUser() {

        String username = selectUser("Remove User");

        if (username == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete "
                        + username + "?",
                "Confirm User Removal",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = userManager.removeUser(
                admin, username
        );

        JOptionPane.showMessageDialog(
                frame,
                success
                        ? "User removed successfully!"
                        : "Unable to remove user.\n"
                        + "You cannot remove your own account "
                        + "or the last administrator."
        );
    }

    // Display a list of usernames for selection
    private String selectUser(String title) {

        ArrayList<User> users = userManager.getUsers();

        if (users.isEmpty()) {
            return null;
        }

        String[] usernames = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {

            usernames[i] = users.get(i).getUsername();
        }

        return (String) JOptionPane.showInputDialog(
                frame,
                "Select a user:",
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                usernames,
                usernames[0]
        );
    }
}