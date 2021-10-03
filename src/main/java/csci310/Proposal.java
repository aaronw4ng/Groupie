package csci310;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proposal {
    private User user;
    private List<Event> events;
    private List<User> invitedUsers;
    private Map<User, Response> mapOfResponses;
    private Event bestEvent;
    private boolean finalized = false;
    private List<User> accepted;
    private List<User> declined;

    public Proposal(User user) {
        this.user = user;
        events = new ArrayList<>();
        invitedUsers = new ArrayList<>();
        mapOfResponses = new HashMap<>();
        accepted = new ArrayList<>();
        declined = new ArrayList<>();
    }

    public User getOwner() {
        return this.user;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public List<User> getAllInvitedUsers() {
        return this.invitedUsers;
    }

    public Map<User, Response> getResponses() {
        return this.mapOfResponses;
    }

    public List<User> getAcceptedList() {
        return this.accepted;
    }

    public List<User> getDeclinedList() {
        return this.declined;
    }

    public void addEvent(Event event) {
    }


}
