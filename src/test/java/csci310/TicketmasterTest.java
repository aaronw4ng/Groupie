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
    String hostBegin = "https://app.ticketmaster.com/discovery/v2/events.json?";
    String api_key = "NpmZT6NVdqwadA0ZDTadaPApGwAknwH4";

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
        String host = ticketmaster.buildHostString("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z", "");
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
        String host = ticketmaster.buildHostString("", "", "", "", "", "");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyCity() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "");
        assertEquals(hostBegin +"city=Irvine&apikey=" + api_key, host);
    }
    @Test
    public void testBuildHostStringOnlyStartDateTime() {
        String host = ticketmaster.buildHostString("", "", "", "2021-11-01T00:00:00Z", "", "");
        assertEquals(hostBegin +"startDateTime=2021-11-01T00:00:00Z&apikey=" + api_key, host);
    }
    @Test
    public void testBuildHostStringOnlyEndDateTime() {
        String host = ticketmaster.buildHostString("", "", "", "", "2021-11-01T00:00:00Z", "");
        assertEquals(hostBegin +"endDateTime=2021-11-01T00:00:00Z&apikey=" + api_key, host);
    }
    @Test
    public void testBuildHostStringOnlyPopGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Pop");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAev")); // Pop genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringPopGenre() {
        String host = ticketmaster.buildHostString("", "", "Sacramento", "", "", "Pop");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Sacramento"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAev")); // Pop genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyJazzGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Jazz");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAvE")); // Jazz genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringJazzGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Jazz");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAvE")); // Jazz genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyClassicalGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Classical");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAeJ")); // Classical genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringClassicalGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Classical");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAeJ")); // Classical genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyRockGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Rock");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAeA")); // Rock genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringRockGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Rock");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAeA")); // Rock genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlySoccerGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Soccer");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vA7E")); // Soccer genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringSoccerGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Soccer");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vA7E")); // Soccer genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyFootballGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Football");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAdE")); // Football genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringFootballGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Football");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAdE")); // Football genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyBasketballGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Basketball");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAde")); // Basketball genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringBasketballGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Basketball");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAde")); // Basketball genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyTheatreGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Theatre");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7v7l1")); // Theatre genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringTheatreGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Theatre");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7v7l1")); // Theatre genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyComedyGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Comedy");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAe1")); // Comedy genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringComedyGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Comedy");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7vAe1")); // Comedy genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringOnlyMagicAndIllusionGenre() {
        String host = ticketmaster.buildHostString("", "", "", "", "", "Magic & Illusion");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertFalse(host.contains("city"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7v7lv")); // Magic & Illusion genre id
        assertTrue(host.contains("apikey"));
    }
    @Test
    public void testBuildHostStringMagicAndIllusionGenre() {
        String host = ticketmaster.buildHostString("", "", "Irvine", "", "", "Magic & Illusion");
        assertTrue(host.contains("https://app.ticketmaster.com/discovery/v2/events.json?"));
        assertFalse(host.contains("keyword"));
        assertFalse(host.contains("postalCode"));
        assertTrue(host.contains("city"));
        assertTrue(host.contains("Irvine"));
        assertFalse(host.contains("startDateTime"));
        assertFalse(host.contains("endDateTime"));
        assertTrue(host.contains("KnvZfZ7v7lv")); // Magic & Illusion genre id
        assertTrue(host.contains("apikey"));
    }

    @Test
    public void testGetSearchResult() throws Exception{
        String host = ticketmaster.buildHostString("", "", "", "", "", "");
        String result = ticketmaster.getSearchResult(host);
        assertFalse(result == "");
        assertTrue(result.contains("_embedded"));
        saveMockData("testGetSearchResult", result, host);
    }

    @Test
    public void testParseEventsArray() throws Exception{
        try{
        String host = ticketmaster.buildHostString("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z", "");
        String result = ticketmaster.getSearchResult(host);
        ArrayList<Event> events = ticketmaster.parseEventsArray(result);
        assertNotEquals(events.size(), 0);

        saveMockData("testParseEventsArray", result, host);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchEvents() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "Inglewood", "2021-11-01T00:00:00Z", "2021-11-30T00:00:00Z", "");
        assertTrue(result.contains("BTS"));
    }

    @Test
    public void testSearchEventsOnlyKeyword() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "", "", "", "", "");
        assertTrue(result.contains("BTS"));

        String host = ticketmaster.buildHostString("BTS", "", "", "", "", "");
        saveMockData("testSearchEventsOnlyKeyword", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsMissingKeyword() throws Exception {
        String result = ticketmaster.searchEvents("", "90301", "Inglewood", "", "", "");
        assertFalse(result.isEmpty());

        String host = ticketmaster.buildHostString("", "90301", "Inglewood", "", "", "");
        saveMockData("testSearchEventsMissingKeyword", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsMissingZipCode() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "", "Inglewood", "", "", "");
        assertTrue(result.contains("BTS"));

        String host = ticketmaster.buildHostString("BTS", "", "Inglewood", "", "", "");
        saveMockData("testSearchEventsMissingZipCode", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsMissingCity() throws Exception {
        String result = ticketmaster.searchEvents("BTS", "90301", "", "", "", "");
        assertTrue(result.contains("BTS"));

        String host = ticketmaster.buildHostString("BTS", "90301", "", "", "", "");
        saveMockData("testSearchEventsMissingCity", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsNoEventsFound() throws Exception {
        // exception should be thrown because no results corresponding to the fields providing
        try {
            String host = ticketmaster.buildHostString("qwerty", "", "", "", "", "");
            saveMockData("testSearchEventsNoEventsFound", ticketmaster.getSearchResult(host), host);
            ticketmaster.searchEvents("qwerty", "", "", "", "", "");
            fail();
        }
        catch (Exception e) {
            assertEquals("No results found!", e.getMessage());
        }
    }

    @Test
    public void testSearchEventsPopGenre() throws Exception {
        String result = ticketmaster.searchEvents("", "", "", "", "", "Pop");
        assertTrue(result.contains("BTS"));

        String host = ticketmaster.buildHostString("", "", "", "", "", "Pop");
        saveMockData("testSearchEventsPopGenre", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsBasketballGenre() throws Exception {
        String result = ticketmaster.searchEvents("", "", "", "", "", "Basketball");
        assertTrue(result.contains("Phoenix Suns"));

        String host = ticketmaster.buildHostString("", "", "", "", "", "Basketball");
        saveMockData("testSearchEventsBasketballGenre", ticketmaster.getSearchResult(host), host);
    }

    @Test
    public void testSearchEventsNoSavingData() throws Exception {
        String result = ticketmaster.searchEvents("Eric", "", "", "", "", "");
        assertTrue(result.contains("Eric"));
    }
}