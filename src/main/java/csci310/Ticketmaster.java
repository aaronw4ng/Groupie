package csci310;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.*;

public class Ticketmaster {
    // search up event through keyword
    String searchEvents(String keyword) throws IOException {
        String inline = "";
        String host = "https://app.ticketmaster.com/discovery/v2/events.json?keyword=" + keyword + "&apikey=NpmZT6NVdqwadA0ZDTadaPApGwAknwH4";
        URL url = new URL(host);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner scanner = new Scanner(url.openStream());

        //Write all the JSON data into a string using a scanner
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        //Close the scanner
        scanner.close();

        JsonObject jobj = new Gson().fromJson(inline, JsonObject.class);
        String events = jobj.get("_embedded").toString();
        JsonObject eventsObj = new Gson().fromJson(events, JsonObject.class);
        JsonArray eventsArray = eventsObj.getAsJsonArray("events");

        ArrayList<Event> refinedListOfEvents = new ArrayList<>();
        // turn into json array
        JsonArray jsonArray = new JsonArray();
        // iterate through each event and get the name
        for (int i = 0; i < eventsArray.size(); i++) {
            String event = eventsArray.get(i).toString();
            JsonObject eventDetails = new Gson().fromJson(event, JsonObject.class);
            // create a new event w/ name
            Event newEvent = new Event(eventDetails.get("name").toString());
            // get url of event
            newEvent.setURLEvent(eventDetails.get("url").toString());
            refinedListOfEvents.add(newEvent);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(refinedListOfEvents);

        return json;
    }
}
