package csci310;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private String url;
    private String startDateTime;
    private List<Venue> venues = new ArrayList<>();

    public Event() {
        this.eventName = "";
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setURLEvent(String url) { this.url = url; }

    public void setStartDateTime(String dateTime) { this.startDateTime = dateTime; }

    public void setVenues(List<Venue> places) { this.venues = places; }
}
