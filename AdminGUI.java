import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminGUI {

    public AdminGUI(User user) {

        JFrame frame = new JFrame("Admin Home");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel welcomeLabel =
                new JLabel("Welcome, " + user.getUsername());

        panel.add(welcomeLabel);

        JLabel roleLabel =
                new JLabel("Role: ADMIN");

        panel.add(roleLabel);

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            frame.dispose();
        });

        frame.setVisible(true);
    }
}