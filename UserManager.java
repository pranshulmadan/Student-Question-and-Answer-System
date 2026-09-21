import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class UserManager {

    private ArrayList<User> users;

    private static final String FILE_NAME =
        "C:\\Users\\pedro\\Documents\\GitHub\\Student-Question-and-Answer-System\\users.dat";

    // Constructor
    public UserManager() {

        users = new ArrayList<User>();

        loadUsers();
    }

    // Register a new user
    public User registerUser(String username, String password) {

        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {

            System.out.println("Username and password cannot be empty.");
            return null;
        }

        // Check if username already exists
        for (User user : users) {

            if (user.getUsername().equals(username)) {

                System.out.println("Username already exists.");
                return null;
            }
        }

        User newUser = new User(username, password);

        // First user automatically becomes admin
        if (users.isEmpty()) {

            newUser.addRole(Role.ADMIN);
        }

        users.add(newUser);

        // Save users whenever a new account is created
        saveUsers();

        return newUser;
    }

    // Login
    public User login(String username, String password) {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.checkPassword(password)) {

                return user;
            }
        }

        return null;
    }

    // Get all users
    public ArrayList<User> getUsers() {

        return new ArrayList<User>(users);
    }

    // Save users to file
    private void saveUsers() {

        try {

            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            output.writeObject(users);

            output.close();

            System.out.println("Users saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Load users from file
    private void loadUsers() {

        File file = new File(FILE_NAME);

        // No saved file exists yet
        if (!file.exists()) {

            System.out.println("No saved users found.");

            return;
        }

        try {

            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            Object savedData = input.readObject();

            input.close();

            users = (ArrayList<User>) savedData;

            System.out.println("Users loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}