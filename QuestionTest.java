import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuestionTest {

    /*
     * TEST 1
     * A newly created question
     * should start unresolved.
     */
    @Test
    public void testNewQuestionIsUnresolved() {

        Question question =
                new Question(
                        1,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test",
                        Subject.PROGRAMMING
                );

        assertFalse(
                question.isResolved()
        );
    }

    /*
     * TEST 2
     * A question should correctly
     * store its subject.
     */
    @Test
    public void testQuestionStoresSubject() {

        Question question =
                new Question(
                        2,
                        "What is a binary tree?",
                        "I need help understanding binary trees.",
                        "Test",
                        Subject.DATA_STRUCTURES
                );

        assertEquals(
                Subject.DATA_STRUCTURES,
                question.getSubject()
        );
    }

    /*
     * TEST 3
     * If the old constructor is used,
     * the question should default
     * to Miscellaneous.
     */
    @Test
    public void testQuestionDefaultsToMiscellaneous() {

        Question question =
                new Question(
                        3,
                        "General Question",
                        "This question has no selected subject.",
                        "Test"
                );

        assertEquals(
                Subject.MISCELLANEOUS,
                question.getSubject()
        );
    }

    /*
     * TEST 4
     * A question should be able
     * to receive an answer.
     */
    @Test
    public void testQuestionCanReceiveAnswer() {

        Question question =
                new Question(
                        4,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test",
                        Subject.PROGRAMMING
                );

        Answer answer =
                new Answer(
                        1,
                        "Recursion is when a method calls itself.",
                        "Student2"
                );

        question.addAnswer(
                answer
        );

        assertEquals(
                1,
                question.getAnswers().size()
        );
    }

    /*
     * TEST 5
     * A question can receive
     * multiple answers.
     */
    @Test
    public void testQuestionCanReceiveMultipleAnswers() {

        Question question =
                new Question(
                        5,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test",
                        Subject.PROGRAMMING
                );

        Answer answer1 =
                new Answer(
                        1,
                        "Recursion is when a method calls itself.",
                        "Student1"
                );

        Answer answer2 =
                new Answer(
                        2,
                        "It solves a problem by calling itself with a smaller problem.",
                        "Student2"
                );

        question.addAnswer(
                answer1
        );

        question.addAnswer(
                answer2
        );

        assertEquals(
                2,
                question.getAnswers().size()
        );
    }

    /*
     * TEST 6
     * A question should correctly
     * store its accepted answer.
     */
    @Test
    public void testAcceptedAnswer() {

        Question question =
                new Question(
                        6,
                        "What is recursion?",
                        "Please explain recursion.",
                        "Test",
                        Subject.PROGRAMMING
                );

        Answer answer =
                new Answer(
                        5,
                        "A function calling itself.",
                        "Student2"
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

    /*
     * TEST 7
     * A new question should not
     * have an accepted answer yet.
     */
    @Test
    public void testNewQuestionHasNoAcceptedAnswer() {

        Question question =
                new Question(
                        7,
                        "What is Big O?",
                        "Please explain Big O notation.",
                        "Test",
                        Subject.DATA_STRUCTURES
                );

        assertEquals(
                -1,
                question.getAcceptedAnswerId()
        );

        assertFalse(
                question.hasAcceptedAnswer()
        );
    }
}