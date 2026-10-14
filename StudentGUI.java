
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentGUI {

    private JFrame frame;
    private User user;
    private UserManager userManager;

    private static final ArrayList<Questions> questions =
            new ArrayList<Questions>();
    private static int nextQuestionId = 1;
    private static boolean questionsLoaded;

    private static final String QUESTIONS_FILE = "questions.txt";

    private static final Color BLUE =
            new Color(23, 101, 209);

    private static final Color DARK =
            new Color(20, 44, 82);

    private static final Color BACKGROUND =
            new Color(244, 247, 252);

    public StudentGUI(User user, UserManager userManager) {

        this.user = user;
        this.userManager = userManager;
        loadQuestions();

        // Create window
        frame = new JFrame("Student Home");

        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        panel.setBackground(BACKGROUND);

        panel.setBorder(
                new EmptyBorder(40, 50, 40, 50)
        );

        frame.add(panel);

        // Title
        JLabel title = new JLabel("Student Dashboard");

        title.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        title.setForeground(DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);

        panel.add(Box.createVerticalStrut(15));

        // Welcome message
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + user.getUsername()
        );

        welcomeLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        welcomeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(welcomeLabel);

        panel.add(Box.createVerticalStrut(35));

        // Ask Question button
        JButton askButton = createButton("Ask Question");

        panel.add(askButton);

        panel.add(Box.createVerticalStrut(15));

        // View Questions button
        JButton viewButton = createButton("View Questions");

        panel.add(viewButton);

        panel.add(Box.createVerticalStrut(15));

        // Unresolved Questions button
        JButton unresolvedButton =
                createButton("Unresolved Questions");

        panel.add(unresolvedButton);

        panel.add(Box.createVerticalStrut(15));

        // Logout button
        JButton logoutButton = createButton("Logout");

        panel.add(logoutButton);

        askButton.addActionListener(e -> askQuestion());
        viewButton.addActionListener(e -> viewQuestions(false));
        unresolvedButton.addActionListener(e -> viewQuestions(true));

        // Logout action
        logoutButton.addActionListener(e -> {

            frame.dispose();

            LoginGUI.showLogin(userManager);
        });

        frame.setVisible(true);
    }

    private void askQuestion() {

        JTextField titleField = new JTextField();
        JTextArea descriptionArea = new JTextArea(5, 25);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.NORTHWEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        form.add(new JLabel("Question:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        form.add(titleField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        form.add(new JLabel("Details:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        form.add(new JScrollPane(descriptionArea), constraints);

        form.setPreferredSize(new Dimension(400, 180));

        int result = JOptionPane.showConfirmDialog(
                frame,
                form,
                "Ask a Question",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please enter a question.",
                    "Question Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Please enter details before posting the question.",
                    "Details Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Questions question = new Questions(
                nextQuestionId++,
                title,
                description,
                user.getUsername()
        );
        questions.add(question);
        saveQuestions();

        showPotentialAnswers(question);
    }

    private void showPotentialAnswers(Questions question) {

        List<Answer> answers = findPotentialAnswers(question);
        StringBuilder message = new StringBuilder();

        if (answers.isEmpty()) {
            message.append(
                    "Your question was posted successfully.\n\n"
                    + "There are no potential answers yet. "
                    + "Check View Questions later for community replies."
            );
        } else {
            message.append("Potential answers from other students:\n\n");

            for (Answer answer : answers) {
                message.append("- ")
                        .append(answer.getText())
                        .append("\n  (by ")
                        .append(answer.getAuthor())
                        .append(")\n\n");
            }
        }

        JTextArea answerArea = new JTextArea(message.toString(), 12, 42);
        answerArea.setEditable(false);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);

        JOptionPane.showMessageDialog(
                frame,
                new JScrollPane(answerArea),
                "Potential Answers",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private List<Answer> findPotentialAnswers(Questions question) {

        ArrayList<Answer> matches = new ArrayList<Answer>();
        String questionText = (
                question.getTitle() + " "
                + question.getDescription()
        ).toLowerCase();

        for (Questions existing : questions) {
            if (existing == question) {
                continue;
            }

            String existingText = (
                    existing.getTitle() + " "
                    + existing.getDescription()
            ).toLowerCase();

            if (hasCommonKeyword(questionText, existingText)) {
                matches.addAll(existing.getAnswers());
            }
        }

        return matches;
    }

    private boolean hasCommonKeyword(String first, String second) {

        String[] words = first.split("\\W+");

        for (String word : words) {
            if (word.length() >= 4 && second.contains(word)) {
                return true;
            }
        }

        return false;
    }

    private void viewQuestions(boolean unresolvedOnly) {

        ArrayList<Questions> visibleQuestions =
                new ArrayList<Questions>();

        for (Questions question : questions) {
            if (!unresolvedOnly || !question.isResolved()) {
                visibleQuestions.add(question);
            }
        }

        if (visibleQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    unresolvedOnly
                            ? "There are no unresolved questions."
                            : "No questions have been posted yet.",
                    "Questions",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        Questions selected = (Questions) JOptionPane.showInputDialog(
                frame,
                "Select a question to view answers or add a reply:",
                unresolvedOnly
                        ? "Unresolved Questions"
                        : "All Questions",
                JOptionPane.QUESTION_MESSAGE,
                null,
                visibleQuestions.toArray(),
                visibleQuestions.get(0)
        );

        if (selected == null) {
            return;
        }

        showQuestion(selected);
    }

    private void showQuestion(Questions question) {

        StringBuilder details = new StringBuilder();
        details.append(question.getTitle())
                .append("\nAsked by: ")
                .append(question.getAuthor())
                .append("\n\n")
                .append(question.getDescription())
                .append("\n\nPotential answers:\n");

        ArrayList<Answer> answers = question.getAnswers();
        if (answers.isEmpty()) {
            details.append("No answers yet.");
        } else {
            for (Answer answer : answers) {
                details.append("- ")
                        .append(answer)
                        .append("\n");
            }
        }

        JTextArea questionArea = new JTextArea(details.toString(), 14, 45);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);

        Object[] options = {"Add Answer", "Close"};
        int choice = JOptionPane.showOptionDialog(
                frame,
                new JScrollPane(questionArea),
                "Question #" + question.getQuestionId(),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == 0) {
            addAnswer(question);
        }
    }

    private void addAnswer(Questions question) {

        String answerText = JOptionPane.showInputDialog(
                frame,
                "Enter a potential answer:",
                "Add Answer",
                JOptionPane.PLAIN_MESSAGE
        );

        if (answerText == null || answerText.trim().isEmpty()) {
            return;
        }

        question.addAnswer(new Answer(
                answerText.trim(),
                user.getUsername()
        ));
        saveQuestions();

        JOptionPane.showMessageDialog(
                frame,
                "Your answer was added for other students to review.",
                "Answer Added",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static void loadQuestions() {

        if (questionsLoaded) {
            return;
        }

        Path file = getQuestionsFile();

        if (!Files.exists(file)) {
            questionsLoaded = true;
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                file, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {
                loadQuestionRecord(line);
            }

            questionsLoaded = true;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not load saved questions from " + file, e
            );
        }
    }

    private static void loadQuestionRecord(String line) {

        String[] parts = line.split("\\|", -1);

        if (parts.length == 6 && parts[0].equals("QUESTION")) {
            try {
                int questionId = Integer.parseInt(parts[1]);
                Questions question = new Questions(
                        questionId,
                        decode(parts[3]),
                        decode(parts[4]),
                        decode(parts[5])
                );
                question.setResolved(Boolean.parseBoolean(parts[2]));
                questions.add(question);
                nextQuestionId = Math.max(
                        nextQuestionId, questionId + 1
                );
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Skipping invalid saved question record."
                );
            }
        } else if (parts.length == 4
                && parts[0].equals("ANSWER")) {
            try {
                int questionId = Integer.parseInt(parts[1]);
                Questions question = findQuestion(questionId);

                if (question != null) {
                    question.addAnswer(new Answer(
                            decode(parts[3]),
                            decode(parts[2])
                    ));
                }
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Skipping invalid saved answer record."
                );
            }
        }
    }

    private static Questions findQuestion(int questionId) {

        for (Questions question : questions) {
            if (question.getQuestionId() == questionId) {
                return question;
            }
        }

        return null;
    }

    private static void saveQuestions() {

        Path file = getQuestionsFile();

        try (BufferedWriter writer = Files.newBufferedWriter(
                file, StandardCharsets.UTF_8)) {

            for (Questions question : questions) {
                writer.write(
                        "QUESTION|"
                        + question.getQuestionId()
                        + "|"
                        + question.isResolved()
                        + "|"
                        + encode(question.getAuthor())
                        + "|"
                        + encode(question.getTitle())
                        + "|"
                        + encode(question.getDescription())
                );
                writer.newLine();

                for (Answer answer : question.getAnswers()) {
                    writer.write(
                            "ANSWER|"
                            + question.getQuestionId()
                            + "|"
                            + encode(answer.getAuthor())
                            + "|"
                            + encode(answer.getText())
                    );
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "The question could not be saved.\n"
                    + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(
                value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static String decode(String value) {
        return new String(
                Base64.getDecoder().decode(value),
                StandardCharsets.UTF_8
        );
    }

    private static Path getQuestionsFile() {

        String projectFolder = System.getProperty(
                "studentqa.projectDir"
        );

        Path directory = projectFolder == null
                || projectFolder.trim().isEmpty()
                ? Paths.get("").toAbsolutePath()
                : Paths.get(projectFolder).toAbsolutePath();

        return directory.resolve(QUESTIONS_FILE);
    }

    // Create matching blue buttons
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        button.setBackground(BLUE);
        button.setForeground(Color.WHITE);

        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setMaximumSize(
                new Dimension(350, 42)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return button;
    }
}