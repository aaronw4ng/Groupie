package csci310;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.*;
import java.lang.Thread;

public class Ticketmaster {
    private static ReentrantLock lock = new ReentrantLock();
    
    public String buildVarString(String var, String value) {
        return "";
    }

    public String buildHostString(String keyword, String postalCode, String city, String startDate, String endDate, String genre) {
        System.out.println("Genre: " + genre);
        System.out.println("city: " + city);
        String host = "https://app.ticketmaster.com/discovery/v2/events.json?";
        String api_key = "NpmZT6NVdqwadA0ZDTadaPApGwAknwH4";
        // refactor below to form the host as a separate function to make it easier to test
        // if keyword is not empty string, then add it to the query
        boolean first_flag = true;
        if (keyword != "") {
            first_flag = false;
            host = host + "keyword=" + keyword;
        }
        // if the zipCode is not empty string, then add it to the query
        if (postalCode != "") {
            // if first
            if (first_flag) {
                host = host + "postalCode=" + postalCode;
                first_flag = false;
            }
            else {
                host = host + "&postalCode=" + postalCode;
            }
        }
        // TODO: consider city with more than 1 word
        // if the city is not empty string, then add it to the query
        if (city != "") {
            // if first
            if (first_flag) {
                host = host + "city=" + city;
                first_flag = false;
            }
            else {
                host = host + "&city=" + city;
            }
        }
        // if the start date is not empty, then add it to the query
        if (startDate != "") {
            // if first
            if (first_flag) {
                host = host + "startDateTime=" + startDate;
                first_flag = false;
            }
            else {
                host = host + "&startDateTime=" + startDate;
            }
        }
        // if the end date is not empty, then add it to the query
        if (endDate != "") {
            // if first
            if (first_flag) {
                host = host + "endDateTime=" + endDate;
                first_flag = false;
            }
            else {
                host = host + "&endDateTime=" + endDate;
            }
        }
        // if genre is not empty, then use the appropriate genre id
        // "id":"KnvZfZ7vAev","name":"Pop"
        if (genre.equalsIgnoreCase("Pop")) {
            // if first
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAev";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAev";
            }
        }
        //"id":"KnvZfZ7vAvE","name":"Jazz"
        if (genre.equalsIgnoreCase("Jazz")) {
            // if first
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAvE";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAvE";
            }
        }
        //"id":"KnvZfZ7vAeJ","name":"Classical"
        if (genre.equalsIgnoreCase("Classical")) {
            // if first
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAeJ";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAeJ";
            }
        }
        //"id":"KnvZfZ7vAeA","name":"Rock"
        if (genre.equalsIgnoreCase("Rock")) {
            // if first
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAeA";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAeA";
            }
        }
        //"id":"KnvZfZ7vA7E","name":"Soccer"
        if (genre.equalsIgnoreCase("Soccer")) {
            // if first
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vA7E";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vA7E";
            }
        }
        //"id":"KnvZfZ7vAdE","name":"Football"
        if (genre.equalsIgnoreCase("Football")) {
            // if first
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAdE";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAdE";
            }
        }
        //"id":"KnvZfZ7vAde","name":"Basketball"
        if (genre.equalsIgnoreCase("Basketball")) {
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAde";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAde";
            }
        }
        //"id":"KnvZfZ7v7l1","name":"Theatre"
        if (genre.equalsIgnoreCase("Theatre")) {
            if (first_flag) {
                host = host + "genreId=KnvZfZ7v7l1";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7v7l1";
            }
        }
        //"id":"KnvZfZ7vAe1","name":"Comedy"
        if (genre.equalsIgnoreCase("Comedy")) {
            if (first_flag) {
                host = host + "genreId=KnvZfZ7vAe1";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7vAe1";
            }
        }
        //"id":"KnvZfZ7v7lv","name":"Magic & Illusion"
        if (genre.equalsIgnoreCase("Magic & Illusion")) {
            if (first_flag) {
                host = host + "genreId=KnvZfZ7v7lv";
                first_flag = false;
            }
            else {
                host = host + "&genreId=KnvZfZ7v7lv";
            }
        }
        // add api key to query at the very end after all parameters
        host = host + "&apikey=" + api_key;
        System.out.println("Host: " + host);
        return host;
    }


    // isolate the below function into own class like TicketmasterWrapper
    // and add TicketmasterWrapper class static member variable to Ticketmaster class
    // can then mock this class member
    public String getSearchResult(String hostString) throws Exception {
        // for local testing purpose
        System.out.println("Warning: Ticketmaster API Triggered");

        // refactor url and httpurlrequest and can do separate file for mock json
        // want to only test it once; ifile stream?
        String result = "";
        URL url = new URL(hostString);

        // to force 250ms delay even when multiple threads are accessing the same url
        lock.lock();
        // temporary hotfix for limiting api calls
        Thread.sleep(250);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner scanner = new Scanner(url.openStream());

        //Write all the JSON data into a string using a scanner
        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }
        //Close the scanner
        scanner.close();
        // release lock
        lock.unlock();
        return result;
    }

    public ArrayList<Event> parseEventsArray(String result) {
        // turn into json object in order to extract embedded items
        // System.out.println(result);
        JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
        JsonArray eventsArray = jobj.getAsJsonObject("_embedded").getAsJsonArray("events");
        ArrayList<Event> refinedListOfEvents = new ArrayList<>();

        // iterate through each event and get the name and url
        for (int i = 0; i < eventsArray.size(); i++) {
            // should check for null before parsing any data
            String event = eventsArray.get(i).toString();
            // turn each individual event into json object
            JsonObject eventDetails = new Gson().fromJson(event, JsonObject.class);
            try {
                // create a new event w/ name
                // first check if there's event name
                Event newEvent = new Event(eventDetails.get("name").getAsString());
                // get url of event
                newEvent.setURLEvent(eventDetails.get("url").getAsString());
                // get start date and time of event
                newEvent.setStartDateTime(eventDetails.getAsJsonObject("dates").getAsJsonObject("start").get("dateTime").getAsString());
                // get venues of event
                JsonArray venuesArray = eventDetails.getAsJsonObject("_embedded").getAsJsonArray("venues");
                List<Venue> venues = new ArrayList<>();
                for (int j = 0; j < venuesArray.size(); j++) {
                    // try parsing the current event's venues; if it is missing any fields, then ignore event
                    try {
                        String venueName = venuesArray.get(j).getAsJsonObject().get("name").getAsString();
                        String address = venuesArray.get(j).getAsJsonObject().getAsJsonObject("address").get("line1").getAsString();
                        String venueCity = venuesArray.get(j).getAsJsonObject().getAsJsonObject("city").get("name").getAsString();
                        String state = venuesArray.get(j).getAsJsonObject().getAsJsonObject("state").get("stateCode").getAsString();
                        String country = venuesArray.get(j).getAsJsonObject().getAsJsonObject("country").get("countryCode").getAsString();
                        Venue venue = new Venue(venueName, address, venueCity, state, country);
                        venues.add(venue);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                // if event does have at least one venue, then add the event
                if (!venues.isEmpty()) {
                    newEvent.setVenues(venues);
                    refinedListOfEvents.add(newEvent);
                }
            }
            // current event is missing necessary details, so just skip it
            catch (Exception e) {
                System.out.println("Was unable to parse current event");
                System.out.println(e.getMessage());
            }
        }
        return refinedListOfEvents;
    }

    // search up event through keyword
    // if empty fields are passed, then assuming they are not using those things for search result
    // startDateTime & endDateTime should be formatted as  YYYY-MM-DDT00:00:00.000Z
    public String searchEvents(String keyword, String postalCode, String city, String startDate, String endDate, String genre) throws Exception {
        System.out.println("search Events called genre: " + genre);
        String host = this.buildHostString(keyword, postalCode, city, startDate, endDate, genre);
        try {
            String result = this.getSearchResult(host);
            ArrayList<Event> refinedListOfEvents = this.parseEventsArray(result);
            // convert the refined results of list into events back into json format
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(refinedListOfEvents);
        }
        // Results were empty aka no events found or something went wrong when trying to connect
        catch (Exception e) {
            // e.printStackTrace();
            throw new Exception("No results found!");
        }
    }
}
