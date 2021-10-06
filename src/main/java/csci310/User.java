package csci310;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class User {
    private String password;
    private String username;
    private ArrayList<User> blockedUsers;
    private ArrayList<Proposal> receivedProposals;
    private ArrayList<Proposal> sentProposals;

    public User(String username) {
        this.username = username;
        blockedUsers = new ArrayList<User>();
        receivedProposals = new ArrayList<Proposal>();
        sentProposals = new ArrayList<Proposal>();
    }

    public void addBlockedUser(User user) {
        // if this user is already blocked, then don't add them
        for (User u:blockedUsers) {
            // user is already blocked
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                return;
            }
        }
        // user is not yet blocked, then add them to blocked list
        this.blockedUsers.add(user);
    }

    public void removeBlockedUser(User user) {
        // first check if user is on blocked list, if so then remove
        // otherwise, do nothing
        if (!this.blockedUsers.contains(user)) {
            System.out.println("No such user found.");
            return;
        }
        this.blockedUsers.remove(user);
    }

    public void addReceivedProposal(Proposal proposal){
        this.receivedProposals.add(proposal);
    }

    public void removeReceivedProposal(Proposal proposal){
        this.receivedProposals.remove(proposal);
        if (!this.receivedProposals.contains(proposal)) {
            System.out.println("No such proposal found.");
        }

    }

    public void addSentProposal(Proposal proposal) {
        this.sentProposals.add(proposal);

    }

    public void removeSentProposal(Proposal proposal) {
        this.sentProposals.remove(proposal);
        if (!this.sentProposals.contains(proposal)) {
            System.out.println("No such proposal found.");
        }

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<User> getBlockedUsers() {
        if (this.blockedUsers.isEmpty()) {
            return new ArrayList<User>();
        }
        return this.blockedUsers;
    }

    public ArrayList<Proposal> getReceivedProposals() {
        if (this.receivedProposals.isEmpty()) {
            return new ArrayList<Proposal>();
        }
        return this.receivedProposals;
    }

    public ArrayList<Proposal> getSentProposals() {
        if (this.sentProposals.isEmpty()) {
            return new ArrayList<Proposal>();
        }
        return sentProposals;
    }
}
