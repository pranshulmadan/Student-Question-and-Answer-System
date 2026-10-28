import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReviewManager {

    private ArrayList<Review> reviews;
    private int nextReviewId;

    private static final String FILE_NAME =
            "reviews.txt";

    private static final Path FILE_PATH =
            findProjectFolder()
                    .resolve(FILE_NAME);

    public ReviewManager() {

        reviews =
                new ArrayList<Review>();

        nextReviewId = 1;

        System.out.println(
                "Review database: "
                + FILE_PATH
        );

        loadReviews();
    }

    /*
     * ADD REVIEW
     */

    public Review addReview(
            Answer answer,
            String description,
            String reviewer) {

        if (answer == null
                || description == null
                || description.trim().isEmpty()
                || reviewer == null
                || reviewer.trim().isEmpty()) {

            return null;
        }

        Review review =
                new Review(
                        nextReviewId,
                        answer.getAnswerId(),
                        description.trim(),
                        reviewer.trim()
                );

        reviews.add(
                review
        );

        nextReviewId++;

        saveReviews();

        return review;
    }

    /*
     * GET REVIEWS FOR ANSWER
     */

    public ArrayList<Review>
    getReviewsForAnswer(
            Answer answer) {

        ArrayList<Review> result =
                new ArrayList<Review>();

        if (answer == null) {
            return result;
        }

        for (Review review :
                reviews) {

            if (review.getAnswerId()
                    == answer.getAnswerId()) {

                result.add(
                        review
                );
            }
        }

        return result;
    }

    /*
     * GET ALL REVIEWS
     */

    public ArrayList<Review>
    getReviews() {

        return new ArrayList<Review>(
                reviews
        );
    }

    /*
     * SAVE
     */

    private void saveReviews() {

        try (BufferedWriter writer =
                Files.newBufferedWriter(
                        FILE_PATH
                )) {

            for (Review review :
                    reviews) {

                String safeDescription =
                        review.getDescription()
                                .replace(
                                        "|",
                                        "/"
                                )
                                .replace(
                                        "\n",
                                        " "
                                )
                                .replace(
                                        "\r",
                                        " "
                                );

                String safeReviewer =
                        review.getReviewer()
                                .replace(
                                        "|",
                                        "/"
                                );

                writer.write(
                        review.getReviewId()
                        + "|"
                        + review.getAnswerId()
                        + "|"
                        + safeDescription
                        + "|"
                        + safeReviewer
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving reviews: "
                    + e.getMessage()
            );
        }
    }

    /*
     * LOAD
     */

    private void loadReviews() {

        if (!Files.exists(
                FILE_PATH)) {

            System.out.println(
                    "No saved reviews found."
            );

            return;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(
                        FILE_PATH
                )) {

            String line;

            int largestId = 0;

            while ((line =
                    reader.readLine())
                    != null) {

                if (line.trim()
                        .isEmpty()) {

                    continue;
                }

                String[] parts =
                        line.split(
                                "\\|",
                                -1
                        );

                if (parts.length != 4) {

                    continue;
                }

                try {

                    int reviewId =
                            Integer.parseInt(
                                    parts[0]
                            );

                    int answerId =
                            Integer.parseInt(
                                    parts[1]
                            );

                    String description =
                            parts[2];

                    String reviewer =
                            parts[3];

                    Review review =
                            new Review(
                                    reviewId,
                                    answerId,
                                    description,
                                    reviewer
                            );

                    reviews.add(
                            review
                    );

                    if (reviewId
                            > largestId) {

                        largestId =
                                reviewId;
                    }

                } catch (
                        NumberFormatException e) {

                    System.out.println(
                            "Skipping invalid review."
                    );
                }
            }

            nextReviewId =
                    largestId + 1;

        } catch (IOException e) {

            System.out.println(
                    "Error loading reviews: "
                    + e.getMessage()
            );
        }
    }

    /*
     * FIND PROJECT FOLDER
     */

    private static Path
    findProjectFolder() {

        String projectFolder =
                System.getProperty(
                        "studentqa.projectDir"
                );

        if (projectFolder != null
                && !projectFolder
                        .isEmpty()) {

            return Paths.get(
                    projectFolder
            ).toAbsolutePath();
        }

        Path current =
                Paths.get("")
                        .toAbsolutePath()
                        .normalize();

        Path found =
                searchForRepository(
                        current
                );

        if (found != null) {
            return found;
        }

        try {

            Path classLocation =
                    Paths.get(
                            ReviewManager.class
                                    .getProtectionDomain()
                                    .getCodeSource()
                                    .getLocation()
                                    .toURI()
                    );

            Path directory =
                    Files.isDirectory(
                            classLocation
                    )
                    ? classLocation
                    : classLocation
                            .getParent();

            found =
                    searchForRepository(
                            directory
                    );

            if (found != null) {
                return found;
            }

        } catch (Exception e) {

            System.out.println(
                    "Could not determine "
                    + "class location: "
                    + e.getMessage()
            );
        }

        throw new IllegalStateException(
                "Could not locate the "
                + "project folder."
        );
    }

    private static Path
    searchForRepository(
            Path directory) {

        while (directory != null) {

            boolean hasGit =
                    Files.exists(
                            directory.resolve(
                                    ".git"
                            )
                    );

            boolean hasMain =
                    Files.exists(
                            directory.resolve(
                                    "Main.java"
                            )
                    );

            if (hasGit && hasMain) {

                return directory;
            }

            directory =
                    directory.getParent();
        }

        return null;
    }
}