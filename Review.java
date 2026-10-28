public class Review {

    private int reviewId;
    private int answerId;
    private String description;
    private String reviewer;

    public Review(
            int reviewId,
            int answerId,
            String description,
            String reviewer) {

        this.reviewId = reviewId;
        this.answerId = answerId;
        this.description = description;
        this.reviewer = reviewer;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getDescription() {
        return description;
    }

    public String getReviewer() {
        return reviewer;
    }

    @Override
    public String toString() {

        return "Review #"
                + reviewId
                + " by "
                + reviewer
                + ":\n"
                + description;
    }
}