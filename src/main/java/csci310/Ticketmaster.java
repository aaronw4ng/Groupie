package csci310;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.*;

public class Ticketmaster {
    // search up event through keyword
    // if empty fields are passed, then assuming they are not using those things for search result
    String searchEvents(String keyword, String postalCode, String city) throws IOException {
        String result = "";
        String host = "https://app.ticketmaster.com/discovery/v2/events.json?";
        String api_key = "NpmZT6NVdqwadA0ZDTadaPApGwAknwH4";
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
        // add api key to query at the very end after all parameters
        host = host + "&apikey=" + api_key;
        URL url = new URL(host);
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


        // turn into json object in order to extract embedded items
        JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
        String events = jobj.get("_embedded").toString();

        // turn into json object again in order to array of events
        JsonObject eventsObj = new Gson().fromJson(events, JsonObject.class);
        JsonArray eventsArray = eventsObj.getAsJsonArray("events");

        ArrayList<Event> refinedListOfEvents = new ArrayList<>();

        // iterate through each event and get the name and url
        for (int i = 0; i < eventsArray.size(); i++) {
            // should check for null before parsing any data
            String event = eventsArray.get(i).toString();
            // turn each individual event into json object
            JsonObject eventDetails = new Gson().fromJson(event, JsonObject.class);
            // create a new event w/ name
            Event newEvent = new Event(eventDetails.get("name").toString());
            // get url of event
            newEvent.setURLEvent(eventDetails.get("url").toString());
            refinedListOfEvents.add(newEvent);
        }
        // convert the refined results of list into events back into json format
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(refinedListOfEvents);
        return json;
    }
}
