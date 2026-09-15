import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addRole(Role role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
}