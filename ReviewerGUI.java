import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReviewerGUI {

    private JFrame frame;

    private User reviewer;
    private UserManager userManager;

    private QuestionManager questionManager;
    private ReviewManager reviewManager;

    private static final Color BLUE =
            new Color(
                    23,
                    101,
                    209
            );

    private static final Color DARK =
            new Color(
                    20,
                    44,
                    82
            );

    private static final Color BACKGROUND =
            new Color(
                    244,
                    247,
                    252
            );

    private static final Color GRAY =
            new Color(
                    105,
                    115,
                    130
            );

    public ReviewerGUI(
            User reviewer,
            UserManager userManager) {

        this.reviewer =
                reviewer;

        this.userManager =
                userManager;

        this.questionManager =
                new QuestionManager();

        this.reviewManager =
                new ReviewManager();

        createGUI();
    }

    /*
     * MAIN GUI
     */

    private void createGUI() {

        frame =
                new JFrame(
                        "Student Q&A - Reviewer Dashboard"
                );

        frame.setSize(
                900,
                560
        );

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(
                null
        );

        frame.setResizable(
                false
        );

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        frame.add(
                mainPanel
        );

        /*
         * SIDEBAR
         */

        JPanel sidebar =
                new JPanel();

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setPreferredSize(
                new Dimension(
                        230,
                        560
                )
        );

        sidebar.setBackground(
                BLUE
        );

        sidebar.setBorder(
                new EmptyBorder(
                        35,
                        20,
                        30,
                        20
                )
        );

        JLabel systemTitle =
                new JLabel(
                        "Student Q&A"
                );

        systemTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        systemTitle.setForeground(
                Color.WHITE
        );

        systemTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                systemTitle
        );

        sidebar.add(
                Box.createVerticalStrut(
                        8
                )
        );

        JLabel reviewerLabel =
                new JLabel(
                        "Reviewer Portal"
                );

        reviewerLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        reviewerLabel.setForeground(
                new Color(
                        220,
                        230,
                        245
                )
        );

        reviewerLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                reviewerLabel
        );

        sidebar.add(
                Box.createVerticalStrut(
                        45
                )
        );

        JButton reviewAnswerButton =
                createSidebarButton(
                        "Review Answers"
                );

        JButton viewReviewsButton =
                createSidebarButton(
                        "View Reviews"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        sidebar.add(
                reviewAnswerButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );

        sidebar.add(
                viewReviewsButton
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        sidebar.add(
                logoutButton
        );

        /*
         * CONTENT
         */

        JPanel contentPanel =
                new JPanel();

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        contentPanel.setBackground(
                BACKGROUND
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        55,
                        65,
                        45,
                        65
                )
        );

        JLabel title =
                new JLabel(
                        "Reviewer Dashboard"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                DARK
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(
                title
        );

        contentPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back, "
                        + reviewer.getUsername()
                );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        welcomeLabel.setForeground(
                GRAY
        );

        welcomeLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(
                welcomeLabel
        );

        contentPanel.add(
                Box.createVerticalStrut(
                        35
                )
        );

        /*
         * CARD
         */

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory
                        .createCompoundBorder(

                                BorderFactory
                                        .createLineBorder(
                                                new Color(
                                                        220,
                                                        225,
                                                        235
                                                )
                                        ),

                                new EmptyBorder(
                                        30,
                                        35,
                                        30,
                                        35
                                )
                        )
        );

        card.setMaximumSize(
                new Dimension(
                        540,
                        260
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel cardTitle =
                new JLabel(
                        "Answer Reviews"
                );

        cardTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        cardTitle.setForeground(
                DARK
        );

        cardTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                cardTitle
        );

        card.add(
                Box.createVerticalStrut(
                        10
                )
        );

        JLabel description =
                new JLabel(
                        "<html>"
                        + "Review potential answers submitted "
                        + "by students and share your experience "
                        + "with the class."
                        + "</html>"
                );

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(
                GRAY
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                description
        );

        card.add(
                Box.createVerticalStrut(
                        25
                )
        );

        JLabel tools =
                new JLabel(
                        "<html>"
                        + "• Browse student questions<br>"
                        + "• View potential answers<br>"
                        + "• Write reviews for answers<br>"
                        + "• View existing reviews"
                        + "</html>"
                );

        tools.setForeground(
                GRAY
        );

        tools.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                tools
        );

        contentPanel.add(
                card
        );

        /*
         * MAIN PANELS
         */

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        /*
         * ACTIONS
         */

        reviewAnswerButton
                .addActionListener(
                        e ->
                                reviewAnswers()
                );

        viewReviewsButton
                .addActionListener(
                        e ->
                                viewAllReviews()
                );

        logoutButton
                .addActionListener(
                        e -> {

                            frame.dispose();

                            LoginGUI.showLogin(
                                    userManager
                            );
                        }
                );

        frame.setVisible(
                true
        );
    }

    /*
     * BUTTON STYLE
     */

    private JButton
    createSidebarButton(
            String text) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                BLUE
        );

        button.setBorder(
                BorderFactory
                        .createEmptyBorder(
                                10,
                                15,
                                10,
                                15
                        )
        );

        button.setBorderPainted(
                false
        );

        button.setFocusPainted(
                false
        );

        button.setMaximumSize(
                new Dimension(
                        190,
                        42
                )
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return button;
    }

    /*
     * REVIEW ANSWERS
     */

    private void reviewAnswers() {

        ArrayList<Question> questions =
                questionManager
                        .getQuestions();

        if (questions.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "There are no questions."
                    );

            return;
        }

        Question selectedQuestion =
                (Question)
                JOptionPane
                        .showInputDialog(
                                frame,
                                "Select a question:",
                                "Review Answers",
                                JOptionPane
                                        .QUESTION_MESSAGE,
                                null,
                                questions.toArray(),
                                questions.get(0)
                        );

        if (selectedQuestion == null) {
            return;
        }

        ArrayList<Answer> answers =
                selectedQuestion
                        .getAnswers();

        if (answers.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "This question has no answers yet."
                    );

            return;
        }

        Answer selectedAnswer =
                (Answer)
                JOptionPane
                        .showInputDialog(
                                frame,
                                "Select an answer to review:",
                                "Review Potential Answer",
                                JOptionPane
                                        .QUESTION_MESSAGE,
                                null,
                                answers.toArray(),
                                answers.get(0)
                        );

        if (selectedAnswer == null) {
            return;
        }

        /*
         * SHOW EXISTING REVIEWS
         */

        ArrayList<Review> existingReviews =
                reviewManager
                        .getReviewsForAnswer(
                                selectedAnswer
                        );

        StringBuilder existingText =
                new StringBuilder();

        if (existingReviews.isEmpty()) {

            existingText.append(
                    "No reviews have been submitted yet."
            );

        } else {

            for (Review review :
                    existingReviews) {

                existingText.append(
                        review.getReviewer()
                );

                existingText.append(
                        ": "
                );

                existingText.append(
                        review.getDescription()
                );

                existingText.append(
                        "\n\n"
                );
            }
        }

        JTextArea reviewInput =
                new JTextArea(
                        6,
                        35
                );

        reviewInput.setLineWrap(
                true
        );

        reviewInput.setWrapStyleWord(
                true
        );

        JTextArea existingArea =
                new JTextArea(
                        existingText.toString(),
                        6,
                        35
                );

        existingArea.setEditable(
                false
        );

        existingArea.setLineWrap(
                true
        );

        existingArea.setWrapStyleWord(
                true
        );

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel answerLabel =
                new JLabel(
                        "Answer by "
                        + selectedAnswer
                                .getAuthor()
                );

        answerLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        panel.add(
                answerLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        JTextArea answerArea =
                new JTextArea(
                        selectedAnswer
                                .getDescription(),
                        5,
                        35
                );

        answerArea.setEditable(
                false
        );

        answerArea.setLineWrap(
                true
        );

        answerArea.setWrapStyleWord(
                true
        );

        panel.add(
                new JScrollPane(
                        answerArea
                )
        );

        panel.add(
                Box.createVerticalStrut(
                        15
                )
        );

        panel.add(
                new JLabel(
                        "Existing Reviews"
                )
        );

        panel.add(
                new JScrollPane(
                        existingArea
                )
        );

        panel.add(
                Box.createVerticalStrut(
                        15
                )
        );

        panel.add(
                new JLabel(
                        "Your Review"
                )
        );

        panel.add(
                new JScrollPane(
                        reviewInput
                )
        );

        int result =
                JOptionPane
                        .showConfirmDialog(
                                frame,
                                panel,
                                "Review Potential Answer",
                                JOptionPane
                                        .OK_CANCEL_OPTION,
                                JOptionPane
                                        .PLAIN_MESSAGE
                        );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String description =
                reviewInput
                        .getText()
                        .trim();

        if (description.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Please enter a review."
                    );

            return;
        }

        Review review =
                reviewManager
                        .addReview(
                                selectedAnswer,
                                description,
                                reviewer
                                        .getUsername()
                        );

        if (review != null) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Review submitted successfully!"
                    );

        } else {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Unable to submit review."
                    );
        }
    }

    /*
     * VIEW ALL REVIEWS
     */

    private void viewAllReviews() {

        ArrayList<Review> reviews =
                reviewManager
                        .getReviews();

        if (reviews.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "No reviews have been submitted."
                    );

            return;
        }

        StringBuilder text =
                new StringBuilder();

        for (Review review :
                reviews) {

            text.append(
                    "Review #"
            );

            text.append(
                    review.getReviewId()
            );

            text.append(
                    "\nAnswer ID: "
            );

            text.append(
                    review.getAnswerId()
            );

            text.append(
                    "\nReviewer: "
            );

            text.append(
                    review.getReviewer()
            );

            text.append(
                    "\nReview: "
            );

            text.append(
                    review.getDescription()
            );

            text.append(
                    "\n\n"
            );
        }

        JTextArea area =
                new JTextArea(
                        text.toString(),
                        18,
                        42
                );

        area.setEditable(
                false
        );

        area.setLineWrap(
                true
        );

        area.setWrapStyleWord(
                true
        );

        area.setCaretPosition(
                0
        );

        JOptionPane
                .showMessageDialog(
                        frame,
                        new JScrollPane(
                                area
                        ),
                        "All Reviews",
                        JOptionPane
                                .INFORMATION_MESSAGE
                );
    }
}