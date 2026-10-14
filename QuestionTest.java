import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuestionTest {

    @Test
    public void testNewQuestionIsUnresolved() {

        Question question =
                new Question(
                        1,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test"
                );

        assertFalse(
                question.isResolved()
        );
    }

    @Test
    public void testQuestionCanReceiveAnswer() {

        Question question =
                new Question(
                        1,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test"
                );

        Answer answer =
                new Answer(
                        1,
                        "Recursion is when a method calls itself.",
                        "Test"
                );

        question.addAnswer(
                answer
        );

        assertEquals(
                1,
                question.getAnswers().size()
        );
    }

    @Test
    public void testAcceptedAnswer() {

        Question question =
                new Question(
                        1,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test"
                );

        Answer answer =
                new Answer(
                        5,
                        "A function calling itself.",
                        "Test"
                );

        question.addAnswer(
                answer
        );

        question.setResolved(
                true
        );

        question.setAcceptedAnswerId(
                answer.getAnswerId()
        );

        assertTrue(
                question.isResolved()
        );

        assertEquals(
                5,
                question.getAcceptedAnswerId()
        );

        assertTrue(
                question.hasAcceptedAnswer()
        );
    }
}