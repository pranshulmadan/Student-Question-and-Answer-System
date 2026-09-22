
import java.util.ArrayList;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {

    private String username;
    private String passwordHash;
    private ArrayList<Role> roles;

    private static final int ITERATIONS = 600000;
    private static final int KEY_LENGTH = 256;

    // Create a new user
    public User(String username, String password) {

        this.username = username;
        this.passwordHash = hashPassword(password);
        this.roles = new ArrayList<Role>();
    }

    // Load an existing user from a saved password hash
    public static User fromSavedData(
            String username,
            String savedHash,
            ArrayList<Role> savedRoles) {

        User user = new User(username);

        user.passwordHash = savedHash;
        user.roles = new ArrayList<Role>(savedRoles);

        return user;
    }

    // Private constructor for loading saved accounts
    private User(String username) {
        this.username = username;
        this.roles = new ArrayList<Role>();
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean checkPassword(String password) {

        try {

            String[] parts = passwordHash.split(":");

            if (parts.length != 3) {
                return false;
            }

            int iterations = Integer.parseInt(parts[0]);

            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expectedHash =
                    Base64.getDecoder().decode(parts[2]);

            byte[] actualHash = deriveKey(
                    password,
                    salt,
                    iterations
            );

            return MessageDigest.isEqual(
                    expectedHash,
                    actualHash
            );

        } catch (Exception e) {

            return false;
        }
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
        return new ArrayList<Role>(roles);
    }

    private static String hashPassword(String password) {

        try {

            byte[] salt = new byte[16];

            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            byte[] hash = deriveKey(
                    password,
                    salt,
                    ITERATIONS
            );

            return ITERATIONS + ":"
                    + Base64.getEncoder().encodeToString(salt)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Could not hash password", e
            );
        }
    }

    private static byte[] deriveKey(
            String password,
            byte[] salt,
            int iterations) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                iterations,
                KEY_LENGTH
        );

        try {

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            return factory.generateSecret(spec).getEncoded();

        } finally {

            spec.clearPassword();
        }
    }
}