import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<User>();
    }

    public User registerUser(String username, String password) {

        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return null;
            }
        }

        User newUser = new User(username, password);

        if (users.isEmpty()) {
            newUser.addRole(Role.ADMIN);
        }

        users.add(newUser);

        return newUser;
    }

    public User login(String username, String password) {

        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.checkPassword(password)) {

                return user;
            }
        }

        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}