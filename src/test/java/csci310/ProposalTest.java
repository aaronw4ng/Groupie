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
        user = new User();
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
    public void testInviteUser() {
        User friend = new User();
        int oldNumberOfInvitees = proposal.getAllInvitedUsers().size();
        proposal.inviteUser(friend);
        int newNumberOfInvitees = proposal.getAllInvitedUsers().size();
        assertTrue(oldNumberOfInvitees < newNumberOfInvitees);
    }
}