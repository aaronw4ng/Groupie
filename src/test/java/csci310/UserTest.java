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
        user.addBlockedUser(other_user);

        ArrayList<User> expected = new ArrayList<>(Arrays.asList(other_user));
        assertTrue(expected.equals(user.getBlockedUsers()));

        user.removeBlockedUser(other_user);
        expected.remove(other_user);
        assertTrue(expected.equals(user.getBlockedUsers()));
    }

    @Test
    public void getReceivedProposals() {
        Proposal new_p = new Proposal(other_user);
        user.addReceivedProposal(new_p);
        ArrayList<Proposal> expected = new ArrayList<>(Arrays.asList(new_p));
        assertTrue(expected.equals(user.getReceivedProposals()));

        user.removeReceivedProposal(new_p);
        expected.remove(new_p);
        assertTrue(expected.equals(user.getReceivedProposals()));
    }

    @Test
    public void getSentProposals() {
        Proposal new_p = new Proposal(user);
        user.addSentProposal(new_p);
        ArrayList<Proposal> expected = new ArrayList<>(Arrays.asList(new_p));
        assertTrue(expected.equals(user.getSentProposals()));

        user.removeSentProposal(new_p);
        expected.remove(new_p);
        assertTrue(expected.equals(user.getSentProposals()));
    }
}