package csci310;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserTest {
    User user;
    User other_user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        other_user = new User();
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
        //user.addBlockedUser("a");
        user.addBlockedUser(other_user);

        ArrayList<User> expected = new ArrayList<>(Arrays.asList(other_user));
        assertTrue(expected.equals(user.getBlockedUsers()));

        user.removeBlockedUser(other_user);
        expected.remove(other_user);
        assertTrue(expected.equals(user.getBlockedUsers()));
    }

    @Test
    public void getReceivedProposals() {
        user.addReceivedProposal("b");
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("b"));
        assertTrue(expected.equals(user.getReceivedProposals()));

        user.removeReceivedProposal("b");
        expected.remove("b");
        assertTrue(expected.equals(user.getReceivedProposals()));
    }

    @Test
    public void getSentProposals() {
        user.addSentProposal("c");
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("c"));
        assertTrue(expected.equals(user.getSentProposals()));

        user.removeSentProposal("c");
        expected.remove("c");
        assertTrue(expected.equals(user.getSentProposals()));
    }
}