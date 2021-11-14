package csci310;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.*;
import java.lang.Thread;

public class Ticketmaster {
    private static ReentrantLock lock = new ReentrantLock();

    public String buildVarString(String var, String value) {
        String ans = "";
        if (value == null){
            // pass
        }
        else if (var.equals("genreId")) {
            Map<String, String> genreMap = new HashMap<String, String>();
            //"id":"KnvZfZ7vAev","name":"Pop"
            //"id":"KnvZfZ7vAvE","name":"Jazz"
            //"id":"KnvZfZ7vAeJ","name":"Classical"
            //"id":"KnvZfZ7vAeA","name":"Rock"
            //"id":"KnvZfZ7vA7E","name":"Soccer"
            //"id":"KnvZfZ7vAdE","name":"Football"
            //"id":"KnvZfZ7vAde","name":"Basketball"
            //"id":"KnvZfZ7v7l1","name":"Theatre"
            //"id":"KnvZfZ7vAe1","name":"Comedy"
            //"id":"KnvZfZ7v7lv","name":"Magic & Illusion"
            genreMap.put("Pop".toLowerCase(), "KnvZfZ7vAev");
            genreMap.put("Jazz".toLowerCase(), "KnvZfZ7vAvE");
            genreMap.put("Classical".toLowerCase(), "KnvZfZ7vAeJ");
            genreMap.put("Rock".toLowerCase(), "KnvZfZ7vAeA");
            genreMap.put("Soccer".toLowerCase(), "KnvZfZ7vA7E");
            genreMap.put("Football".toLowerCase(), "KnvZfZ7vAdE");
            genreMap.put("Basketball".toLowerCase(), "KnvZfZ7vAde");
            genreMap.put("Theatre".toLowerCase(), "KnvZfZ7v7l1");
            genreMap.put("Comedy".toLowerCase(), "KnvZfZ7vAe1");
            genreMap.put("Magic & Illusion".toLowerCase(), "KnvZfZ7v7lv");
            if (genreMap.containsKey(value.toLowerCase())) {
                ans = "&" + var + "=" + genreMap.get(value.toLowerCase());
            }
        } else {
            // all other var
            if (value.equals("")) {
                ans = "";
            } else {
                ans = "&" + var + "=";
                for (int i = 0; i < value.length(); i++) {
                    if (value.charAt(i) == ' ') {
                        ans += "+";
                    } else {
                        ans += value.charAt(i);
                    }
                }
            }
        }
        return ans;
    }

    public String buildHostString(String keyword, String postalCode, String city, String startDate, String endDate, String genre) {
        System.out.println("Genre: " + genre);
        System.out.println("city: " + city);
        String host = "https://app.ticketmaster.com/discovery/v2/events.json?";
        String api_key = "NpmZT6NVdqwadA0ZDTadaPApGwAknwH4";
        // refactor below to form the host as a separate function to make it easier to test
        // if keyword is not empty string, then add it to the query
        String query_modifier = "";
        query_modifier += buildVarString("keyword", keyword);
        query_modifier += buildVarString("postalCode", postalCode);
        query_modifier += buildVarString("city", city);
        query_modifier += buildVarString("startDateTime", startDate);
        query_modifier += buildVarString("endDateTime", endDate);
        query_modifier += buildVarString("genreId", genre);
        if (!query_modifier.equals("")){
            query_modifier = query_modifier.substring(1);
        }
        // add api key to query at the very end after all parameters
        host = host + query_modifier + "&apikey=" + api_key;
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
    
    public String getAsStringDefaultNA(JsonObject jsonObject, String key) {
        if (jsonObject == null || !jsonObject.has(key)) {
            return "N/A";
        } else {
            return jsonObject.get(key).getAsString();
        }
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
            // create a new event w/ name
            // first check if there's event name
            Event newEvent = new Event(getAsStringDefaultNA(eventDetails, "name"));
            // get url of event
            newEvent.setURLEvent(getAsStringDefaultNA(eventDetails, "url"));
            // get start date and time of event
            newEvent.setStartDateTime(getAsStringDefaultNA(eventDetails.getAsJsonObject("dates").getAsJsonObject("start"), "dateTime"));
            // get venues of event
            JsonArray venuesArray = eventDetails.getAsJsonObject("_embedded").getAsJsonArray("venues");
            List<Venue> venues = new ArrayList<>();
            for (int j = 0; j < venuesArray.size(); j++) {
                // try parsing the current event's venues; if it is missing any fields, then ignore event
                JsonObject venueObject = venuesArray.get(j).getAsJsonObject();
                String venueName = getAsStringDefaultNA(venueObject, "name");
                String address = getAsStringDefaultNA(venueObject.getAsJsonObject("address"), "line1");
                String venueCity = getAsStringDefaultNA(venueObject.getAsJsonObject("city"), "name");
                String state = getAsStringDefaultNA(venueObject.getAsJsonObject("state"), "stateCode");
                String country = getAsStringDefaultNA(venueObject.getAsJsonObject("country"), "countryCode");
                Venue venue = new Venue(venueName, address, venueCity, state, country);
                venues.add(venue);
            }
            // add venues to event
            newEvent.setVenues(venues);
            refinedListOfEvents.add(newEvent);
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
            e.printStackTrace();
            throw new Exception("No results found!");
        }
    }
}
