package csci310;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProposalTest {

    Proposal proposal;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        proposal = new Proposal(user);
    }

    @Test
    public void testAddEvent() {
        Event event1 = new Event();
        int oldNumberOfEvents = proposal.getEvents().size();
        proposal.addEvent(event1);
        int newNumberOfEvents = proposal.getEvents().size();
        assertTrue(oldNumberOfEvents < newNumberOfEvents);
    }

}