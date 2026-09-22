
import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserManager {

    private ArrayList<User> users;

    private static final String FILE_NAME = "users.txt";

    private static final Path FILE_PATH = findProjectFolder()
            .resolve(FILE_NAME);

    public UserManager() {

        users = new ArrayList<User>();

        System.out.println("User database: " + FILE_PATH);

        loadUsers();
    }

    // Find the GitHub project folder
    private static Path findProjectFolder() {

        // Use an explicit project folder if provided
        String projectFolder =
                System.getProperty("studentqa.projectDir");

        if (projectFolder != null && !projectFolder.isEmpty()) {
            return Paths.get(projectFolder).toAbsolutePath();
        }

        // Search from the current working directory
        Path current = Paths.get("")
                .toAbsolutePath()
                .normalize();

        Path found = searchForRepository(current);

        if (found != null) {
            return found;
        }

        // Search from the compiled class location
        try {

            Path classLocation = Paths.get(
                    UserManager.class.getProtectionDomain()
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
                "Could not locate the Student Question "
                + "and Answer System repository. "
                + "Open the project folder in VS Code "
                + "and run the project from that folder."
        );
    }

    // Search parent directories for the project
    private static Path searchForRepository(Path directory) {

        while (directory != null) {

            boolean hasGit = Files.exists(
                    directory.resolve(".git")
            );

            boolean hasUserManager = Files.exists(
                    directory.resolve("UserManager.java")
            );

            boolean hasMain = Files.exists(
                    directory.resolve("Main.java")
            );

            if (hasGit && hasUserManager && hasMain) {
                return directory;
            }

            directory = directory.getParent();
        }

        return null;
    }

    // Register a new user
    public User registerUser(String username, String password) {

        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {

            return null;
        }

        username = username.trim();

        // Prevent delimiter characters in usernames
        if (username.contains("|")
                || username.contains("\n")
                || username.contains("\r")) {

            return null;
        }

        // Check for duplicate usernames
        for (User user : users) {

            if (user.getUsername().equals(username)) {
                return null;
            }
        }

        User newUser = new User(username, password);

        // First user becomes admin
        if (users.isEmpty()) {

            newUser.addRole(Role.ADMIN);

        } else {

            // Temporary student registration for testing
            newUser.addRole(Role.STUDENT);
        }

        users.add(newUser);

        saveUsers();

        return newUser;
    }

    // Login
    public User login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.checkPassword(password)) {

                return user;
            }
        }

        return null;
    }

    // Assign a role to an existing user
    public boolean addRoleToUser(String username, Role role) {

        for (User user : users) {

            if (user.getUsername().equals(username)) {

                user.addRole(role);

                saveUsers();

                return true;
            }
        }

        return false;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<User>(users);
    }

    // Save users as readable text
    private void saveUsers() {

        try (BufferedWriter writer =
                Files.newBufferedWriter(FILE_PATH)) {

            for (User user : users) {

                String roles = "";

                for (Role role : user.getRoles()) {

                    if (!roles.isEmpty()) {
                        roles += ",";
                    }

                    roles += role.name();
                }

                writer.write(
                        user.getUsername()
                        + "|"
                        + user.getPasswordHash()
                        + "|"
                        + roles
                );

                writer.newLine();
            }

            System.out.println("Users saved to: " + FILE_PATH);

        } catch (IOException e) {

            System.out.println(
                    "Error saving users: " + e.getMessage()
            );
        }
    }

    // Load users from the text file
    private void loadUsers() {

        if (!Files.exists(FILE_PATH)) {

            System.out.println(
                    "No saved users found. Starting fresh."
            );

            return;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(FILE_PATH)) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length != 3) {

                    System.out.println(
                            "Skipping invalid user record."
                    );

                    continue;
                }

                String username = parts[0];
                String passwordHash = parts[1];

                ArrayList<Role> roles =
                        new ArrayList<Role>();

                String[] roleNames = parts[2].split(",");

                for (String roleName : roleNames) {

                    try {

                        roles.add(
                                Role.valueOf(roleName.trim())
                        );

                    } catch (IllegalArgumentException e) {

                        System.out.println(
                                "Unknown role: " + roleName
                        );
                    }
                }

                User user = User.fromSavedData(
                        username,
                        passwordHash,
                        roles
                );

                users.add(user);
            }

            System.out.println(
                    "Loaded " + users.size() + " users."
            );

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Could not load user database.", e
            );
        }
    }
}