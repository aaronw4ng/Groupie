package csci310;

public class Event {
    private String eventName;
    private String url;
    private String startDateTime;

    public Event() {
        this.eventName = "";
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setURLEvent(String url) { this.url = url; }

    public void setStartDateTime(String dateTime) { this.startDateTime = dateTime; }
}
