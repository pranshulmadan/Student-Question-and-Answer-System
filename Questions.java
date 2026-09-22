
import java.util.ArrayList;

public class Questions {

    private int questionId;
    private String title;
    private String description;
    private String author;
    private boolean resolved;

    private ArrayList<Answer> answers;

    // Constructor
    public Questions(int questionId, String title,
                     String description, String author) {

        this.questionId = questionId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.resolved = false;

        this.answers = new ArrayList<Answer>();
    }

    // Get question ID
    public int getQuestionId() {
        return questionId;
    }

    // Get question title
    public String getTitle() {
        return title;
    }

    // Get question description
    public String getDescription() {
        return description;
    }

    // Get question author
    public String getAuthor() {
        return author;
    }

    // Check if question is resolved
    public boolean isResolved() {
        return resolved;
    }

    // Change question resolution status
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    // Get potential answers
    public ArrayList<Answer> getAnswers() {
        return new ArrayList<Answer>(answers);
    }

    // Add a potential answer
    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    // Display question in the GUI
    @Override
    public String toString() {

        String status;

        if (resolved) {
            status = "Resolved";
        } else {
            status = "Unresolved";
        }

        return "#" + questionId + " - " + title
                + " (" + status + ")";
    }
}