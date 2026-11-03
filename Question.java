import java.util.ArrayList;

public class Question {

    private int questionId;
    private String title;
    private String description;
    private String author;

    private boolean resolved;
    private int acceptedAnswerId;

    private Subject subject;

    private ArrayList<Answer> answers;

    /*
     * MAIN CONSTRUCTOR WITH SUBJECT
     */

    public Question(
            int questionId,
            String title,
            String description,
            String author,
            Subject subject) {

        this.questionId =
                questionId;

        this.title =
                title;

        this.description =
                description;

        this.author =
                author;

        /*
         * If no subject is provided,
         * automatically use Miscellaneous.
         */

        if (subject == null) {

            this.subject =
                    Subject.MISCELLANEOUS;

        } else {

            this.subject =
                    subject;
        }

        this.resolved =
                false;

        this.acceptedAnswerId =
                -1;

        this.answers =
                new ArrayList<Answer>();
    }

    /*
     * OLD CONSTRUCTOR
     *
     * Keeps your existing code
     * and JUnit tests working.
     *
     * Questions created without
     * a subject automatically become
     * Miscellaneous.
     */

    public Question(
            int questionId,
            String title,
            String description,
            String author) {

        this(
                questionId,
                title,
                description,
                author,
                Subject.MISCELLANEOUS
        );
    }

    /*
     * GET QUESTION ID
     */

    public int getQuestionId() {

        return questionId;
    }

    /*
     * GET TITLE
     */

    public String getTitle() {

        return title;
    }

    /*
     * GET DESCRIPTION
     */

    public String getDescription() {

        return description;
    }

    /*
     * GET AUTHOR
     */

    public String getAuthor() {

        return author;
    }

    /*
     * GET SUBJECT
     */

    public Subject getSubject() {

        return subject;
    }

    /*
     * RESOLVED STATUS
     */

    public boolean isResolved() {

        return resolved;
    }

    public void setResolved(
            boolean resolved) {

        this.resolved =
                resolved;
    }

    /*
     * ACCEPTED ANSWER
     */

    public int getAcceptedAnswerId() {

        return acceptedAnswerId;
    }

    public void setAcceptedAnswerId(
            int acceptedAnswerId) {

        this.acceptedAnswerId =
                acceptedAnswerId;
    }

    public boolean hasAcceptedAnswer() {

        return acceptedAnswerId
                != -1;
    }

    /*
     * ADD ANSWER
     */

    public void addAnswer(
            Answer answer) {

        if (answer != null) {

            answers.add(
                    answer
            );
        }
    }

    /*
     * GET ANSWERS
     */

    public ArrayList<Answer> getAnswers() {

        return new ArrayList<Answer>(
                answers
        );
    }

    /*
     * DISPLAY QUESTION
     */

    @Override
    public String toString() {

        return "#"
                + questionId
                + " - "
                + title;
    }
}