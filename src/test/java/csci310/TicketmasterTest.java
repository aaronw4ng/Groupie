package csci310;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.File;

import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
public class TicketmasterTest {
    Ticketmaster ticketmaster;

    @Before
    public void setUp() throws Exception {
        ticketmaster = Mockito.spy(new Ticketmaster());

        // import mock data from directory "misc"
        File f = new File("./misc");
        String[] fileList = f.list();
        for (String pathname:fileList){
            if (pathname.contains(".json")){
                mockFileImport(pathname.substring(0, pathname.length()-5));
                // System.out.println(pathname.substring(0, pathname.length()-5));
            }
        }
    }

    // helper function for testing
    public String getFileString(String filename){
        String fileString = "";
        try {
            Path filePath = Path.of("./misc/" + filename);
            // System.out.println(filePath);
            fileString = Files.readString(filePath);
        } catch (Exception e) {
            System.out.println("File not found");
        }
        return fileString;
    }

    // helper function for testing
    public void writeFile(String filename, String content){
        try {
            Path filePath = Path.of("./misc/" + filename);
            // System.out.println(filePath);
            Files.writeString(filePath, content);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public void mockFileImport(String filename)throws Exception{
        Mockito.doReturn(getFileString(filename+".json")).when(ticketmaster).getSearchResult(getFileString(filename+".url"));
        // System.out.println("-----------" + filename + "-----------");
    }

    public void saveMockData(String filename, String content, String url)throws Exception{
        writeFile(filename+".json", content);
        writeFile(filename+".url", url);
    }

    @Test
    public void testBuildHostString() {
        String host = ticketmaster.buildHostString("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertTrue(host.contains("BTS"));
        assertTrue(host.contains("90301"));
        assertTrue(host.contains("Inglewood"));
        assertTrue(host.contains("2021-11-01T00:00:00Z"));
        assertTrue(host.contains("2021-11-30T00:00:00Z"));
        assertTrue(host.contains("apikey="));
    }

    @Test
    public void testBuildHostStringNoInput() {
        String host = ticketmaster.buildHostString("", "", "", "", "");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("apikey"));
    }

    @Test
    public void testGetSearchResult() throws Exception{
        String host = ticketmaster.buildHostString("", "", "", "", "");
        String result = ticketmaster.getSearchResult(host);
        assertFalse(result == "");
        assertTrue(result.contains("_embedded"));
        // saveMockData("testGetSearchResult", result, host);
    }

    @Test
    public void testParseEventsArray() throws Exception{
        try{
        String host = ticketmaster.buildHostString("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z");
        String result = ticketmaster.getSearchResult(host);
        ArrayList<Event> events = ticketmaster.parseEventsArray(result);
        assertNotEquals(events.size(), 0);

        // saveMockData("testParseEventsArray", result, host);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchEvents() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z");
        assertTrue(result.contains("BTS"));
    }

    @Test
    public void testSearchEventsOnlyKeyword() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "", "", "", "");
        assertTrue(result.contains("BTS"));

        // String host = ticketmaster.buildHostString("BTS", "", "", "", "");
        // saveMockData("testSearchEventsOnlyKeyword", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsMissingKeyword() throws Exception {
        String result = ticketmaster.searchEvents("", "90301", "Inglewood", "", "");
        assertFalse(result.isEmpty());

        // String host = ticketmaster.buildHostString("", "90301", "Inglewood", "", "");
        // saveMockData("testSearchEventsMissingKeyword", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsMissingZipCode() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "", "Inglewood", "", "");
        assertTrue(result.contains("BTS"));

        // String host = ticketmaster.buildHostString("BTS", "", "Inglewood", "", "");
        // saveMockData("testSearchEventsMissingZipCode", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsMissingCity() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "", "", "");
        assertTrue(result.contains("BTS"));

        // String host = ticketmaster.buildHostString("BTS", "90301", "", "", "");
        // saveMockData("testSearchEventsMissingCity", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsNoEventsFound() throws Exception {
        // exception should be thrown because no results corresponding to the fields providing
        try {
            // String host = ticketmaster.buildHostString("qwerty", "", "", "", "");
            // saveMockData("testSearchEventsNoEventsFound", ticketmaster.getSearchResult(host), host);

            String result = ticketmaster.searchEvents("qwerty", "", "", "", "");
            fail();
        }
        catch (Exception e) {
            assertEquals("No results found!", e.getMessage());
        }
    }

}