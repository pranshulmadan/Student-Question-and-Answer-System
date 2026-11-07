import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentGUITest {

    private Robot robot;
    private FrameFixture loginWindow;
    private UserManager userManager;

    @BeforeEach
    public void setUp() throws Exception {
        robot = BasicRobot.robotWithNewAwtHierarchy();
        userManager = new UserManager();
        SwingUtilities.invokeAndWait(() -> LoginGUI.showLogin(userManager));
        loginWindow = findWindowByTitle("Student Q&A System");
    }

    // AssertJ's findFrame(String) matches the component NAME, not its title.
    // This matcher searches by the actual JFrame title instead.
    private FrameFixture findWindowByTitle(String title) {
        GenericTypeMatcher<JFrame> matcher =
                new GenericTypeMatcher<JFrame>(JFrame.class) {
                    @Override
                    protected boolean isMatching(JFrame frame) {
                        return title.equals(frame.getTitle()) && frame.isShowing();
                    }
                };

        return WindowFinder.findFrame(matcher)
                .withTimeout(10000)
                .using(robot);
    }

    @Test
    public void testLoginScreenVisible() {
        loginWindow.requireVisible();
        loginWindow.requireTitle("Student Q&A System");
        loginWindow.textBox("usernameField").requireVisible();
        loginWindow.textBox("passwordField").requireVisible();
        loginWindow.button("loginButton").requireVisible();
    }

    @Test
    public void testEmptyUsername() {
        loginWindow.button("loginButton").click();
        loginWindow.label("loginErrorLabel")
                .requireText("Please enter your username.");
    }

    @Test
    public void testEmptyPassword() {
        loginWindow.textBox("usernameField")
                .enterText("nonexistent_test_student");
        loginWindow.button("loginButton").click();
        loginWindow.label("loginErrorLabel")
                .requireText("Please enter your password.");
    }

    // Uses the existing DEMO LOGIN button (which signs in as "Test").
    // This validates the demo account first, so missing account/roles produce
    // a clear assertion failure instead of a GUI timeout.
    private FrameFixture logInAsStudent() {
        User demoUser = userManager.getUserByUsername("Test");
        assertNotNull(demoUser,
                "The DEMO LOGIN button requires an existing account named 'Test'.");
        assertTrue(demoUser.getRoles().contains(Role.STUDENT),
                "The 'Test' demo account must have the STUDENT role.");

        loginWindow.button(JButtonMatcher.withText("DEMO LOGIN")).click();

        // LoginGUI shows role selection only for users with multiple roles.
        if (demoUser.getRoles().size() > 1) {
            FrameFixture roles = findWindowByTitle("Select Role");
            roles.button(JButtonMatcher.withText("Student Dashboard")).click();
        }

        return findWindowByTitle("Student Q&A - Student Dashboard");
    }

    @Test
    public void testValidStudentLogin() {
        FrameFixture dashboard = logInAsStudent();
        dashboard.requireVisible();
        dashboard.requireTitle("Student Q&A - Student Dashboard");
    }

    @Test
    public void testDashboardButtons() {
        FrameFixture dashboard = logInAsStudent();
        dashboard.button("askQuestionButton").requireVisible();
        dashboard.button("viewQuestionsButton").requireVisible();
        dashboard.button("addAnswerButton").requireVisible();
        dashboard.button("unresolvedButton").requireVisible();
        dashboard.button("markSolvedButton").requireVisible();
        dashboard.button("logoutButton").requireVisible();
    }

    @Test
    public void testAskQuestionWindowOpens() {
        FrameFixture dashboard = logInAsStudent();
        dashboard.button("askQuestionButton").click();
        FrameFixture askQuestion = findWindowByTitle("Ask Question");
        askQuestion.requireVisible();
        askQuestion.requireTitle("Ask Question");
        askQuestion.close();
    }

    @Test
    public void testLogoutReturnsToLogin() {
        FrameFixture dashboard = logInAsStudent();
        dashboard.button("logoutButton").click();
        findWindowByTitle("Student Q&A System").requireVisible();
    }

    @AfterEach
    public void tearDown() {
        if (robot != null) {
            robot.cleanUp();
        }
    }
}
