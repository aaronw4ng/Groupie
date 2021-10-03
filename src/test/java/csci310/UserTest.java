package csci310;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void getPassword() {
        user.setPassword("x");
        assertEquals("x", user.getPassword());
    }

    @Test
    public void getUsername() {
        user.setUsername("y");
        assertEquals("y", user.getUsername());
    }

    @Test
    public void getBlockedUsers() {
        user.addBlockedUser("a");
        assertEquals("a", user.getBlockedUsers());

        user.removeBlockedUser("a");
        assertEquals("", user.getBlockedUsers());
    }

    @Test
    public void getReceivedProposals() {
        user.addReceivedProposal("b");
        assertEquals("b", user.getReceivedProposals());

        user.removeReceivedProposal("b");
        assertEquals("", user.getReceivedProposals());
    }

    @Test
    public void getProposedProposals() {
        user.addSentProposal("c");
        assertEquals("c", user.getSentProposals());

        user.removeSentProposal("c");
        assertEquals("", user.getSentProposals());
    }
}