
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentGUI {

    private JFrame frame;
    private User user;
    private UserManager userManager;

    private static final Color BLUE =
            new Color(23, 101, 209);

    private static final Color DARK =
            new Color(20, 44, 82);

    private static final Color BACKGROUND =
            new Color(244, 247, 252);

    public StudentGUI(User user, UserManager userManager) {

        this.user = user;
        this.userManager = userManager;

        // Create window
        frame = new JFrame("Student Home");

        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        panel.setBackground(BACKGROUND);

        panel.setBorder(
                new EmptyBorder(40, 50, 40, 50)
        );

        frame.add(panel);

        // Title
        JLabel title = new JLabel("Student Dashboard");

        title.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        title.setForeground(DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);

        panel.add(Box.createVerticalStrut(15));

        // Welcome message
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + user.getUsername()
        );

        welcomeLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(welcomeLabel);

        panel.add(Box.createVerticalStrut(35));

        // Ask Question button
        JButton askButton = createButton("Ask Question");

        panel.add(askButton);

        panel.add(Box.createVerticalStrut(15));

        // View Questions button
        JButton viewButton = createButton("View Questions");

        panel.add(viewButton);

        panel.add(Box.createVerticalStrut(15));

        // Unresolved Questions button
        JButton unresolvedButton =
                createButton("Unresolved Questions");

        panel.add(unresolvedButton);

        panel.add(Box.createVerticalStrut(15));

        // Logout button
        JButton logoutButton = createButton("Logout");

        panel.add(logoutButton);

        // Logout action
        logoutButton.addActionListener(e -> {

            frame.dispose();

            LoginGUI.showLogin(userManager);
        });

        frame.setVisible(true);
    }

    // Create matching blue buttons
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        button.setBackground(BLUE);
        button.setForeground(Color.WHITE);

        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setMaximumSize(
                new Dimension(350, 42)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return button;
    }
}