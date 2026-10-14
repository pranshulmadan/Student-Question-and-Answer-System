import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class QuestionManagerTest {

    /*
     * User Story:
     * A student can ask a question.
     */
    @Test
    public void testStudentCanAskQuestion() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "JUnit Test Question",
                        "This question was created by a JUnit test.",
                        "Test"
                );

        assertNotNull(
                question
        );

        assertEquals(
                "JUnit Test Question",
                question.getTitle()
        );

        assertEquals(
                "Test",
                question.getAuthor()
        );

        assertFalse(
                question.isResolved()
        );
    }

    /*
     * User Story:
     * A student can see related questions
     * before submitting a new question.
     */
    @Test
    public void testRelatedQuestionsAreFound() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Recursion JUnit Example",
                        "How does recursion work in Java?",
                        "Test"
                );

        assertNotNull(
                question
        );

        ArrayList<Question> related =
                manager.findRelatedQuestions(
                        "Help with recursion",
                        "I do not understand recursion."
                );

        assertTrue(
                related.contains(question)
        );
    }

    /*
     * User Story:
     * A student can see unresolved questions.
     */
    @Test
    public void testUnresolvedQuestionsAreReturned() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Unresolved JUnit Question",
                        "This question should still be unresolved.",
                        "Test"
                );

        assertNotNull(
                question
        );

        ArrayList<Question> unresolved =
                manager.getUnresolvedQuestions();

        assertTrue(
                unresolved.contains(question)
        );

        assertFalse(
                question.isResolved()
        );
    }

    /*
     * User Story:
     * A student can submit a potential answer.
     */
    @Test
    public void testStudentCanAddAnswer() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Answer JUnit Question",
                        "This question needs a potential answer.",
                        "Test"
                );

        Answer answer =
                manager.addAnswer(
                        question,
                        "This is a potential answer.",
                        "Test"
                );

        assertNotNull(
                answer
        );

        assertEquals(
                1,
                question.getAnswers().size()
        );

        assertEquals(
                "This is a potential answer.",
                question.getAnswers()
                        .get(0)
                        .getDescription()
        );
    }

    /*
     * The student who asked the question
     * can choose an answer and mark the
     * question as solved.
     */
    @Test
    public void testQuestionAuthorCanResolve() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Resolved JUnit Question",
                        "This question will be marked solved.",
                        "Test"
                );

        Answer answer =
                manager.addAnswer(
                        question,
                        "This answer solved the problem.",
                        "AnotherStudent"
                );

        boolean result =
                manager.resolveQuestion(
                        question,
                        answer,
                        "Test"
                );

        assertTrue(
                result
        );

        assertTrue(
                question.isResolved()
        );

        assertEquals(
                answer.getAnswerId(),
                question.getAcceptedAnswerId()
        );

        assertTrue(
                question.hasAcceptedAnswer()
        );
    }

    /*
     * A student who did not ask the question
     * cannot mark another student's question
     * as solved.
     */
    @Test
    public void testOtherStudentCannotResolve() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Author Permission JUnit Question",
                        "Only Test should be able to resolve this.",
                        "Test"
                );

        Answer answer =
                manager.addAnswer(
                        question,
                        "Potential answer.",
                        "AnotherStudent"
                );

        boolean result =
                manager.resolveQuestion(
                        question,
                        answer,
                        "NotTest"
                );

        assertFalse(
                result
        );

        assertFalse(
                question.isResolved()
        );

        assertFalse(
                question.hasAcceptedAnswer()
        );
    }
    @Test
public void testUnresolvedQuestionShowsPotentialAnswers() {

    QuestionManager manager =
            new QuestionManager();

    Question question =
            manager.addQuestion(
                    "Unresolved Question With Answers",
                    "This question should have potential answers.",
                    "Test"
            );

    Answer answer =
            manager.addAnswer(
                    question,
                    "This is an existing potential answer.",
                    "AnotherStudent"
            );

    ArrayList<Question> unresolved =
            manager.getUnresolvedQuestions();

    assertTrue(
            unresolved.contains(question)
    );

    assertFalse(
            question.getAnswers().isEmpty()
    );

    assertEquals(
            1,
            question.getAnswers().size()
    );

    assertEquals(
            "This is an existing potential answer.",
            question.getAnswers()
                    .get(0)
                    .getDescription()
    );
}
}