package csci310;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketmasterTest {
    Ticketmaster ticketmaster;

    @Before
    public void setUp() throws Exception {
        ticketmaster = new Ticketmaster();
    }

    @Test
    public void testSearchEvents() throws Exception {
        String result = ticketmaster.searchEvents("BTS");
        assertTrue(result.contains("BTS"));
        System.out.println(result);
    }
}