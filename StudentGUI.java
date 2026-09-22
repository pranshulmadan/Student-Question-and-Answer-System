
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI {

    private JFrame frame;
    private User user;
    private UserManager userManager;

    private DefaultListModel<Questions> questionModel;
    private JList<Questions> questionList;
    private JTextArea detailsArea;

    // Shared while the application is running
    private static ArrayList<Questions> questions =
            new ArrayList<Questions>();

    private static int nextQuestionId = 1;
    private static int nextAnswerId = 1;

    public StudentGUI(User user, UserManager userManager) {

        this.user = user;
        this.userManager = userManager;

        // Create main window
        frame = new JFrame("Student Home");
        frame.setSize(800, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        frame.add(panel);

        // Welcome message
        JLabel welcomeLabel = new JLabel(
                "Welcome, " + user.getUsername()
                + " | Role: STUDENT"
        );

        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Question list
        questionModel = new DefaultListModel<Questions>();
        questionList = new JList<Questions>(questionModel);

        JScrollPane questionScroll =
                new JScrollPane(questionList);

        // Question details and answers
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        JScrollPane detailsScroll =
                new JScrollPane(detailsArea);

        // Split question list and details
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                questionScroll,
                detailsScroll
        );

        splitPane.setDividerLocation(300);

        panel.add(splitPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));

        JButton askButton = new JButton("Ask Question");
        JButton allButton = new JButton("View All");
        JButton unresolvedButton =
                new JButton("Unresolved Questions");
        JButton searchButton = new JButton("Search Questions");

        JButton answerButton = new JButton("Add Answer");
        JButton resolvedButton = new JButton("Mark Resolved");
        JButton refreshButton = new JButton("Refresh");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(askButton);
        buttonPanel.add(allButton);
        buttonPanel.add(unresolvedButton);
        buttonPanel.add(searchButton);

        buttonPanel.add(answerButton);
        buttonPanel.add(resolvedButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Show details when a question is selected
        questionList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                showQuestionDetails();
            }
        });

        // Ask Question button
        askButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                askQuestion();
            }
        });

        // View All button
        allButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshQuestions(false);
            }
        });

        // Unresolved Questions button
        unresolvedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshQuestions(true);
            }
        });

        // Search button
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                searchQuestions();
            }
        });

        // Add Answer button
        answerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addAnswer();
            }
        });

        // Mark Resolved button
        resolvedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                markResolved();
            }
        });

        // Refresh button
        refreshButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshQuestions(false);
            }
        });

        // Logout button
        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();

                LoginGUI.showLogin(userManager);
            }
        });

        refreshQuestions(false);

        frame.setVisible(true);
    }

    // Display questions in the list
    private void refreshQuestions(boolean unresolvedOnly) {

        questionModel.clear();

        for (Questions question : questions) {

            if (!unresolvedOnly || !question.isResolved()) {
                questionModel.addElement(question);
            }
        }

        detailsArea.setText(
                "Select a question to view its details and answers."
        );
    }

    // Ask a new question
    private void askQuestion() {

        JTextField titleField = new JTextField(25);
        JTextArea descriptionArea = new JTextArea(5, 25);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));

        inputPanel.add(new JLabel("Question Title:"));
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(
                frame,
                inputPanel,
                "Ask a Question",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Please enter a title and description."
            );

            return;
        }

        // Check for related questions before creating a new one
        String related = "";

        for (Questions question : questions) {

            if (question.getTitle().toLowerCase()
                    .contains(title.toLowerCase())
                    || title.toLowerCase().contains(
                            question.getTitle().toLowerCase())) {

                related += question.toString() + "\n";
            }
        }

        if (!related.isEmpty()) {

            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Related questions already exist:\n\n"
                    + related
                    + "\nDo you still want to ask your question?",
                    "Related Questions",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Create the question
        Questions newQuestion = new Questions(
                nextQuestionId,
                title,
                description,
                user.getUsername()
        );

        questions.add(newQuestion);

        nextQuestionId++;

        refreshQuestions(false);

        questionList.setSelectedValue(newQuestion, true);

        JOptionPane.showMessageDialog(
                frame,
                "Question submitted successfully!"
        );
    }

    // Show selected question and its potential answers
    private void showQuestionDetails() {

        Questions selected = questionList.getSelectedValue();

        if (selected == null) {
            return;
        }

        String status = selected.isResolved()
                ? "Resolved" : "Unresolved";

        String details =
                "Question #" + selected.getQuestionId()
                + "\nTitle: " + selected.getTitle()
                + "\nAsked by: " + selected.getAuthor()
                + "\nStatus: " + status
                + "\n\nDescription:\n"
                + selected.getDescription()
                + "\n\nPotential Answers:\n";

        if (selected.getAnswers().isEmpty()) {

            details += "\nNo answers have been submitted yet.";

        } else {

            for (Answer answer : selected.getAnswers()) {
                details += "\n" + answer.toString() + "\n";
            }
        }

        detailsArea.setText(details);
        detailsArea.setCaretPosition(0);
    }

    // Search for related questions
    private void searchQuestions() {

        String search = JOptionPane.showInputDialog(
                frame,
                "Enter a keyword to search for questions:"
        );

        if (search == null) {
            return;
        }

        search = search.trim().toLowerCase();

        if (search.isEmpty()) {
            refreshQuestions(false);
            return;
        }

        questionModel.clear();

        for (Questions question : questions) {

            if (question.getTitle().toLowerCase().contains(search)
                    || question.getDescription()
                            .toLowerCase().contains(search)) {

                questionModel.addElement(question);
            }
        }

        detailsArea.setText(
                "Search results: " + questionModel.size()
                + " question(s) found."
        );
    }

    // Submit a potential answer
    private void addAnswer() {

        Questions selected = questionList.getSelectedValue();

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Please select a question first."
            );

            return;
        }

        if (selected.isResolved()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "This question has already been resolved."
            );

            return;
        }

        JTextArea answerArea = new JTextArea(5, 25);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);

        int result = JOptionPane.showConfirmDialog(
                frame,
                new JScrollPane(answerArea),
                "Submit a Potential Answer",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String description = answerArea.getText().trim();

        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Please enter an answer."
            );

            return;
        }

        Answer newAnswer = new Answer(
                nextAnswerId,
                description,
                user.getUsername()
        );

        selected.addAnswer(newAnswer);

        nextAnswerId++;

        showQuestionDetails();

        JOptionPane.showMessageDialog(
                frame,
                "Answer submitted successfully!"
        );
    }

    // Mark your own question as resolved
    private void markResolved() {

        Questions selected = questionList.getSelectedValue();

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Please select a question first."
            );

            return;
        }

        if (!selected.getAuthor().equals(user.getUsername())) {

            JOptionPane.showMessageDialog(
                    frame,
                    "You can only resolve questions you asked."
            );

            return;
        }

        if (selected.isResolved()) {

            JOptionPane.showMessageDialog(
                    frame,
                    "This question is already resolved."
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Mark this question as resolved?",
                "Resolve Question",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            selected.setResolved(true);

            refreshQuestions(false);
            questionList.setSelectedValue(selected, true);
        }
    }
}