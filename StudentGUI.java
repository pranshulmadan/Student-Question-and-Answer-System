import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentGUI {

    private JFrame frame;

    private User user;
    private UserManager userManager;

    private QuestionManager questionManager;
    private ReviewManager reviewManager;

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

        this.user =
                user;

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
                        "Student Q&A - Student Dashboard"
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

        sidebar.add(
                studentLabel
        );

        sidebar.add(
                Box.createVerticalStrut(
                        40
                )
        );

        /*
         * BUTTONS
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

        JButton markSolvedButton =
                createSidebarButton(
                        "Mark Solved"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        sidebar.add(
                askQuestionButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );

        sidebar.add(
                viewQuestionsButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );

        sidebar.add(
                addAnswerButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );

        sidebar.add(
                unresolvedButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );

        sidebar.add(
                markSolvedButton
        );

        sidebar.add(
                Box.createVerticalGlue()
        );

        sidebar.add(
                logoutButton
        );

        /*
         * RIGHT CONTENT
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
                        "Student Dashboard"
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
                        + user.getUsername()
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
                        300
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel cardTitle =
                new JLabel(
                        "Student Question & Answer"
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
                        + "Ask questions by subject, review existing answers, "
                        + "read reviewer feedback, and help other students "
                        + "by submitting potential answers."
                        + "</html>"
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
                        + "• Ask questions by subject<br>"
                        + "• Browse questions by subject<br>"
                        + "• Find related questions<br>"
                        + "• View potential answers<br>"
                        + "• Read reviewer feedback<br>"
                        + "• Add potential answers<br>"
                        + "• Resolve your own questions"
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
                        e -> viewUnresolvedQuestions()
                );

        markSolvedButton
                .addActionListener(
                        e -> markQuestionSolved()
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
     * SIDEBAR BUTTON
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
     * ASK QUESTION
     */

    private void askQuestion() {

        JFrame askFrame =
                new JFrame(
                        "Ask Question"
                );

        askFrame.setSize(
                600,
                560
        );

        askFrame.setLocationRelativeTo(
                frame
        );

        askFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        askFrame.setResizable(
                false
        );

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                BACKGROUND
        );

        panel.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        askFrame.add(
                panel
        );

        /*
         * TITLE
         */

        JLabel titleLabel =
                new JLabel(
                        "Question Title"
                );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(
                titleLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        JTextField titleField =
                new JTextField();

        titleField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        titleField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(
                titleField
        );

        panel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        /*
         * SUBJECT
         */

        JLabel subjectLabel =
                new JLabel(
                        "Subject"
                );

        subjectLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(
                subjectLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        JComboBox<Subject> subjectBox =
                new JComboBox<Subject>(
                        Subject.values()
                );

        subjectBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        subjectBox.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        /*
         * Default option
         */

        subjectBox.setSelectedItem(
                Subject.MISCELLANEOUS
        );

        panel.add(
                subjectBox
        );

        panel.add(
                Box.createVerticalStrut(
                        18
                )
        );

        /*
         * DESCRIPTION
         */

        JLabel descriptionLabel =
                new JLabel(
                        "Question Description"
                );

        descriptionLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(
                descriptionLabel
        );

        panel.add(
                Box.createVerticalStrut(
                        6
                )
        );

        JTextArea descriptionArea =
                new JTextArea(
                        8,
                        40
                );

        descriptionArea.setLineWrap(
                true
        );

        descriptionArea.setWrapStyleWord(
                true
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        descriptionArea
                );

        scrollPane.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(
                scrollPane
        );

        panel.add(
                Box.createVerticalStrut(
                        20
                )
        );

        JButton submitButton =
                new JButton(
                        "Submit Question"
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
                        150,
                        40
                )
        );

        submitButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        panel.add(
                submitButton
        );

        /*
         * SUBMIT
         */

        submitButton.addActionListener(
                e -> {

                    String title =
                            titleField
                                    .getText()
                                    .trim();

                    String description =
                            descriptionArea
                                    .getText()
                                    .trim();

                    Subject subject =
                            (Subject)
                            subjectBox
                                    .getSelectedItem();

                    if (title.isEmpty()
                            || description.isEmpty()) {

                        JOptionPane
                                .showMessageDialog(
                                        askFrame,
                                        "Please enter both a title and description."
                                );

                        return;
                    }

                    if (subject == null) {

                        subject =
                                Subject.MISCELLANEOUS;
                    }

                    /*
                     * RELATED QUESTIONS
                     *
                     * Only searches questions
                     * from the same subject.
                     */

                    ArrayList<Question> related =
                            questionManager
                                    .findRelatedQuestions(
                                            title,
                                            description,
                                            subject
                                    );

                    if (!related.isEmpty()) {

                        StringBuilder relatedText =
                                new StringBuilder();

                        relatedText.append(
                                "Similar questions in "
                        );

                        relatedText.append(
                                subject.toString()
                        );

                        relatedText.append(
                                " already exist:\n\n"
                        );

                        for (Question question :
                                related) {

                            relatedText.append(
                                    question.toString()
                            );

                            relatedText.append(
                                    "\n"
                            );
                        }

                        relatedText.append(
                                "\nDo you still want to submit your question?"
                        );

                        int result =
                                JOptionPane
                                        .showConfirmDialog(
                                                askFrame,
                                                relatedText.toString(),
                                                "Related Questions Found",
                                                JOptionPane.YES_NO_OPTION
                                        );

                        if (result !=
                                JOptionPane.YES_OPTION) {

                            return;
                        }
                    }

                    /*
                     * SAVE QUESTION
                     */

                    Question question =
                            questionManager
                                    .addQuestion(
                                            title,
                                            description,
                                            user.getUsername(),
                                            subject
                                    );

                    if (question != null) {

                        JOptionPane
                                .showMessageDialog(
                                        askFrame,
                                        "Question submitted successfully!\n"
                                        + "Subject: "
                                        + subject
                                );

                        askFrame.dispose();

                    } else {

                        JOptionPane
                                .showMessageDialog(
                                        askFrame,
                                        "Unable to submit question."
                                );
                    }
                }
        );

        askFrame.setVisible(
                true
        );
    }

    /*
     * VIEW QUESTION SUBJECT TILES
     */

    private void viewQuestions() {

        ArrayList<Question> questions =
                questionManager
                        .getQuestions();

        if (questions.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "There are no questions yet."
                    );

            return;
        }

        JFrame subjectFrame =
                new JFrame(
                        "Browse Questions by Subject"
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
                        "Browse by Subject"
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
                        "Choose a subject to view its questions."
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

            int count =
                    subjectQuestions.size();

            JButton tile =
                    new JButton(
                            "<html><center>"
                            + "<b>"
                            + subject.toString()
                            + "</b>"
                            + "<br><br>"
                            + count
                            + (count == 1
                                    ? " Question"
                                    : " Questions")
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

                        if (count == 0) {

                            JOptionPane
                                    .showMessageDialog(
                                            subjectFrame,
                                            "There are no questions in "
                                            + subject
                                            + " yet."
                                    );

                            return;
                        }

                        viewQuestionsForSubject(
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

        subjectFrame.setVisible(
                true
        );
    }

    /*
     * VIEW QUESTIONS FOR ONE SUBJECT
     */

    private void viewQuestionsForSubject(
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

        JFrame questionsFrame =
                new JFrame(
                        subject
                        + " Questions"
                );

        questionsFrame.setSize(
                850,
                600
        );

        questionsFrame.setLocationRelativeTo(
                frame
        );

        questionsFrame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        questionsFrame.add(
                mainPanel
        );

        /*
         * SUBJECT HEADER
         */

        JLabel subjectTitle =
                new JLabel(
                        subject
                        + " Questions"
                );

        subjectTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        subjectTitle.setForeground(
                DARK
        );

        mainPanel.add(
                subjectTitle,
                BorderLayout.NORTH
        );

        /*
         * QUESTION LIST
         */

        DefaultListModel<Question> model =
                new DefaultListModel<Question>();

        for (Question question :
                questions) {

            model.addElement(
                    question
            );
        }

        JList<Question> questionList =
                new JList<Question>(
                        model
                );

        questionList.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        JScrollPane listScroll =
                new JScrollPane(
                        questionList
                );

        listScroll.setPreferredSize(
                new Dimension(
                        280,
                        450
                )
        );

        mainPanel.add(
                listScroll,
                BorderLayout.WEST
        );

        /*
         * DETAILS
         */

        JPanel detailsPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        detailsPanel.setBackground(
                Color.WHITE
        );

        detailsPanel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JTextArea questionArea =
                new JTextArea();

        questionArea.setEditable(
                false
        );

        questionArea.setLineWrap(
                true
        );

        questionArea.setWrapStyleWord(
                true
        );

        questionArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JTextArea answersArea =
                new JTextArea();

        answersArea.setEditable(
                false
        );

        answersArea.setLineWrap(
                true
        );

        answersArea.setWrapStyleWord(
                true
        );

        answersArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        JPanel textPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                10,
                                10
                        )
                );

        textPanel.add(
                new JScrollPane(
                        questionArea
                )
        );

        textPanel.add(
                new JScrollPane(
                        answersArea
                )
        );

        detailsPanel.add(
                textPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                detailsPanel,
                BorderLayout.CENTER
        );

        /*
         * QUESTION SELECTION
         */

        questionList
                .addListSelectionListener(
                        e -> {

                            if (e.getValueIsAdjusting()) {

                                return;
                            }

                            Question selectedQuestion =
                                    questionList
                                            .getSelectedValue();

                            if (selectedQuestion == null) {

                                return;
                            }

                            questionArea.setText(
                                    "Question #"
                                    + selectedQuestion
                                            .getQuestionId()
                                    + "\n\nSubject: "
                                    + selectedQuestion
                                            .getSubject()
                                    + "\n\n"
                                    + selectedQuestion
                                            .getTitle()
                                    + "\n\n"
                                    + selectedQuestion
                                            .getDescription()
                                    + "\n\nAsked by: "
                                    + selectedQuestion
                                            .getAuthor()
                                    + "\nStatus: "
                                    + (selectedQuestion
                                            .isResolved()
                                            ? "Resolved"
                                            : "Unresolved")
                            );

                            refreshAnswersDisplay(
                                    selectedQuestion,
                                    answersArea
                            );
                        }
                );

        /*
         * BOTTOM BUTTONS
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

        JButton addAnswerButton =
                new JButton(
                        "Add Answer"
                );

        addAnswerButton.setBackground(
                DARK
        );

        addAnswerButton.setForeground(
                Color.WHITE
        );

        addAnswerButton.setFocusPainted(
                false
        );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        bottomPanel.add(
                addAnswerButton
        );

        bottomPanel.add(
                closeButton
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        /*
         * ADD ANSWER
         */

        addAnswerButton
                .addActionListener(
                        e -> {

                            Question selectedQuestion =
                                    questionList
                                            .getSelectedValue();

                            if (selectedQuestion == null) {

                                JOptionPane
                                        .showMessageDialog(
                                                questionsFrame,
                                                "Please select a question first."
                                        );

                                return;
                            }

                            if (selectedQuestion
                                    .isResolved()) {

                                JOptionPane
                                        .showMessageDialog(
                                                questionsFrame,
                                                "This question has already been resolved."
                                        );

                                return;
                            }

                            JTextArea answerInput =
                                    new JTextArea(
                                            6,
                                            35
                                    );

                            answerInput.setLineWrap(
                                    true
                            );

                            answerInput.setWrapStyleWord(
                                    true
                            );

                            int result =
                                    JOptionPane
                                            .showConfirmDialog(
                                                    questionsFrame,
                                                    new JScrollPane(
                                                            answerInput
                                                    ),
                                                    "Add Potential Answer",
                                                    JOptionPane.OK_CANCEL_OPTION,
                                                    JOptionPane.PLAIN_MESSAGE
                                            );

                            if (result !=
                                    JOptionPane.OK_OPTION) {

                                return;
                            }

                            String description =
                                    answerInput
                                            .getText()
                                            .trim();

                            if (description.isEmpty()) {

                                JOptionPane
                                        .showMessageDialog(
                                                questionsFrame,
                                                "Please enter an answer."
                                        );

                                return;
                            }

                            Answer answer =
                                    questionManager
                                            .addAnswer(
                                                    selectedQuestion,
                                                    description,
                                                    user.getUsername()
                                            );

                            if (answer != null) {

                                JOptionPane
                                        .showMessageDialog(
                                                questionsFrame,
                                                "Answer added successfully!"
                                        );

                                refreshAnswersDisplay(
                                        selectedQuestion,
                                        answersArea
                                );

                            } else {

                                JOptionPane
                                        .showMessageDialog(
                                                questionsFrame,
                                                "Unable to add answer."
                                        );
                            }
                        }
                );

        closeButton
                .addActionListener(
                        e ->
                                questionsFrame.dispose()
                );

        if (!questions.isEmpty()) {

            questionList
                    .setSelectedIndex(
                            0
                    );
        }

        questionsFrame.setVisible(
                true
        );
    }

    /*
     * DISPLAY ANSWERS + REVIEWS
     */

    private void refreshAnswersDisplay(
            Question question,
            JTextArea answersArea) {

        ArrayList<Answer> answers =
                question.getAnswers();

        if (answers.isEmpty()) {

            answersArea.setText(
                    "No potential answers yet."
            );

            return;
        }

        StringBuilder text =
                new StringBuilder();

        int answerNumber = 1;

        for (Answer answer :
                answers) {

            if (answer.getAnswerId()
                    == question
                            .getAcceptedAnswerId()) {

                text.append(
                        "✓ ACCEPTED ANSWER\n"
                );
            }

            text.append(
                    "Answer "
            );

            text.append(
                    answerNumber
            );

            text.append(
                    "\n"
            );

            text.append(
                    answer.getDescription()
            );

            text.append(
                    "\nBy: "
            );

            text.append(
                    answer.getAuthor()
            );

            text.append(
                    "\n"
            );

            /*
             * REVIEWS
             */

            ArrayList<Review> reviews =
                    reviewManager
                            .getReviewsForAnswer(
                                    answer
                            );

            if (!reviews.isEmpty()) {

                text.append(
                        "\nReviewer Feedback:\n"
                );

                for (Review review :
                        reviews) {

                    text.append(
                            "• "
                    );

                    text.append(
                            review.getReviewer()
                    );

                    text.append(
                            ": "
                    );

                    text.append(
                            review.getDescription()
                    );

                    text.append(
                            "\n"
                    );
                }
            }

            text.append(
                    "\n------------------------------\n\n"
            );

            answerNumber++;
        }

        answersArea.setText(
                text.toString()
        );

        answersArea.setCaretPosition(
                0
        );
    }

    /*
     * ADD ANSWER
     */

    private void addAnswer() {

        ArrayList<Question> questions =
                questionManager
                        .getUnresolvedQuestions();

        if (questions.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "There are no unresolved questions."
                    );

            return;
        }

        Question selectedQuestion =
                (Question)
                JOptionPane
                        .showInputDialog(
                                frame,
                                "Select a question:",
                                "Add Answer",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                questions.toArray(),
                                questions.get(0)
                        );

        if (selectedQuestion == null) {

            return;
        }

        StringBuilder existing =
                new StringBuilder();

        existing.append(
                "Subject: "
        );

        existing.append(
                selectedQuestion
                        .getSubject()
        );

        existing.append(
                "\n\n"
        );

        ArrayList<Answer> existingAnswers =
                selectedQuestion
                        .getAnswers();

        if (existingAnswers.isEmpty()) {

            existing.append(
                    "No answers yet."
            );

        } else {

            int number = 1;

            for (Answer answer :
                    existingAnswers) {

                existing.append(
                        number
                );

                existing.append(
                        ". "
                );

                existing.append(
                        answer.getDescription()
                );

                existing.append(
                        "\nBy: "
                );

                existing.append(
                        answer.getAuthor()
                );

                existing.append(
                        "\n\n"
                );

                number++;
            }
        }

        JTextArea existingArea =
                new JTextArea(
                        existing.toString(),
                        8,
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

        JTextArea answerArea =
                new JTextArea(
                        6,
                        35
                );

        answerArea.setLineWrap(
                true
        );

        answerArea.setWrapStyleWord(
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

        panel.add(
                new JLabel(
                        "Current Potential Answers"
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
                        "Your Answer"
                )
        );

        panel.add(
                new JScrollPane(
                        answerArea
                )
        );

        int result =
                JOptionPane
                        .showConfirmDialog(
                                frame,
                                panel,
                                "Add Answer",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String description =
                answerArea
                        .getText()
                        .trim();

        if (description.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Please enter an answer."
                    );

            return;
        }

        Answer answer =
                questionManager
                        .addAnswer(
                                selectedQuestion,
                                description,
                                user.getUsername()
                        );

        if (answer != null) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Answer added successfully!"
                    );

        } else {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Unable to add answer."
                    );
        }
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
                            "There are no unresolved questions."
                    );

            return;
        }

        StringBuilder text =
                new StringBuilder();

        for (Question question :
                unresolved) {

            text.append(
                    "Question #"
            );

            text.append(
                    question.getQuestionId()
            );

            text.append(
                    ": "
            );

            text.append(
                    question.getTitle()
            );

            text.append(
                    "\nSubject: "
            );

            text.append(
                    question.getSubject()
            );

            text.append(
                    "\nAsked by: "
            );

            text.append(
                    question.getAuthor()
            );

            text.append(
                    "\n"
            );

            ArrayList<Answer> answers =
                    question.getAnswers();

            if (answers.isEmpty()) {

                text.append(
                        "No potential answers yet."
                );

            } else {

                text.append(
                        "Potential Answers:\n"
                );

                int number = 1;

                for (Answer answer :
                        answers) {

                    text.append(
                            number
                    );

                    text.append(
                            ". "
                    );

                    text.append(
                            answer.getDescription()
                    );

                    text.append(
                            "\n   By: "
                    );

                    text.append(
                            answer.getAuthor()
                    );

                    ArrayList<Review> reviews =
                            reviewManager
                                    .getReviewsForAnswer(
                                            answer
                                    );

                    if (!reviews.isEmpty()) {

                        text.append(
                                "\n   Reviewer Feedback:"
                        );

                        for (Review review :
                                reviews) {

                            text.append(
                                    "\n   • "
                            );

                            text.append(
                                    review.getReviewer()
                            );

                            text.append(
                                    ": "
                            );

                            text.append(
                                    review.getDescription()
                            );
                        }
                    }

                    text.append(
                            "\n\n"
                    );

                    number++;
                }
            }

            text.append(
                    "\n============================\n\n"
            );
        }

        JTextArea area =
                new JTextArea(
                        text.toString(),
                        22,
                        48
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
                        "Unresolved Questions",
                        JOptionPane.INFORMATION_MESSAGE
                );
    }

    /*
     * MARK QUESTION SOLVED
     */

    private void markQuestionSolved() {

        ArrayList<Question> ownQuestions =
                new ArrayList<Question>();

        for (Question question :
                questionManager
                        .getUnresolvedQuestions()) {

            if (question
                    .getAuthor()
                    .equals(
                            user.getUsername()
                    )) {

                ownQuestions.add(
                        question
                );
            }
        }

        if (ownQuestions.isEmpty()) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "You do not have any unresolved questions."
                    );

            return;
        }

        Question selectedQuestion =
                (Question)
                JOptionPane
                        .showInputDialog(
                                frame,
                                "Select one of your questions:",
                                "Mark Question Solved",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                ownQuestions.toArray(),
                                ownQuestions.get(0)
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
                                "Select the accepted answer:",
                                "Accepted Answer",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                answers.toArray(),
                                answers.get(0)
                        );

        if (selectedAnswer == null) {

            return;
        }

        int confirm =
                JOptionPane
                        .showConfirmDialog(
                                frame,
                                "Mark this question as solved "
                                + "and accept the selected answer?",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION
                        );

        if (confirm !=
                JOptionPane.YES_OPTION) {

            return;
        }

        boolean success =
                questionManager
                        .resolveQuestion(
                                selectedQuestion,
                                selectedAnswer,
                                user.getUsername()
                        );

        if (success) {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Question marked as solved!"
                    );

        } else {

            JOptionPane
                    .showMessageDialog(
                            frame,
                            "Unable to resolve question."
                    );
        }
    }
}