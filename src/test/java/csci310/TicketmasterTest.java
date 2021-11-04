package csci310;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class TicketmasterTest {
    Ticketmaster ticketmaster;

    @Before
    public void setUp() throws Exception {
        ticketmaster = new Ticketmaster();
    }

    // look over tests for search events

    @Test
    public void testBuildHostString() {
        String host = ticketmaster.buildHostString("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z");
        assertEquals("A, B, C, D, E", host);
    }

    @Test
    public void testGetSearchResult() throws Exception{
        String result = ticketmaster.getSearchResult("host");
        assertNotEquals(result, "");
    }

    @Test
    public void testParseEvent() {
        Event result = ticketmaster.parseEvent("host");
        assertNotEquals(result, "");
    }

    @Test
    public void testParseEventsArray() throws Exception{
        String host = ticketmaster.buildHostString("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z");
        String result = ticketmaster.getSearchResult(host);
        ArrayList<Event> events = ticketmaster.parseEventsArray(result);
        assertNotEquals(events.size(), 0);
    }

    @Test
    public void testSearchEvents() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z");
        assertTrue(result.contains("BTS"));
     //   System.out.println(result);
    }

    @Test
    public void testSearchEventsMissingKeyword() throws Exception {
        String result = ticketmaster.searchEvents("", "90301", "Inglewood", "", "");
        assertFalse(result.isEmpty());
     //   System.out.println(result);
    }

    @Test
    public void testSearchEventsMissingZipCode() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "", "Inglewood", "", "");
        assertTrue(result.contains("BTS"));
      //  System.out.println(result);
    }

    @Test
    public void testSearchEventsMissingCity() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "", "", "");
        assertTrue(result.contains("BTS"));
      //  System.out.println(result);
    }

    @Test
    public void testSearchEventsNoEventsFound() throws Exception {
        // exception should be thrown because no results corresponding to the fields providing
        try {
            String result = ticketmaster.searchEvents("qwerty", "", "", "", "");
            fail();
        }
        catch (Exception e) {
            assertEquals("No results found!", e.getMessage());
        }
    }

}