package csci310;

import java.util.*;

public class Proposal {
    public User user;
    public Map<String, Event> events;
    public Map<String, User> invitedUsers;
   // private Map<User, Response> mapOfResponses;
    public Event bestEvent;
    public boolean finalized = false;
    public Map<String, User> accepted;
    public Map<String, User> declined;

    public Proposal(User user) {
        this.user = user;
        events = new TreeMap<>();
        invitedUsers = new TreeMap<>();
        //mapOfResponses = new TreeMap<>();
        accepted = new TreeMap<>();
        declined = new TreeMap<>();
    }

    public User getOwner() {
        return this.user;
    }

    public Map<String, Event> getEvents() {
        return this.events;
    }

    public Map<String, User> getAllInvitedUsers() {
        return this.invitedUsers;
    }

    public void addEvent(Event event) {
        events.put(event.getEventName(), event);
    }

    public void removeEvent(Event event) {
        // find event to be removed
        events.remove(event.getEventName());
    }

    public void inviteUser(User user) {
        invitedUsers.put(user.getUsername(), user);
    }

    public void removeUser(User user) {
        // find user to be removed
        invitedUsers.remove(user.getUsername());
    }
}
