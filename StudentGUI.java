import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentGUI {

    private JFrame frame;
    private User user;
    private UserManager userManager;
    private QuestionManager questionManager;

    private static final Color BLUE =
            new Color(23, 101, 209);

    private static final Color DARK =
            new Color(20, 44, 82);

    private static final Color BACKGROUND =
            new Color(244, 247, 252);

    private static final Color GRAY =
            new Color(105, 115, 130);

    public StudentGUI(
            User user,
            UserManager userManager) {

        this.user = user;
        this.userManager = userManager;

        questionManager = new QuestionManager();

        frame = new JFrame(
                "Student Q&A - Student Dashboard"
        );

        frame.setSize(900, 560);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        frame.add(mainPanel);

        /*
         * LEFT SIDEBAR
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

        sidebar.setBackground(BLUE);

        sidebar.setBorder(
                new EmptyBorder(
                        35,
                        20,
                        30,
                        20
                )
        );

        /*
         * App title
         */

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

        sidebar.add(systemTitle);

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        /*
         * Student Portal label
         */

        JLabel studentLabel =
                new JLabel(
                        "Student Portal"
                );

        studentLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        studentLabel.setForeground(
                new Color(
                        220,
                        230,
                        245
                )
        );

        studentLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(studentLabel);

        sidebar.add(
                Box.createVerticalStrut(45)
        );

        /*
         * Sidebar Buttons
         */

        JButton askQuestionButton =
                createSidebarButton(
                        "Ask Question"
                );

        JButton viewQuestionsButton =
                createSidebarButton(
                        "View Questions"
                );

        JButton addAnswerButton =
                createSidebarButton(
                        "Add Answer"
                );

        JButton unresolvedButton =
                createSidebarButton(
                        "Unresolved Questions"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        sidebar.add(
                askQuestionButton
        );

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        sidebar.add(
                viewQuestionsButton
        );

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        sidebar.add(
                addAnswerButton
        );

        sidebar.add(
                Box.createVerticalStrut(12)
        );

        sidebar.add(
                unresolvedButton
        );

        /*
         * Push Logout to bottom
         */

        sidebar.add(
                Box.createVerticalGlue()
        );

        sidebar.add(
                logoutButton
        );

        /*
         * RIGHT CONTENT AREA
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

        /*
         * Dashboard title
         */

        JLabel title =
                new JLabel(
                        "Student Dashboard"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(DARK);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(title);

        contentPanel.add(
                Box.createVerticalStrut(8)
        );

        /*
         * Welcome label
         */

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back, "
                        + user.getUsername()
                );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        welcomeLabel.setForeground(GRAY);

        welcomeLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        contentPanel.add(
                welcomeLabel
        );

        contentPanel.add(
                Box.createVerticalStrut(35)
        );

        /*
         * Dashboard Card
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
                        290
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        /*
         * Card title
         */

        JLabel cardTitle =
                new JLabel(
                        "Question & Answer Center"
                );

        cardTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        cardTitle.setForeground(DARK);

        cardTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(cardTitle);

        card.add(
                Box.createVerticalStrut(10)
        );

        /*
         * Card description
         */

        JLabel description =
                new JLabel(
                        "<html>"
                        + "Ask questions, browse existing questions, "
                        + "share potential answers, and review "
                        + "unresolved discussions."
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

        card.add(description);

        card.add(
                Box.createVerticalStrut(25)
        );

        /*
         * Available tools
         */

        JLabel toolsTitle =
                new JLabel(
                        "Available Student Tools"
                );

        toolsTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        toolsTitle.setForeground(
                DARK
        );

        toolsTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(toolsTitle);

        card.add(
                Box.createVerticalStrut(12)
        );

        JLabel tools =
                new JLabel(
                        "<html>"
                        + "• Ask a new question<br>"
                        + "• Find potentially related questions<br>"
                        + "• View questions and answers<br>"
                        + "• Submit potential answers<br>"
                        + "• View unresolved questions"
                        + "</html>"
                );

        tools.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        tools.setForeground(
                GRAY
        );

        tools.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(tools);

        contentPanel.add(card);

        /*
         * ADD SIDEBAR AND CONTENT
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
         * BUTTON ACTIONS
         */

        askQuestionButton
                .addActionListener(
                        e -> askQuestion()
                );

        viewQuestionsButton
                .addActionListener(
                        e -> viewQuestions()
                );

        addAnswerButton
                .addActionListener(
                        e -> addAnswer()
                );

        unresolvedButton
                .addActionListener(
                        e ->
                                viewUnresolvedQuestions()
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

        frame.setVisible(true);
    }

    /*
     * SIDEBAR BUTTON DESIGN
     */

    private JButton createSidebarButton(
            String text) {

        JButton button =
                new JButton(text);

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

        button.setOpaque(true);

        button.setBorder(
                BorderFactory
                        .createEmptyBorder(
                                10,
                                15,
                                10,
                                15
                        )
        );

        button.setBorderPainted(false);
        button.setFocusPainted(false);

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
     * ASK QUESTION
     */

private void askQuestion() {

    JFrame askFrame =
            new JFrame("Ask a Question");

    askFrame.setSize(700, 520);
    askFrame.setLocationRelativeTo(frame);

    askFrame.setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
    );

    askFrame.setResizable(false);

    JPanel mainPanel =
            new JPanel();

    mainPanel.setLayout(
            new BoxLayout(
                    mainPanel,
                    BoxLayout.Y_AXIS
            )
    );

    mainPanel.setBackground(
            BACKGROUND
    );

    mainPanel.setBorder(
            new EmptyBorder(
                    30,
                    45,
                    30,
                    45
            )
    );

    askFrame.add(mainPanel);

    /*
     * TITLE
     */

    JLabel titleLabel =
            new JLabel(
                    "Ask a Question"
            );

    titleLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    26
            )
    );

    titleLabel.setForeground(
            DARK
    );

    titleLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    mainPanel.add(
            titleLabel
    );

    mainPanel.add(
            Box.createVerticalStrut(5)
    );

    JLabel subtitleLabel =
            new JLabel(
                    "Describe what you need help with."
            );

    subtitleLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    subtitleLabel.setForeground(
            GRAY
    );

    subtitleLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    mainPanel.add(
            subtitleLabel
    );

    mainPanel.add(
            Box.createVerticalStrut(25)
    );

    /*
     * QUESTION TITLE
     */

    JLabel questionTitleLabel =
            new JLabel(
                    "Question Title"
            );

    questionTitleLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    questionTitleLabel.setForeground(
            DARK
    );

    questionTitleLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    mainPanel.add(
            questionTitleLabel
    );

    mainPanel.add(
            Box.createVerticalStrut(7)
    );

    JTextField titleField =
            new JTextField();

    titleField.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    titleField.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    40
            )
    );

    titleField.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    mainPanel.add(
            titleField
    );

    mainPanel.add(
            Box.createVerticalStrut(20)
    );

    /*
     * DESCRIPTION
     */

    JLabel descriptionLabel =
            new JLabel(
                    "Question Description"
            );

    descriptionLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    descriptionLabel.setForeground(
            DARK
    );

    descriptionLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    mainPanel.add(
            descriptionLabel
    );

    mainPanel.add(
            Box.createVerticalStrut(7)
    );

    JTextArea descriptionArea =
            new JTextArea();

    descriptionArea.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    descriptionArea.setLineWrap(true);

    descriptionArea.setWrapStyleWord(true);

    descriptionArea.setBorder(
            new EmptyBorder(
                    8,
                    8,
                    8,
                    8
            )
    );

    JScrollPane descriptionScroll =
            new JScrollPane(
                    descriptionArea
            );

    descriptionScroll.setPreferredSize(
            new Dimension(
                    600,
                    170
            )
    );

    descriptionScroll.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    170
            )
    );

    descriptionScroll.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    mainPanel.add(
            descriptionScroll
    );

    mainPanel.add(
            Box.createVerticalStrut(25)
    );

    /*
     * BUTTON PANEL
     */

    JPanel buttonPanel =
            new JPanel(
                    new FlowLayout(
                            FlowLayout.RIGHT
                    )
            );

    buttonPanel.setBackground(
            BACKGROUND
    );

    buttonPanel.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    50
            )
    );

    buttonPanel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    JButton cancelButton =
            new JButton("Cancel");

    cancelButton.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    cancelButton.setPreferredSize(
            new Dimension(
                    110,
                    40
            )
    );

    cancelButton.setFocusPainted(false);

    JButton submitButton =
            new JButton(
                    "Submit Question"
            );

    submitButton.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    submitButton.setBackground(
            BLUE
    );

    submitButton.setForeground(
            Color.WHITE
    );

    submitButton.setFocusPainted(
            false
    );

    submitButton.setBorderPainted(
            false
    );

    submitButton.setPreferredSize(
            new Dimension(
                    160,
                    40
            )
    );

    buttonPanel.add(
            cancelButton
    );

    buttonPanel.add(
            submitButton
    );

    mainPanel.add(
            buttonPanel
    );

    /*
     * CANCEL
     */

    cancelButton.addActionListener(e -> {

        askFrame.dispose();
    });

    /*
     * SUBMIT QUESTION
     */

    submitButton.addActionListener(e -> {

        String title =
                titleField
                        .getText()
                        .trim();

        String description =
                descriptionArea
                        .getText()
                        .trim();

        /*
         * Validate fields
         */

        if (title.isEmpty()
                || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    askFrame,
                    "Please enter a title "
                    + "and description.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        /*
         * FIND RELATED QUESTIONS
         */

        ArrayList<Question> relatedQuestions =
                questionManager
                        .findRelatedQuestions(
                                title,
                                description
                        );

        if (!relatedQuestions.isEmpty()) {

            StringBuilder relatedText =
                    new StringBuilder();

            relatedText.append(
                    "Similar questions may already exist:\n\n"
            );

            for (Question related :
                    relatedQuestions) {

                relatedText.append("#")
                        .append(
                                related
                                        .getQuestionId()
                        )
                        .append(" - ")
                        .append(
                                related
                                        .getTitle()
                        )
                        .append("\n");

                relatedText.append(
                        related
                                .getDescription()
                );

                relatedText.append(
                        "\n\n"
                );
            }

            JTextArea relatedArea =
                    new JTextArea(
                            relatedText
                                    .toString(),
                            12,
                            35
                    );

            relatedArea.setEditable(
                    false
            );

            relatedArea.setLineWrap(
                    true
            );

            relatedArea.setWrapStyleWord(
                    true
            );

            JScrollPane scrollPane =
                    new JScrollPane(
                            relatedArea
                    );

            int choice =
                    JOptionPane
                            .showConfirmDialog(
                                    askFrame,
                                    scrollPane,
                                    "Related Questions Found",
                                    JOptionPane
                                            .YES_NO_OPTION,
                                    JOptionPane
                                            .INFORMATION_MESSAGE
                            );

            /*
             * NO = do not submit
             */

            if (choice !=
                    JOptionPane.YES_OPTION) {

                return;
            }
        }

        /*
         * ADD QUESTION
         */

        Question question =
                questionManager
                        .addQuestion(
                                title,
                                description,
                                user.getUsername()
                        );

        if (question != null) {

            JOptionPane.showMessageDialog(
                    askFrame,
                    "Question submitted successfully!\n\n"
                    + "Question ID: "
                    + question.getQuestionId(),
                    "Question Submitted",
                    JOptionPane.INFORMATION_MESSAGE
            );

            askFrame.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    askFrame,
                    "Unable to submit question.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    });

    /*
     * Press ENTER in title field
     * moves focus to description.
     */

    titleField.addActionListener(e -> {

        descriptionArea.requestFocus();
    });

    askFrame.setVisible(true);

    titleField.requestFocusInWindow();
}

    /*
     * VIEW ALL QUESTIONS
     */

private void viewQuestions() {

    ArrayList<Question> questions =
            questionManager.getQuestions();

    if (questions.isEmpty()) {

        JOptionPane.showMessageDialog(
                frame,
                "No questions have been submitted yet."
        );

        return;
    }

    /*
     * WINDOW
     */

    JFrame questionsFrame =
            new JFrame("View Questions");

    questionsFrame.setSize(900, 560);
    questionsFrame.setLocationRelativeTo(frame);
    questionsFrame.setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
    );

    /*
     * MAIN PANEL
     */

    JPanel mainPanel =
            new JPanel(new BorderLayout());

    mainPanel.setBackground(BACKGROUND);

    questionsFrame.add(mainPanel);

    /*
     * TOP HEADER
     */

    JPanel headerPanel =
            new JPanel();

    headerPanel.setLayout(
            new BoxLayout(
                    headerPanel,
                    BoxLayout.Y_AXIS
            )
    );

    headerPanel.setBackground(BACKGROUND);

    headerPanel.setBorder(
            new EmptyBorder(
                    25,
                    30,
                    20,
                    30
            )
    );

    JLabel title =
            new JLabel("Browse Questions");

    title.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    26
            )
    );

    title.setForeground(DARK);

    JLabel subtitle =
            new JLabel(
                    "Select a question to view its details and answers."
            );

    subtitle.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    subtitle.setForeground(GRAY);

    headerPanel.add(title);

    headerPanel.add(
            Box.createVerticalStrut(5)
    );

    headerPanel.add(subtitle);

    mainPanel.add(
            headerPanel,
            BorderLayout.NORTH
    );

    /*
     * LEFT QUESTION LIST
     */

    DefaultListModel<Question> listModel =
            new DefaultListModel<Question>();

    for (Question question : questions) {

        listModel.addElement(question);
    }

    JList<Question> questionList =
            new JList<Question>(listModel);

    questionList.setSelectionMode(
            ListSelectionModel
                    .SINGLE_SELECTION
    );

    questionList.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    questionList.setFixedCellHeight(38);

    /*
     * Custom display for each question
     */

    questionList.setCellRenderer(
            new DefaultListCellRenderer() {

                @Override
                public Component
                getListCellRendererComponent(
                        JList<?> list,
                        Object value,
                        int index,
                        boolean isSelected,
                        boolean cellHasFocus) {

                    JLabel label =
                            (JLabel)
                            super
                            .getListCellRendererComponent(
                                    list,
                                    value,
                                    index,
                                    isSelected,
                                    cellHasFocus
                            );

                    Question question =
                            (Question) value;

                    String status =
                            question.isResolved()
                                    ? "Resolved"
                                    : "Unresolved";

                    label.setText(
                            "#"
                            + question
                                    .getQuestionId()
                            + " - "
                            + question.getTitle()
                            + "  ["
                            + status
                            + "]"
                    );

                    label.setBorder(
                            new EmptyBorder(
                                    5,
                                    10,
                                    5,
                                    10
                            )
                    );

                    return label;
                }
            }
    );

    JScrollPane listScrollPane =
            new JScrollPane(
                    questionList
            );

    listScrollPane.setPreferredSize(
            new Dimension(
                    330,
                    400
            )
    );

    /*
     * RIGHT DETAIL PANEL
     */

    JPanel detailPanel =
            new JPanel();

    detailPanel.setLayout(
            new BoxLayout(
                    detailPanel,
                    BoxLayout.Y_AXIS
            )
    );

    detailPanel.setBackground(
            Color.WHITE
    );

    detailPanel.setBorder(
            new EmptyBorder(
                    25,
                    30,
                    25,
                    30
            )
    );

    JLabel questionTitleLabel =
            new JLabel(
                    "Select a question"
            );

    questionTitleLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    22
            )
    );

    questionTitleLabel.setForeground(
            DARK
    );

    questionTitleLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    JLabel authorLabel =
            new JLabel("");

    authorLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            )
    );

    authorLabel.setForeground(GRAY);

    authorLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    JLabel statusLabel =
            new JLabel("");

    statusLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            )
    );

    statusLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    JTextArea descriptionArea =
            new JTextArea();

    descriptionArea.setEditable(false);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    descriptionArea.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    descriptionArea.setBackground(
            Color.WHITE
    );

    descriptionArea.setBorder(
            BorderFactory.createTitledBorder(
                    "Description"
            )
    );

    descriptionArea.setRows(6);

    JScrollPane descriptionScroll =
            new JScrollPane(
                    descriptionArea
            );

    descriptionScroll.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    /*
     * Answers area
     */

    JTextArea answersArea =
            new JTextArea();

    answersArea.setEditable(false);
    answersArea.setLineWrap(true);
    answersArea.setWrapStyleWord(true);

    answersArea.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    answersArea.setBackground(
            Color.WHITE
    );

    answersArea.setBorder(
            BorderFactory.createTitledBorder(
                    "Potential Answers"
            )
    );

    JScrollPane answersScroll =
            new JScrollPane(
                    answersArea
            );

    answersScroll.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    /*
     * Add components to detail panel
     */

    detailPanel.add(
            questionTitleLabel
    );

    detailPanel.add(
            Box.createVerticalStrut(8)
    );

    detailPanel.add(authorLabel);

    detailPanel.add(
            Box.createVerticalStrut(5)
    );

    detailPanel.add(statusLabel);

    detailPanel.add(
            Box.createVerticalStrut(15)
    );

    detailPanel.add(
            descriptionScroll
    );

    detailPanel.add(
            Box.createVerticalStrut(15)
    );

    detailPanel.add(
            answersScroll
    );

    /*
     * SPLIT PANE
     */

    JSplitPane splitPane =
            new JSplitPane(
                    JSplitPane
                            .HORIZONTAL_SPLIT,
                    listScrollPane,
                    detailPanel
            );

    splitPane.setDividerLocation(330);

    splitPane.setResizeWeight(0.35);

    splitPane.setBorder(null);

    mainPanel.add(
            splitPane,
            BorderLayout.CENTER
    );

    /*
     * BOTTOM CLOSE BUTTON
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

    bottomPanel.setBorder(
            new EmptyBorder(
                    10,
                    20,
                    15,
                    20
            )
    );

    JButton closeButton =
            new JButton("Close");

    closeButton.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    closeButton.setBackground(BLUE);
    closeButton.setForeground(Color.WHITE);

    closeButton.setFocusPainted(false);
    closeButton.setBorderPainted(false);

    closeButton.setPreferredSize(
            new Dimension(
                    100,
                    38
            )
    );

    bottomPanel.add(closeButton);

    mainPanel.add(
            bottomPanel,
            BorderLayout.SOUTH
    );

    /*
     * QUESTION SELECTION
     */

    questionList.addListSelectionListener(e -> {

        if (e.getValueIsAdjusting()) {
            return;
        }

        Question selectedQuestion =
                questionList
                        .getSelectedValue();

        if (selectedQuestion == null) {
            return;
        }

        questionTitleLabel.setText(
                "#"
                + selectedQuestion
                        .getQuestionId()
                + " - "
                + selectedQuestion
                        .getTitle()
        );

        authorLabel.setText(
                "Asked by: "
                + selectedQuestion
                        .getAuthor()
        );

        if (selectedQuestion
                .isResolved()) {

            statusLabel.setText(
                    "Status: Resolved"
            );

            statusLabel.setForeground(
                    new Color(
                            40,
                            140,
                            80
                    )
            );

        } else {

            statusLabel.setText(
                    "Status: Unresolved"
            );

            statusLabel.setForeground(
                    new Color(
                            190,
                            80,
                            60
                    )
            );
        }

        descriptionArea.setText(
                selectedQuestion
                        .getDescription()
        );

        StringBuilder answers =
                new StringBuilder();

        if (selectedQuestion
                .getAnswers()
                .isEmpty()) {

            answers.append(
                    "No answers yet."
            );

        } else {

            int answerNumber = 1;

            for (Answer answer :
                    selectedQuestion
                            .getAnswers()) {

                answers.append(
                        answerNumber
                );

                answers.append(
                        ". "
                );

                answers.append(
                        answer
                                .getDescription()
                );

                answers.append(
                        "\n"
                );

                answers.append(
                        "   By: "
                );

                answers.append(
                        answer
                                .getAuthor()
                );

                answers.append(
                        "\n\n"
                );

                answerNumber++;
            }
        }

        answersArea.setText(
                answers.toString()
        );

        descriptionArea
                .setCaretPosition(0);

        answersArea
                .setCaretPosition(0);
    });

    /*
     * CLOSE BUTTON
     */

    closeButton.addActionListener(e -> {

        questionsFrame.dispose();
    });

    /*
     * Automatically select first question
     */

    if (!questions.isEmpty()) {

        questionList
                .setSelectedIndex(0);
    }

    questionsFrame.setVisible(true);
}

    /*
     * ADD ANSWER
     */

private void addAnswer() {

ArrayList<Question> questions =
        questionManager.getUnresolvedQuestions();

if (questions.isEmpty()) {

    JOptionPane.showMessageDialog(
            frame,
            "There are no unresolved questions to answer."
    );

    return;
}

    /*
     * WINDOW
     */

    JFrame answerFrame =
            new JFrame("Add Answer");

    answerFrame.setSize(800, 560);
    answerFrame.setLocationRelativeTo(frame);

    answerFrame.setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
    );

    answerFrame.setResizable(false);

    JPanel mainPanel =
            new JPanel(new BorderLayout());

    mainPanel.setBackground(BACKGROUND);

    answerFrame.add(mainPanel);

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

    headerPanel.setBackground(BACKGROUND);

    headerPanel.setBorder(
            new EmptyBorder(
                    25,
                    35,
                    20,
                    35
            )
    );

    JLabel title =
            new JLabel("Add a Potential Answer");

    title.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    26
            )
    );

    title.setForeground(DARK);

    JLabel subtitle =
            new JLabel(
                    "Select a question and submit your answer."
            );

    subtitle.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    subtitle.setForeground(GRAY);

    headerPanel.add(title);

    headerPanel.add(
            Box.createVerticalStrut(5)
    );

    headerPanel.add(subtitle);

    mainPanel.add(
            headerPanel,
            BorderLayout.NORTH
    );

    /*
     * CENTER PANEL
     */

    JPanel centerPanel =
            new JPanel();

    centerPanel.setLayout(
            new BoxLayout(
                    centerPanel,
                    BoxLayout.Y_AXIS
            )
    );

    centerPanel.setBackground(
            Color.WHITE
    );

    centerPanel.setBorder(
            new EmptyBorder(
                    25,
                    35,
                    25,
                    35
            )
    );

    /*
     * QUESTION SELECTOR
     */

    JLabel questionLabel =
            new JLabel("Select Question");

    questionLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    questionLabel.setForeground(DARK);

    questionLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    centerPanel.add(questionLabel);

    centerPanel.add(
            Box.createVerticalStrut(8)
    );

    JComboBox<Question> questionBox =
            new JComboBox<>(
                    questions.toArray(
                            new Question[0]
                    )
            );

    questionBox.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    40
            )
    );

    questionBox.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    centerPanel.add(questionBox);

    centerPanel.add(
            Box.createVerticalStrut(20)
    );

    /*
     * QUESTION DETAILS
     */

    JLabel detailsLabel =
            new JLabel("Question Details");

    detailsLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    detailsLabel.setForeground(DARK);

    detailsLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    centerPanel.add(detailsLabel);

    centerPanel.add(
            Box.createVerticalStrut(8)
    );

    JTextArea questionDetails =
            new JTextArea();

    questionDetails.setEditable(false);
    questionDetails.setLineWrap(true);
    questionDetails.setWrapStyleWord(true);

    questionDetails.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    questionDetails.setBackground(
            new Color(
                    248,
                    250,
                    253
            )
    );

    questionDetails.setBorder(
            new EmptyBorder(
                    10,
                    10,
                    10,
                    10
            )
    );

    JScrollPane questionScroll =
            new JScrollPane(
                    questionDetails
            );

    questionScroll.setPreferredSize(
            new Dimension(
                    700,
                    120
            )
    );

    questionScroll.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    120
            )
    );

    questionScroll.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    centerPanel.add(questionScroll);

    centerPanel.add(
            Box.createVerticalStrut(20)
    );

    /*
     * ANSWER FIELD
     */

    JLabel answerLabel =
            new JLabel("Your Answer");

    answerLabel.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    answerLabel.setForeground(DARK);

    answerLabel.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    centerPanel.add(answerLabel);

    centerPanel.add(
            Box.createVerticalStrut(8)
    );

    JTextArea answerArea =
            new JTextArea();

    answerArea.setLineWrap(true);
    answerArea.setWrapStyleWord(true);

    answerArea.setFont(
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    14
            )
    );

    answerArea.setBorder(
            new EmptyBorder(
                    10,
                    10,
                    10,
                    10
            )
    );

    JScrollPane answerScroll =
            new JScrollPane(
                    answerArea
            );

    answerScroll.setPreferredSize(
            new Dimension(
                    700,
                    130
            )
    );

    answerScroll.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    130
            )
    );

    answerScroll.setAlignmentX(
            Component.LEFT_ALIGNMENT
    );

    centerPanel.add(answerScroll);

    mainPanel.add(
            centerPanel,
            BorderLayout.CENTER
    );

    /*
     * BUTTON PANEL
     */

    JPanel buttonPanel =
            new JPanel(
                    new FlowLayout(
                            FlowLayout.RIGHT
                    )
            );

    buttonPanel.setBackground(
            BACKGROUND
    );

    buttonPanel.setBorder(
            new EmptyBorder(
                    10,
                    25,
                    15,
                    25
            )
    );

    JButton cancelButton =
            new JButton("Cancel");

    cancelButton.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    cancelButton.setPreferredSize(
            new Dimension(
                    110,
                    40
            )
    );

    cancelButton.setFocusPainted(false);

    JButton submitButton =
            new JButton("Submit Answer");

    submitButton.setFont(
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    14
            )
    );

    submitButton.setBackground(BLUE);
    submitButton.setForeground(Color.WHITE);

    submitButton.setFocusPainted(false);
    submitButton.setBorderPainted(false);

    submitButton.setPreferredSize(
            new Dimension(
                    150,
                    40
            )
    );

    buttonPanel.add(cancelButton);
    buttonPanel.add(submitButton);

    mainPanel.add(
            buttonPanel,
            BorderLayout.SOUTH
    );

    /*
     * UPDATE QUESTION DETAILS
     */

    Runnable updateQuestionDetails = () -> {

        Question selectedQuestion =
                (Question)
                questionBox.getSelectedItem();

        if (selectedQuestion == null) {

            questionDetails.setText("");
            return;
        }

        StringBuilder details =
                new StringBuilder();

        details.append("Question #")
                .append(
                        selectedQuestion
                                .getQuestionId()
                );

        details.append("\nTitle: ")
                .append(
                        selectedQuestion
                                .getTitle()
                );

        details.append("\nAsked by: ")
                .append(
                        selectedQuestion
                                .getAuthor()
                );

        details.append("\nStatus: ")
                .append(
                        selectedQuestion
                                .isResolved()
                                ? "Resolved"
                                : "Unresolved"
                );

        details.append("\n\n")
                .append(
                        selectedQuestion
                                .getDescription()
                );

        details.append(
                "\n\nCurrent Answers:\n"
        );

        if (selectedQuestion
                .getAnswers()
                .isEmpty()) {

            details.append(
                    "No answers yet."
            );

        } else {

            for (Answer answer :
                    selectedQuestion
                            .getAnswers()) {

                details.append("- ")
                        .append(
                                answer
                                        .getDescription()
                        )
                        .append(" (by ")
                        .append(
                                answer
                                        .getAuthor()
                        )
                        .append(")\n");
            }
        }

        questionDetails.setText(
                details.toString()
        );

        questionDetails.setCaretPosition(0);
    };

    /*
     * When question changes
     */

    questionBox.addActionListener(e -> {

        updateQuestionDetails.run();
    });

    /*
     * Initial question
     */

    updateQuestionDetails.run();

    /*
     * CANCEL BUTTON
     */

    cancelButton.addActionListener(e -> {

        answerFrame.dispose();
    });

    /*
     * SUBMIT ANSWER
     */

    submitButton.addActionListener(e -> {

        Question selectedQuestion =
                (Question)
                questionBox.getSelectedItem();

        if (selectedQuestion == null) {

            JOptionPane.showMessageDialog(
                    answerFrame,
                    "Please select a question."
            );

            return;
        }

        String description =
                answerArea
                        .getText()
                        .trim();

        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    answerFrame,
                    "Please enter an answer.",
                    "Missing Answer",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Answer answer =
                questionManager.addAnswer(
                        selectedQuestion,
                        description,
                        user.getUsername()
                );

        if (answer != null) {

            JOptionPane.showMessageDialog(
                    answerFrame,
                    "Answer submitted successfully!",
                    "Answer Submitted",
                    JOptionPane.INFORMATION_MESSAGE
            );

            answerFrame.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    answerFrame,
                    "Unable to submit answer.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    });

    answerFrame.setVisible(true);
}

    /*
     * VIEW UNRESOLVED QUESTIONS
     */

    private void viewUnresolvedQuestions() {

        ArrayList<Question> unresolved =
                questionManager
                        .getUnresolvedQuestions();

        if (unresolved.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "There are no "
                            + "unresolved questions."
                    );

            return;
        }

        StringBuilder output =
                new StringBuilder();

        for (Question question :
                unresolved) {

            output.append(
                    "Question #"
            );

            output.append(
                    question
                            .getQuestionId()
            );

            output.append(
                    "\nTitle: "
            );

            output.append(
                    question.getTitle()
            );

            output.append(
                    "\nAsked by: "
            );

            output.append(
                    question.getAuthor()
            );

            output.append(
                    "\nDescription: "
            );

            output.append(
                    question
                            .getDescription()
            );

            output.append(
                    "\nPotential Answers:\n"
            );

            if (question
                    .getAnswers()
                    .isEmpty()) {

                output.append(
                        "No answers yet.\n"
                );

            } else {

                for (Answer answer :
                        question
                                .getAnswers()) {

                    output.append("- ");

                    output.append(
                            answer
                                    .getDescription()
                    );

                    output.append(
                            " (by "
                    );

                    output.append(
                            answer
                                    .getAuthor()
                    );

                    output.append(
                            ")\n"
                    );
                }
            }

            output.append(
                    "\n--------------------------\n\n"
            );
        }

        JTextArea textArea =
                new JTextArea(
                        output.toString(),
                        18,
                        40
                );

        textArea.setEditable(false);

        textArea.setLineWrap(true);

        textArea
                .setWrapStyleWord(true);

        textArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );

        JOptionPane
                .showMessageDialog(
                        frame,
                        scrollPane,
                        "Unresolved Questions",
                        JOptionPane
                                .INFORMATION_MESSAGE
                );
    }
}