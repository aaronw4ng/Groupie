package csci310;

public class Event {
    private String eventName;
    private String description;

    public Event() {
        this.eventName = "";
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }
}
