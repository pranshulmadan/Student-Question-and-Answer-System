import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class QuestionManagerTest {

    /*
     * TEST 1
     *
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
                        "Test",
                        Subject.PROGRAMMING
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

        assertEquals(
                Subject.PROGRAMMING,
                question.getSubject()
        );

        assertFalse(
                question.isResolved()
        );
    }

    /*
     * TEST 2
     *
     * A question created without
     * a subject should automatically
     * become Miscellaneous.
     */
    @Test
    public void testQuestionDefaultsToMiscellaneous() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "General JUnit Question",
                        "This question does not specify a subject.",
                        "Test"
                );

        assertNotNull(
                question
        );

        assertEquals(
                Subject.MISCELLANEOUS,
                question.getSubject()
        );
    }

    /*
     * TEST 3
     *
     * User Story:
     * A student can see related questions
     * before submitting a new question.
     *
     * Related question should be found
     * when it is in the same subject.
     */
    @Test
    public void testRelatedQuestionsAreFoundInSameSubject() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Recursion JUnit Example",
                        "How does recursion work in Java?",
                        "Test",
                        Subject.PROGRAMMING
                );

        assertNotNull(
                question
        );

        ArrayList<Question> related =
                manager.findRelatedQuestions(
                        "Help with recursion",
                        "I do not understand recursion.",
                        Subject.PROGRAMMING
                );

        assertTrue(
                related.contains(
                        question
                )
        );
    }

    /*
     * TEST 4
     *
     * Related questions from a different
     * subject should not appear in a
     * subject-specific search.
     */
    @Test
    public void testRelatedQuestionsIgnoreDifferentSubjects() {

        QuestionManager manager =
                new QuestionManager();

        Question programmingQuestion =
                manager.addQuestion(
                        "Recursion Subject Test",
                        "How does recursion work?",
                        "Test",
                        Subject.PROGRAMMING
                );

        Question mathQuestion =
                manager.addQuestion(
                        "Recursion Subject Test",
                        "How does recursion work?",
                        "Test",
                        Subject.DISCRETE_MATH
                );

        assertNotNull(
                programmingQuestion
        );

        assertNotNull(
                mathQuestion
        );

        ArrayList<Question> related =
                manager.findRelatedQuestions(
                        "Recursion help",
                        "I need help with recursion.",
                        Subject.PROGRAMMING
                );

        assertTrue(
                related.contains(
                        programmingQuestion
                )
        );

        assertFalse(
                related.contains(
                        mathQuestion
                )
        );
    }

    /*
     * TEST 5
     *
     * Questions can be filtered
     * by subject.
     */
    @Test
    public void testQuestionsCanBeFilteredBySubject() {

        QuestionManager manager =
                new QuestionManager();

        Question programmingQuestion =
                manager.addQuestion(
                        "Programming Filter Test",
                        "Testing programming filtering.",
                        "Test",
                        Subject.PROGRAMMING
                );

        Question dataStructuresQuestion =
                manager.addQuestion(
                        "Data Structures Filter Test",
                        "Testing data structures filtering.",
                        "Test",
                        Subject.DATA_STRUCTURES
                );

        ArrayList<Question> programmingQuestions =
                manager.getQuestionsBySubject(
                        Subject.PROGRAMMING
                );

        assertTrue(
                programmingQuestions.contains(
                        programmingQuestion
                )
        );

        assertFalse(
                programmingQuestions.contains(
                        dataStructuresQuestion
                )
        );
    }

    /*
     * TEST 6
     *
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
                        "Test",
                        Subject.SOFTWARE_ENGINEERING
                );

        assertNotNull(
                question
        );

        ArrayList<Question> unresolved =
                manager.getUnresolvedQuestions();

        assertTrue(
                unresolved.contains(
                        question
                )
        );

        assertFalse(
                question.isResolved()
        );
    }

    /*
     * TEST 7
     *
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
                        "Test",
                        Subject.PROGRAMMING
                );

        Answer answer =
                manager.addAnswer(
                        question,
                        "This is a potential answer.",
                        "AnotherStudent"
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

        assertEquals(
                "AnotherStudent",
                question.getAnswers()
                        .get(0)
                        .getAuthor()
        );
    }

    /*
     * TEST 8
     *
     * An unresolved question should
     * still show its current
     * potential answers.
     */
    @Test
    public void testUnresolvedQuestionShowsPotentialAnswers() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Unresolved Question With Answers",
                        "This question should have potential answers.",
                        "Test",
                        Subject.DATA_STRUCTURES
                );

        Answer answer =
                manager.addAnswer(
                        question,
                        "This is an existing potential answer.",
                        "AnotherStudent"
                );

        assertNotNull(
                answer
        );

        ArrayList<Question> unresolved =
                manager.getUnresolvedQuestions();

        assertTrue(
                unresolved.contains(
                        question
                )
        );

        assertFalse(
                question.getAnswers()
                        .isEmpty()
        );

        assertEquals(
                1,
                question.getAnswers()
                        .size()
        );

        assertEquals(
                "This is an existing potential answer.",
                question.getAnswers()
                        .get(0)
                        .getDescription()
        );
    }

    /*
     * TEST 9
     *
     * The original question author
     * can select an accepted answer
     * and resolve the question.
     */
    @Test
    public void testQuestionAuthorCanResolve() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Resolved JUnit Question",
                        "This question will be marked solved.",
                        "Test",
                        Subject.PROGRAMMING
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
     * TEST 10
     *
     * Another student cannot resolve
     * someone else's question.
     */
    @Test
    public void testOtherStudentCannotResolve() {

        QuestionManager manager =
                new QuestionManager();

        Question question =
                manager.addQuestion(
                        "Author Permission JUnit Question",
                        "Only Test should be able to resolve this.",
                        "Test",
                        Subject.PROGRAMMING
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

    /*
     * TEST 11
     *
     * A question should not be resolved
     * using an answer that belongs to
     * another question.
     */
    @Test
    public void testCannotResolveWithAnswerFromDifferentQuestion() {

        QuestionManager manager =
                new QuestionManager();

        Question question1 =
                manager.addQuestion(
                        "Question One",
                        "First question.",
                        "Test",
                        Subject.PROGRAMMING
                );

        Question question2 =
                manager.addQuestion(
                        "Question Two",
                        "Second question.",
                        "Test",
                        Subject.PROGRAMMING
                );

        Answer answerForQuestion2 =
                manager.addAnswer(
                        question2,
                        "Answer for question two.",
                        "AnotherStudent"
                );

        boolean result =
                manager.resolveQuestion(
                        question1,
                        answerForQuestion2,
                        "Test"
                );

        assertFalse(
                result
        );

        assertFalse(
                question1.isResolved()
        );

        assertFalse(
                question1.hasAcceptedAnswer()
        );
    }
}