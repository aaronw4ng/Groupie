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

    // look over tests for search events

    @Test
    public void testSearchEvents() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "Inglewood");
        assertTrue(result.contains("BTS"));
        System.out.println(result);
    }

    @Test
    public void testSearchEventsMissingKeyword() throws Exception {
        String result = ticketmaster.searchEvents("", "90301", "Inglewood");
        assertFalse(result.isEmpty());
        System.out.println(result);
    }

    @Test
    public void testSearchEventsMissingZipCode() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "", "Inglewood");
        assertTrue(result.contains("BTS"));
        System.out.println(result);
    }

    @Test
    public void testSearchEventsMissingCity() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "");
        assertTrue(result.contains("BTS"));
        System.out.println(result);
    }

    @Test
    public void testSearchEventsFailure() throws Exception {

    }

}