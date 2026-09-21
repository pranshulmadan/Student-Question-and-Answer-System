import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI {

    public static void main(String[] args) {

        UserManager userManager = new UserManager();

        JFrame frame = new JFrame("Student Question and Answer System");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField(15);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        JLabel messageLabel = new JLabel("");
        panel.add(messageLabel);

        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame registerFrame = new JFrame("Register");
                registerFrame.setSize(400, 250);

                JPanel registerPanel = new JPanel();
                registerFrame.add(registerPanel);

                JLabel registerUsernameLabel =
                        new JLabel("Username:");
                registerPanel.add(registerUsernameLabel);

                JTextField registerUsernameField =
                        new JTextField(15);
                registerPanel.add(registerUsernameField);

                JLabel registerPasswordLabel =
                        new JLabel("Password:");
                registerPanel.add(registerPasswordLabel);

                JPasswordField registerPasswordField =
                        new JPasswordField(15);
                registerPanel.add(registerPasswordField);

                JButton createButton =
                        new JButton("Create Account");
                registerPanel.add(createButton);

                JLabel registerMessageLabel =
                        new JLabel("");
                registerPanel.add(registerMessageLabel);

                createButton.addActionListener(
                        new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String username =
                                registerUsernameField.getText();

                        String password =
                                new String(
                                    registerPasswordField
                                        .getPassword()
                                );

                        User newUser =
                                userManager.registerUser(
                                        username,
                                        password
                                );

                        if (newUser != null) {

                            registerMessageLabel.setText(
                                    "Account created."
                            );

                            registerFrame.dispose();

                            messageLabel.setText(
                                    "Account created. Please log in."
                            );

                        } else {

                            registerMessageLabel.setText(
                                    "Username already exists."
                            );
                        }
                    }
                });

                registerFrame.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();

                String password =
                        new String(passwordField.getPassword());

                User user =
                        userManager.login(username, password);

                if (user != null) {

                    messageLabel.setText(
                            "Login successful!"
                    );

                    if (user.hasRole(Role.ADMIN)) {

                        frame.dispose();
                        new AdminGUI(user);
                    }

                } else {

                    messageLabel.setText(
                            "Invalid username or password."
                    );
                }
            }
        });

        frame.setVisible(true);
    }
}