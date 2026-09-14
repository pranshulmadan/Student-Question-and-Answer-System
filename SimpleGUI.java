import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI {
    public static void main(String[] args) {
        // 1. Create the main window (Frame)
        JFrame frame = new JFrame("My First Java GUI");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Create a panel to hold our buttons and text
        JPanel panel = new JPanel();
        frame.add(panel);

        // 3. Add a simple text label
        JLabel label = new JLabel("Hello! Click the button below.");
        panel.add(label);

        // 4. Add a clickable button
        JButton button = new JButton("Click Me!");
        panel.add(button);

        // 5. Add an action listener to make the button do something
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("You successfully clicked the button!");
            }
        });

        // 6. Make the window visible
        frame.setVisible(true);
    }
}