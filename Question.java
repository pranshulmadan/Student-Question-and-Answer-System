import java.util.ArrayList;

public class Question {

    private int questionId;
    private String title;
    private String description;
    private String author;
    private boolean resolved;

    private ArrayList<Answer> answers;

    public Question(int questionId, String title,
                    String description, String author) {

        this.questionId = questionId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.resolved = false;

        this.answers = new ArrayList<Answer>();
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public ArrayList<Answer> getAnswers() {
        return new ArrayList<Answer>(answers);
    }

    @Override
    public String toString() {
        return "#" + questionId + " - " + title;
    }
}