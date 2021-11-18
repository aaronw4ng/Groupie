package csci310;

import java.util.*;

public class Proposal {
    public User user;
    public int proposalId;
    public String title;
    public String description;
    public Boolean isDraft;
    public List<Event> events;
    public List<User> invitedUsers;
   // private Map<User, Response> mapOfResponses;
    public Event bestEvent;
    public List<User> accepted;
    public List<User> declined;

    public Proposal(User user) {
        this.user = user;
        events = new ArrayList<>();
        invitedUsers = new ArrayList<>();
        //mapOfResponses = new TreeMap<>();
        accepted = new ArrayList<>();
        declined = new ArrayList<>();
    }

    public Proposal() {
        events = new ArrayList<>();
        invitedUsers = new ArrayList<>();
        //mapOfResponses = new TreeMap<>();
        accepted = new ArrayList<>();
        declined = new ArrayList<>();
    }
}
