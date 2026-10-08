import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuestionManager {

    private ArrayList<Question> questions;
    private int nextQuestionId;
    private int nextAnswerId;

    private static final String FILE_NAME = "questions.txt";
    private static final Path FILE_PATH =
            findProjectFolder().resolve(FILE_NAME);

    private static final String ANSWER_FILE_NAME = "answers.txt";
    private static final Path ANSWER_FILE_PATH =
            findProjectFolder().resolve(ANSWER_FILE_NAME);

    public QuestionManager() {

        questions = new ArrayList<Question>();
        nextQuestionId = 1;
        nextAnswerId = 1;

        System.out.println(
                "Question database: " + FILE_PATH
        );

        System.out.println(
                "Answer database: " + ANSWER_FILE_PATH
        );

        loadQuestions();
        loadAnswers();
    }

    // Find the project folder
    private static Path findProjectFolder() {

        Path current = Paths.get("")
                .toAbsolutePath()
                .normalize();

        Path found = searchForRepository(current);

        if (found != null) {
            return found;
        }

        try {

            Path classLocation = Paths.get(
                    QuestionManager.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );

            Path directory = Files.isDirectory(classLocation)
                    ? classLocation
                    : classLocation.getParent();

            found = searchForRepository(directory);

            if (found != null) {
                return found;
            }

        } catch (Exception e) {

            System.out.println(
                    "Could not determine class location: "
                    + e.getMessage()
            );
        }

        throw new IllegalStateException(
                "Could not locate the project folder."
        );
    }

    // Search for the Git repository
    private static Path searchForRepository(Path directory) {

        while (directory != null) {

            boolean hasGit =
                    Files.exists(
                            directory.resolve(".git")
                    );

            boolean hasQuestionManager =
                    Files.exists(
                            directory.resolve(
                                    "QuestionManager.java"
                            )
                    );

            boolean hasMain =
                    Files.exists(
                            directory.resolve("Main.java")
                    );

            if (hasGit
                    && hasQuestionManager
                    && hasMain) {

                return directory;
            }

            directory = directory.getParent();
        }

        return null;
    }

    // Add a new question
    public Question addQuestion(
            String title,
            String description,
            String author) {

        if (title == null
                || title.trim().isEmpty()) {

            return null;
        }

        if (description == null
                || description.trim().isEmpty()) {

            return null;
        }

        Question newQuestion = new Question(
                nextQuestionId,
                title.trim(),
                description.trim(),
                author
        );

        questions.add(newQuestion);

        nextQuestionId++;

        saveQuestions();

        return newQuestion;
    }

    // Add an answer to a question
    public Answer addAnswer(
            Question question,
            String description,
            String author) {

        if (question == null) {
            return null;
        }

        if (description == null
                || description.trim().isEmpty()) {

            return null;
        }

        Answer newAnswer = new Answer(
                nextAnswerId,
                description.trim(),
                author
        );

        question.addAnswer(newAnswer);

        nextAnswerId++;

        saveAnswers();

        return newAnswer;
    }

    // Return all questions
    public ArrayList<Question> getQuestions() {

        return new ArrayList<Question>(questions);
    }

    // Save questions to questions.txt
    private void saveQuestions() {

        try (BufferedWriter writer =
                Files.newBufferedWriter(FILE_PATH)) {

            for (Question question : questions) {

                writer.write(
                        question.getQuestionId()
                        + "|"
                        + cleanText(
                                question.getTitle()
                        )
                        + "|"
                        + cleanText(
                                question.getDescription()
                        )
                        + "|"
                        + cleanText(
                                question.getAuthor()
                        )
                        + "|"
                        + question.isResolved()
                );

                writer.newLine();
            }

            System.out.println(
                    "Questions saved to: "
                    + FILE_PATH
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving questions: "
                    + e.getMessage()
            );
        }
    }

    // Load questions from questions.txt
    private void loadQuestions() {

        if (!Files.exists(FILE_PATH)) {

            System.out.println(
                    "No saved questions found."
            );

            return;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(FILE_PATH)) {

            String line;

            int highestId = 0;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts =
                        line.split("\\|", -1);

                if (parts.length != 5) {

                    System.out.println(
                            "Skipping invalid question record."
                    );

                    continue;
                }

                int questionId =
                        Integer.parseInt(parts[0]);

                String title = parts[1];
                String description = parts[2];
                String author = parts[3];

                boolean resolved =
                        Boolean.parseBoolean(parts[4]);

                Question question = new Question(
                        questionId,
                        title,
                        description,
                        author
                );

                question.setResolved(resolved);

                questions.add(question);

                if (questionId > highestId) {
                    highestId = questionId;
                }
            }

            nextQuestionId =
                    highestId + 1;

            System.out.println(
                    "Loaded "
                    + questions.size()
                    + " questions."
            );

        } catch (IOException
                | NumberFormatException e) {

            System.out.println(
                    "Error loading questions: "
                    + e.getMessage()
            );
        }
    }

    // Save all answers to answers.txt
    private void saveAnswers() {

        try (BufferedWriter writer =
                Files.newBufferedWriter(
                        ANSWER_FILE_PATH
                )) {

            for (Question question : questions) {

                for (Answer answer :
                        question.getAnswers()) {

                    writer.write(
                            answer.getAnswerId()
                            + "|"
                            + question.getQuestionId()
                            + "|"
                            + cleanText(
                                    answer.getDescription()
                            )
                            + "|"
                            + cleanText(
                                    answer.getAuthor()
                            )
                    );

                    writer.newLine();
                }
            }

            System.out.println(
                    "Answers saved to: "
                    + ANSWER_FILE_PATH
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving answers: "
                    + e.getMessage()
            );
        }
    }

    // Load answers from answers.txt
    private void loadAnswers() {

        if (!Files.exists(
                ANSWER_FILE_PATH)) {

            System.out.println(
                    "No saved answers found."
            );

            return;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(
                        ANSWER_FILE_PATH
                )) {

            String line;

            int highestAnswerId = 0;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts =
                        line.split("\\|", -1);

                if (parts.length != 4) {

                    System.out.println(
                            "Skipping invalid answer record."
                    );

                    continue;
                }

                int answerId =
                        Integer.parseInt(parts[0]);

                int questionId =
                        Integer.parseInt(parts[1]);

                String description = parts[2];
                String author = parts[3];

                for (Question question : questions) {

                    if (question.getQuestionId()
                            == questionId) {

                        Answer answer = new Answer(
                                answerId,
                                description,
                                author
                        );

                        question.addAnswer(answer);

                        break;
                    }
                }

                if (answerId > highestAnswerId) {

                    highestAnswerId = answerId;
                }
            }

            nextAnswerId =
                    highestAnswerId + 1;

            System.out.println(
                    "Answers loaded successfully."
            );

        } catch (IOException
                | NumberFormatException e) {

            System.out.println(
                    "Error loading answers: "
                    + e.getMessage()
            );
        }
    }

    // Prevent special characters from
    // breaking the text file format
    private String cleanText(String text) {

        return text
                .replace("|", "/")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    //Finds related questions
    public ArrayList<Question> findRelatedQuestions(
        String title,
        String description) {

    ArrayList<Question> relatedQuestions =
            new ArrayList<Question>();

    String searchText =
            (title + " " + description).toLowerCase();

    String[] words = searchText.split("\\s+");

    for (Question question : questions) {

        String questionText =
                (question.getTitle()
                + " "
                + question.getDescription())
                .toLowerCase();

        for (String word : words) {

            if (word.length() < 4) {
                continue;
            }

            if (questionText.contains(word)) {

                relatedQuestions.add(question);
                break;
            }
        }
    }

    return relatedQuestions;
}
//Finds unresolved questions
public ArrayList<Question> getUnresolvedQuestions() {

    ArrayList<Question> unresolved =
            new ArrayList<Question>();

    for (Question question : questions) {

        if (!question.isResolved()) {
            unresolved.add(question);
        }
    }

    return unresolved;
}
}