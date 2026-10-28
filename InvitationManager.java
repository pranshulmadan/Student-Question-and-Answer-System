import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class InvitationManager {

    private ArrayList<Invitation> invitations;

    private static final String FILE_NAME =
            "invitations.txt";

    private static final Path FILE_PATH =
            findProjectFolder()
                    .resolve(FILE_NAME);

    public InvitationManager() {

        invitations =
                new ArrayList<Invitation>();

        System.out.println(
                "Invitation database: "
                + FILE_PATH
        );

        loadInvitations();
    }

    /*
     * CREATE INVITATION
     */

    public Invitation createInvitation(
            ArrayList<Role> roles,
            long expirationTime) {

        if (roles == null
                || roles.isEmpty()) {

            return null;
        }

        if (expirationTime
                <= System.currentTimeMillis()) {

            return null;
        }

        String code =
                generateUniqueCode();

        Invitation invitation =
                new Invitation(
                        code,
                        roles,
                        expirationTime
                );

        invitations.add(
                invitation
        );

        saveInvitations();

        return invitation;
    }

    /*
     * FIND INVITATION
     */

    public Invitation findInvitation(
            String code) {

        if (code == null) {
            return null;
        }

        for (Invitation invitation :
                invitations) {

            if (invitation
                    .getCode()
                    .equalsIgnoreCase(
                            code.trim()
                    )) {

                return invitation;
            }
        }

        return null;
    }

    /*
     * VALIDATE INVITATION
     */

    public Invitation validateInvitation(
            String code) {

        Invitation invitation =
                findInvitation(code);

        if (invitation == null) {
            return null;
        }

        if (!invitation.isValid()) {
            return null;
        }

        return invitation;
    }

    /*
     * MARK CODE USED
     */

    public boolean useInvitation(
            String code) {

        Invitation invitation =
                validateInvitation(code);

        if (invitation == null) {
            return false;
        }

        invitation.setUsed(
                true
        );

        saveInvitations();

        return true;
    }

    /*
     * GET ALL INVITATIONS
     */

    public ArrayList<Invitation>
    getInvitations() {

        return new ArrayList<Invitation>(
                invitations
        );
    }

    /*
     * GENERATE CODE
     */

    private String generateUniqueCode() {

        String characters =
                "ABCDEFGHJKLMNPQRSTUVWXYZ"
                + "23456789";

        Random random =
                new Random();

        String code;

        do {

            StringBuilder builder =
                    new StringBuilder();

            for (int i = 0;
                    i < 8;
                    i++) {

                int index =
                        random.nextInt(
                                characters.length()
                        );

                builder.append(
                        characters.charAt(
                                index
                        )
                );
            }

            code =
                    builder.toString();

        } while (
                findInvitation(code)
                        != null
        );

        return code;
    }

    /*
     * SAVE INVITATIONS
     */

    private void saveInvitations() {

        try (BufferedWriter writer =
                Files.newBufferedWriter(
                        FILE_PATH
                )) {

            for (Invitation invitation :
                    invitations) {

                StringBuilder roles =
                        new StringBuilder();

                ArrayList<Role> roleList =
                        invitation.getRoles();

                for (int i = 0;
                        i < roleList.size();
                        i++) {

                    if (i > 0) {
                        roles.append(",");
                    }

                    roles.append(
                            roleList.get(i)
                                    .name()
                    );
                }

                writer.write(
                        invitation.getCode()
                        + "|"
                        + roles
                        + "|"
                        + invitation
                                .getExpirationTime()
                        + "|"
                        + invitation.isUsed()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving invitations: "
                    + e.getMessage()
            );
        }
    }

    /*
     * LOAD INVITATIONS
     */

    private void loadInvitations() {

        if (!Files.exists(
                FILE_PATH)) {

            System.out.println(
                    "No saved invitations found."
            );

            return;
        }

        try (BufferedReader reader =
                Files.newBufferedReader(
                        FILE_PATH
                )) {

            String line;

            while ((line =
                    reader.readLine())
                    != null) {

                if (line.trim()
                        .isEmpty()) {

                    continue;
                }

                String[] parts =
                        line.split(
                                "\\|",
                                -1
                        );

                if (parts.length != 4) {

                    continue;
                }

                String code =
                        parts[0];

                String[] savedRoles =
                        parts[1]
                                .split(",");

                ArrayList<Role> roles =
                        new ArrayList<Role>();

                for (String roleName :
                        savedRoles) {

                    try {

                        roles.add(
                                Role.valueOf(
                                        roleName
                                )
                        );

                    } catch (
                            IllegalArgumentException e) {

                        // Ignore invalid role.
                    }
                }

                long expiration =
                        Long.parseLong(
                                parts[2]
                        );

                boolean used =
                        Boolean.parseBoolean(
                                parts[3]
                        );

                Invitation invitation =
                        new Invitation(
                                code,
                                roles,
                                expiration
                        );

                invitation.setUsed(
                        used
                );

                invitations.add(
                        invitation
                );
            }

        } catch (IOException
                | NumberFormatException e) {

            System.out.println(
                    "Error loading invitations: "
                    + e.getMessage()
            );
        }
    }

    /*
     * FIND PROJECT FOLDER
     */

    private static Path
    findProjectFolder() {

        Path current =
                Paths.get("")
                        .toAbsolutePath()
                        .normalize();

        Path found =
                searchForRepository(
                        current
                );

        if (found != null) {

            return found;
        }

        try {

            Path classLocation =
                    Paths.get(
                            InvitationManager.class
                                    .getProtectionDomain()
                                    .getCodeSource()
                                    .getLocation()
                                    .toURI()
                    );

            Path directory =
                    Files.isDirectory(
                            classLocation
                    )
                    ? classLocation
                    : classLocation
                            .getParent();

            found =
                    searchForRepository(
                            directory
                    );

            if (found != null) {

                return found;
            }

        } catch (Exception e) {

            System.out.println(
                    "Could not determine "
                    + "class location: "
                    + e.getMessage()
            );
        }

        throw new IllegalStateException(
                "Could not locate "
                + "the project folder."
        );
    }

    private static Path
    searchForRepository(
            Path directory) {

        while (directory != null) {

            boolean hasGit =
                    Files.exists(
                            directory.resolve(
                                    ".git"
                            )
                    );

            boolean hasMain =
                    Files.exists(
                            directory.resolve(
                                    "Main.java"
                            )
                    );

            if (hasGit && hasMain) {

                return directory;
            }

            directory =
                    directory.getParent();
        }

        return null;
    }
}