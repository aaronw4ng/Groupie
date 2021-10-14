package csci310;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProposalTest {

    Proposal proposal;
    User user;
    Event event1;

    @Before
    public void setUp() throws Exception {
        user = new User("user x");
        proposal = new Proposal(user);
    }

    @Test
    public void testAddEvent() {
        event1 = new Event();
        int oldNumberOfEvents = proposal.getEvents().size();
        proposal.addEvent(event1);
        int newNumberOfEvents = proposal.getEvents().size();
        assertTrue(oldNumberOfEvents < newNumberOfEvents);
    }

    @Test
    public void testRemoveEvent() {
        Proposal proposal = new Proposal(user);
        Event partyEvent = new Event("Party");
        proposal.addEvent(partyEvent);
        int oldNumberOfEvents = proposal.getEvents().size();
        proposal.removeEvent(partyEvent);
        int newNumberOfEvents = proposal.getEvents().size();
        assertTrue(oldNumberOfEvents > newNumberOfEvents);
    }

    @Test
    public void testInviteUser() throws Exception {
        User friend = new User("user y");
        int oldNumberOfInvitees = proposal.getAllInvitedUsers().size();
        proposal.inviteUser(friend);
        int newNumberOfInvitees = proposal.getAllInvitedUsers().size();
        assertTrue(oldNumberOfInvitees < newNumberOfInvitees);
    }

    @Test
    public void testRemoveUser() throws Exception {
        User friend = new User("User1");
        proposal.inviteUser(friend);
        int oldNumberOfInvitees = proposal.getAllInvitedUsers().size();
        proposal.removeUser(friend);
        int newNumberOfInvitees = proposal.getAllInvitedUsers().size();
        assertTrue(oldNumberOfInvitees > newNumberOfInvitees);
    }
}