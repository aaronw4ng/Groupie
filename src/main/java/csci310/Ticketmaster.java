package csci310;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;

import org.openqa.selenium.net.UrlChecker;

public class Ticketmaster {
    public String buildHostString(String keyword, String postalCode, String city, String startDate, String endDate) {
        String host = "https://app.ticketmaster.com/discovery/v2/events.json?";
        String api_key = "NpmZT6NVdqwadA0ZDTadaPApGwAknwH4";
        // refactor below to form the host as a separate function to make it easier to test
        // if keyword is not empty string, then add it to the query
        if (keyword != "") {
            host = host + "keyword=" + keyword;
        }
        // if the zipCode is not empty string, then add it to the query
        if (postalCode != "") {
            host = host + "&postalCode=" + postalCode;
        }
        // if the city is not empty string, then add it to the query
        if (city != "") {
            host = host + "&city=" + city;
        }
        // if the start date is not empty, then add it to the query
        if (startDate != "") {
            host = host + "&startDateTime=" + startDate;
        }
        // if the end date is not empty, then add it to the query
        if (endDate != "") {
            host = host + "&endDateTime=" + endDate;
        }
        // add api key to query at the very end after all parameters
        host = host + "&apikey=" + api_key;
        return host;
    }

    public String getSearchResult(String hostString) throws Exception {
        // refactor url and httpurlrequest and can do separate file for mock json
        // want to only test it once; ifile stream?
        String result = "";
        URL url = new URL(hostString);
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
        return result;
    }

    public Event parseEvent(String eventString) {
        return new Event();
    }

    // search up event through keyword
    // if empty fields are passed, then assuming they are not using those things for search result
    // startDateTime & endDateTime should be formatted as  YYYY-MM-DDT00:00:00.000Z
    public String searchEvents(String keyword, String postalCode, String city, String startDate, String endDate) throws Exception {
        String host = this.buildHostString(keyword, postalCode, city, startDate, endDate);
        try {
            String result = this.getSearchResult(host);

            // turn into json object in order to extract embedded items
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
                Event newEvent = new Event(eventDetails.get("name").getAsString());
                // get url of event
                newEvent.setURLEvent(eventDetails.get("url").getAsString());
                // get start date and time of event
                newEvent.setStartDateTime(eventDetails.getAsJsonObject("dates").getAsJsonObject("start").get("dateTime").getAsString());
                // get venues of event
                JsonArray venuesArray = eventDetails.getAsJsonObject("_embedded").getAsJsonArray("venues");
                List<Venue> venues  = new ArrayList<>();
                for (int j = 0; j < venuesArray.size(); j++) {
                   String venueName = venuesArray.get(j).getAsJsonObject().get("name").getAsString();
                   String address = venuesArray.get(j).getAsJsonObject().getAsJsonObject("address").get("line1").getAsString();
                   String venueCity = venuesArray.get(j).getAsJsonObject().getAsJsonObject("city").get("name").getAsString();
                   String state = venuesArray.get(j).getAsJsonObject().getAsJsonObject("state").get("stateCode").getAsString();
                   String country = venuesArray.get(j).getAsJsonObject().getAsJsonObject("country").get("countryCode").getAsString();
                   Venue venue = new Venue(venueName, address, venueCity, state, country);
                   venues.add(venue);
                }
                newEvent.setVenues(venues);
                refinedListOfEvents.add(newEvent);
            }
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
