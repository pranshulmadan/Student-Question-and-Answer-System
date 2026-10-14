public class Answer {

    private final String text;
    private final String author;

    public Answer(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return text + " (by " + author + ")";
    }
}
