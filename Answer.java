public class Answer {

    private int answerId;
    private String description;
    private String author;

    public Answer(int answerId, String description, String author) {

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

        return "Answer #" + answerId
                + " by " + author
                + ":\n" + description;
    }
}