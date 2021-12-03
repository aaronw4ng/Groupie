package csci310;

import java.util.*;

public class Proposal {
    public User user;
    public int proposalId;
    public String title;
    public String description;
    public Boolean isDraft;
    public Boolean isFinalized;
    public Boolean needsOwnersSelection = false;
    public List<Event> events;
    public List<User> invitees;
    // private Map<User, Response> mapOfResponses;
    public int bestEventId;
    public Event bestEvent;
    public List<User> accepted;
    public List<User> declined;
    public List<User> notResponded;

    public Proposal(User user) {
        this.user = user;
        events = new ArrayList<>();
        invitees = new ArrayList<>();
        //mapOfResponses = new TreeMap<>();
        accepted = new ArrayList<>();
        declined = new ArrayList<>();
        notResponded = new ArrayList<>();
    }

    public Proposal() {
        events = new ArrayList<>();
        invitees = new ArrayList<>();
        //mapOfResponses = new TreeMap<>();
        accepted = new ArrayList<>();
        declined = new ArrayList<>();
        notResponded = new ArrayList<>();
    }
}
