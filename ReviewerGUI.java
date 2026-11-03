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
                        860,
                        440
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
                        + "Browse questions by subject, review "
                        + "potential answers, and share your "
                        + "experience with the class."
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
                        + "• Browse questions by subject<br>"
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
                Theme.statRow(
                        frame,
                        new String[] {
                                "Questions",
                                "Potential Answers",
                                "My Reviews",
                                "All Reviews"
                        },
                        () -> {

                            int questions = 0;
                            int answers = 0;
                            int mine = 0;

                            for (Question q : questionManager.getQuestions()) {
                                questions++;
                                answers += q.getAnswers().size();
                            }

                            java.util.ArrayList<Review> all =
                                    reviewManager.getReviews();

                            for (Review r : all) {

                                if (r.getReviewer().equals(reviewer.getUsername())) {
                                    mine++;
                                }
                            }

                            return new int[] {questions, answers, mine, all.size()};
                        }
                )
        );

        contentPanel.add(
                Box.createVerticalStrut(
                        22
                )
        );

        Theme.scaleFonts(
                card,
                1.35f
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

        Theme.apply(
                frame,
                true
        );

        frame.setVisible(
                true
        );
    }

    /*
     * BUTTON STYLE
     */

    private JButton createSidebarButton(
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
     *
     * First show subject tiles.
     */

    private void reviewAnswers() {

        ArrayList<Question> allQuestions =
                questionManager
                        .getQuestions();

        if (allQuestions.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "There are no questions."
                    );

            return;
        }

        JFrame subjectFrame =
                new JFrame(
                        "Choose Subject"
                );

        subjectFrame.setSize(
                760,
                560
        );

        subjectFrame.setLocationRelativeTo(
                frame
        );

        subjectFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        subjectFrame.setResizable(
                false
        );

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        subjectFrame.add(
                mainPanel
        );

        /*
         * HEADER
         */

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setBackground(
                BACKGROUND
        );

        JLabel title =
                new JLabel(
                        "Choose a Subject"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                DARK
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        headerPanel.add(
                title
        );

        headerPanel.add(
                Box.createVerticalStrut(
                        5
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Select a subject to review its answers."
                );

        subtitle.setForeground(
                GRAY
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        headerPanel.add(
                subtitle
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        /*
         * SUBJECT TILES
         */

        JPanel tilesPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                18,
                                18
                        )
                );

        tilesPanel.setBackground(
                BACKGROUND
        );

        for (Subject subject :
                Subject.values()) {

            ArrayList<Question> subjectQuestions =
                    questionManager
                            .getQuestionsBySubject(
                                    subject
                            );

            int questionCount =
                    subjectQuestions.size();

            int answerCount =
                    countAnswersForSubject(
                            subject
                    );

            JButton tile =
                    new JButton(
                            "<html><center>"
                            + "<b>"
                            + subject
                            + "</b>"
                            + "<br><br>"
                            + questionCount
                            + (questionCount == 1
                                    ? " Question"
                                    : " Questions")
                            + "<br>"
                            + answerCount
                            + (answerCount == 1
                                    ? " Answer"
                                    : " Answers")
                            + "</center></html>"
                    );

            tile.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            16
                    )
            );

            tile.setBackground(
                    Color.WHITE
            );

            tile.setForeground(
                    DARK
            );

            tile.setFocusPainted(
                    false
            );

            tile.setBorder(
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
                                            20,
                                            15,
                                            20,
                                            15
                                    )
                            )
            );

            tile.addActionListener(
                    e -> {

                        if (questionCount == 0) {

                            JOptionPane
                                    .showMessageDialog(
                                            subjectFrame,
                                            "There are no questions in "
                                            + subject
                                            + "."
                                    );

                            return;
                        }

                        reviewSubject(
                                subject
                        );
                    }
            );

            tilesPanel.add(
                    tile
            );
        }

        mainPanel.add(
                tilesPanel,
                BorderLayout.CENTER
        );

        /*
         * CLOSE
         */

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setBackground(
                BACKGROUND
        );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        closeButton
                .addActionListener(
                        e ->
                                subjectFrame.dispose()
                );

        bottomPanel.add(
                closeButton
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        Theme.apply(
                subjectFrame,
                false
        );

        subjectFrame.setVisible(
                true
        );
    }

    /*
     * COUNT ANSWERS IN SUBJECT
     */

    private int countAnswersForSubject(
            Subject subject) {

        int count = 0;

        ArrayList<Question> questions =
                questionManager
                        .getQuestionsBySubject(
                                subject
                        );

        for (Question question :
                questions) {

            count +=
                    question
                            .getAnswers()
                            .size();
        }

        return count;
    }

    /*
     * REVIEW A SUBJECT
     */

    private void reviewSubject(
            Subject subject) {

        ArrayList<Question> questions =
                questionManager
                        .getQuestionsBySubject(
                                subject
                        );

        if (questions.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "There are no questions in "
                            + subject
                            + "."
                    );

            return;
        }

        Question selectedQuestion =
                (Question)
                JOptionPane
                        .showInputDialog(
                                frame,
                                "Select a "
                                + subject
                                + " question:",
                                subject
                                + " Questions",
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

        openReviewWindow(
                selectedQuestion,
                selectedAnswer
        );
    }

    /*
     * REVIEW WINDOW
     */

    private void openReviewWindow(
            Question question,
            Answer answer) {

        ArrayList<Review> existingReviews =
                reviewManager
                        .getReviewsForAnswer(
                                answer
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

        /*
         * QUESTION DISPLAY
         */

        JTextArea questionArea =
                new JTextArea(
                        question.getTitle()
                        + "\n\n"
                        + question.getDescription(),
                        5,
                        35
                );

        questionArea.setEditable(
                false
        );

        questionArea.setLineWrap(
                true
        );

        questionArea.setWrapStyleWord(
                true
        );

        /*
         * ANSWER DISPLAY
         */

        JTextArea answerArea =
                new JTextArea(
                        answer.getDescription()
                        + "\n\nBy: "
                        + answer.getAuthor(),
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

        /*
         * EXISTING REVIEWS
         */

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

        /*
         * NEW REVIEW
         */

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

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel subjectLabel =
                new JLabel(
                        "Subject: "
                        + question.getSubject()
                );

        subjectLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        panel.add(
                subjectLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        10
                )
        );

        panel.add(
                new JLabel(
                        "Question"
                )
        );

        panel.add(
                new JScrollPane(
                        questionArea
                )
        );

        panel.add(
                Box.createVerticalStrut(
                        15
                )
        );

        panel.add(
                new JLabel(
                        "Potential Answer"
                )
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
                                answer,
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