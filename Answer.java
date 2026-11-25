import java.io.Serializable;

public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int answerId;
    private final String description;
    private final String author;

    public Answer(int answerId, String description,
                  String author) {
        this.answerId = answerId;
        this.description = description;
        this.author = author;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "#" + answerId + " - " + description;
    }
}
