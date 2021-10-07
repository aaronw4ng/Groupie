package csci310;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class UserTest {
    User user;
    User other_user;

    @Before
    public void setUp() throws Exception {
        user = new User("1");
        other_user = new User("2");
    }

    @Test
    public void testSetPassword() {
        user.setPassword("x");
        assertEquals("x", user.getPassword());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("y");
        assertEquals("y", user.getUsername());
    }

    @Test
    public void testAddBlockedUser() {
        User user1 = new User("User1");
        User blockedUser = new User("User2");
        user1.addBlockedUser(blockedUser);
        assertEquals(1, user1.getBlockedUsers().size());
    }

    @Test
    public void testAddBlockedUserWhoIsAlreadyBlocked() {
        User user1 = new User("User1");
        User blockedUser = new User("User2");
        user1.addBlockedUser(blockedUser);
        user1.addBlockedUser(blockedUser);
        assertEquals(1, user1.getBlockedUsers().size());
    }

    @Test
    public void testRemoveBlockedUser() {
        User user1 = new User("User1");
        User blockedUser = new User("User2");
        user1.addBlockedUser(blockedUser);
        user1.removeBlockedUser(blockedUser);
        assertEquals(0, user1.getBlockedUsers().size());
    }

    @Test
    public void testRemoveBlockedUserWhereUserIsNotBlocked() {
        User user1 = new User("User1");
        User blockedUser = new User("User2");
        User missingBlockedUser = new User("User Is Not Blocked");
        user1.addBlockedUser(blockedUser);
        user1.removeBlockedUser(missingBlockedUser);
        assertEquals(1, user1.getBlockedUsers().size());
    }

    @Test
    public void testAddReceivedProposal() {
        Proposal new_p = new Proposal(other_user);
        User testUser = new User("testUser");
        testUser.addReceivedProposal(new_p);
        assertEquals(1, testUser.getReceivedProposals().size());
    }

    @Test
    public void testRemoveReceivedProposal() {
        Proposal new_p = new Proposal(other_user);
        user.addReceivedProposal(new_p);
        ArrayList<Proposal> expected = new ArrayList<>(Arrays.asList(new_p));
        assertEquals(expected, user.getReceivedProposals());

        user.removeReceivedProposal(new_p);
        expected.remove(new_p);
        assertEquals(expected, user.getReceivedProposals());
    }

    @Test
    public void testRemoveReceivedProposalWhereProposalDoesNotExist() {
        Proposal new_p = new Proposal(other_user);
        user.removeReceivedProposal(new_p);
        assertEquals(0, user.getReceivedProposals().size());
    }

    @Test
    public void testAddSentProposal() {
        Proposal new_p = new Proposal(user);
        User testUser = new User("testUser");
        testUser.addSentProposal(new_p);
        assertEquals(1, testUser.getSentProposals().size());
    }

    @Test
    public void testRemoveSentProposal() {
        Proposal new_p = new Proposal(user);
        user.addSentProposal(new_p);
        ArrayList<Proposal> expected = new ArrayList<>(Arrays.asList(new_p));
        assertEquals(expected, user.getSentProposals());

        user.removeSentProposal(new_p);
        expected.remove(new_p);
        assertEquals(expected, user.getSentProposals());
    }

    @Test
    public void testRemoveSentProposalWhereProposalDoesNotExist() {
        Proposal new_p = new Proposal(other_user);
        user.removeSentProposal(new_p);
        assertEquals(0, user.getSentProposals().size());
    }

    @Test
    public void testLoginSkeleton() {
        boolean result = user.login();
        assertEquals(result, true);
    }

    @Test
    public void testRegisterSkeleton() {
        boolean result = user.register();
        assertEquals(result, true);
    }
}