package csci310;

import java.util.ArrayList;
import java.util.List;

public class Event {
    public String eventName;
    public int eventId;
    public String url;
    public String startDateTime;
    public String segment;
    public String genre;
    public String subGenre;
    public List<Venue> venues = new ArrayList<>();
    public List<Response> responses = new ArrayList<>();

    public Event() {
        this.eventName = "";
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public Event(String eventName, String url, String startDateTime, List<Venue> venues) {
        this.eventName = eventName;
        this.url = url;
        this.startDateTime = startDateTime;
        this.venues = venues;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getUrl() {
        return this.url;
    }

    public String getStartDateTime() { return this.startDateTime; }

    public List<Venue> getVenues() { return this.venues; }

    public void setURLEvent(String url) { this.url = url; }

    public void setStartDateTime(String dateTime) { this.startDateTime = dateTime; }

    public void setVenues(List<Venue> places) { this.venues = places; }
}
