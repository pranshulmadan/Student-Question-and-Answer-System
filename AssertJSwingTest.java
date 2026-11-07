
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;

public class AssertJSwingTest {

    @Test
    public void testButtonClick() {
        Robot robot = null;

        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame frame = new JFrame("GUI Test");
                JButton button = new JButton("Click Me");

                button.setName("testButton");
                button.addActionListener(e ->
                    button.setText("Clicked!")
                );

                frame.add(button);
                frame.setSize(300, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });

            robot = BasicRobot.robotWithCurrentAwtHierarchy();

            JFrame frame = robot.finder()
                .findByType(JFrame.class);

            FrameFixture window =
                new FrameFixture(robot, frame);

            window.button("testButton").click();

            window.button("testButton")
                .requireText("Clicked!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (robot != null) {
                robot.cleanUp();
            }
        }
    }
}
