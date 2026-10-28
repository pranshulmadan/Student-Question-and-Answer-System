import java.util.ArrayList;

public class Invitation {

    private String code;
    private ArrayList<Role> roles;
    private long expirationTime;
    private boolean used;

    public Invitation(
            String code,
            ArrayList<Role> roles,
            long expirationTime) {

        this.code = code;

        this.roles =
                new ArrayList<Role>(
                        roles
                );

        this.expirationTime =
                expirationTime;

        this.used = false;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<Role> getRoles() {

        return new ArrayList<Role>(
                roles
        );
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(
            boolean used) {

        this.used = used;
    }

    public boolean isExpired() {

        return System.currentTimeMillis()
                > expirationTime;
    }

    public boolean isValid() {

        return !used
                && !isExpired();
    }

    @Override
    public String toString() {

        return code
                + " - "
                + roles;
    }
}