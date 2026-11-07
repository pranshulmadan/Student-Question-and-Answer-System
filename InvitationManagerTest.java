import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class InvitationManagerTest {

    /*
     * TEST 1
     *
     * Admin can create an invitation.
     */
    @Test
    public void testAdminCanCreateInvitation() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        long expirationTime =
                System.currentTimeMillis()
                + 60000;

        Invitation invitation =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNotNull(
                invitation
        );

        assertNotNull(
                invitation.getCode()
        );

        assertFalse(
                invitation.getCode()
                        .isEmpty()
        );
    }

    /*
     * TEST 2
     *
     * Invitation should correctly
     * store multiple assigned roles.
     */
    @Test
    public void testInvitationStoresMultipleRoles() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        roles.add(
                Role.REVIEWER
        );

        long expirationTime =
                System.currentTimeMillis()
                + 60000;

        Invitation invitation =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNotNull(
                invitation
        );

        assertTrue(
                invitation.getRoles()
                        .contains(
                                Role.STUDENT
                        )
        );

        assertTrue(
                invitation.getRoles()
                        .contains(
                                Role.REVIEWER
                        )
        );

        assertEquals(
                2,
                invitation.getRoles()
                        .size()
        );
    }

    /*
     * TEST 3
     *
     * A newly created invitation
     * should be valid before it is
     * used or expired.
     */
    @Test
    public void testNewInvitationIsValid() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        long expirationTime =
                System.currentTimeMillis()
                + 60000;

        Invitation invitation =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNotNull(
                invitation
        );

        assertTrue(
                invitation.isValid()
        );

        assertFalse(
                invitation.isUsed()
        );

        assertFalse(
                invitation.isExpired()
        );
    }

    /*
     * TEST 4
     *
     * InvitationManager should be able
     * to find a created invitation
     * using its invitation code.
     */
    @Test
    public void testInvitationCanBeFoundByCode() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        long expirationTime =
                System.currentTimeMillis()
                + 60000;

        Invitation created =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNotNull(
                created
        );

        Invitation found =
                manager.findInvitation(
                        created.getCode()
                );

        assertNotNull(
                found
        );

        assertEquals(
                created.getCode(),
                found.getCode()
        );
    }

    /*
     * TEST 5
     *
     * A valid invitation code
     * should pass validation.
     */
    @Test
    public void testValidInvitationCodeIsAccepted() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        long expirationTime =
                System.currentTimeMillis()
                + 60000;

        Invitation invitation =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNotNull(
                invitation
        );

        Invitation validatedInvitation =
                manager.validateInvitation(
                        invitation.getCode()
                );

        assertNotNull(
                validatedInvitation
        );

        assertEquals(
                invitation.getCode(),
                validatedInvitation.getCode()
        );
    }

    /*
     * TEST 6
     *
     * An invitation should become
     * invalid after it has been used.
     */
    @Test
    public void testInvitationCanOnlyBeUsedOnce() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        long expirationTime =
                System.currentTimeMillis()
                + 60000;

        Invitation invitation =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNotNull(
                invitation
        );

        String code =
                invitation.getCode();

        manager.useInvitation(
                code
        );

        Invitation usedInvitation =
                manager.findInvitation(
                        code
                );

        assertNotNull(
                usedInvitation
        );

        assertTrue(
                usedInvitation.isUsed()
        );

        assertFalse(
                usedInvitation.isValid()
        );

        assertNull(
                manager.validateInvitation(
                        code
                )
        );
    }

    /*
     * TEST 7
     *
     * The system should reject an
     * invitation whose expiration
     * deadline is already in the past.
     */
    @Test
    public void testExpiredInvitationIsRejected() {

        InvitationManager manager =
                new InvitationManager();

        ArrayList<Role> roles =
                new ArrayList<Role>();

        roles.add(
                Role.STUDENT
        );

        long expirationTime =
                System.currentTimeMillis()
                - 1000;

        Invitation invitation =
                manager.createInvitation(
                        roles,
                        expirationTime
                );

        assertNull(
                invitation
        );
    }

    /*
     * TEST 8
     *
     * A completely invalid invitation
     * code should be rejected.
     */
    @Test
    public void testInvalidInvitationCodeIsRejected() {

        InvitationManager manager =
                new InvitationManager();

        Invitation invitation =
                manager.validateInvitation(
                        "INVALIDCODE"
                );

        assertNull(
                invitation
        );
    }
}