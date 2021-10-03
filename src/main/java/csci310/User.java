package csci310;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class User {
    String password;
    String username;
    ArrayList<String> blockedUsers;
    ArrayList<String> receivedProposals;
    ArrayList<String> sentProposals;

    public User() {
        blockedUsers = new ArrayList<String>();
        receivedProposals = new ArrayList<String>();
        sentProposals = new ArrayList<String>();
    }

    public void addBlockedUser(String user) {
        this.blockedUsers.add(user);
    }

    public void removeBlockedUser(String user) {
        this.blockedUsers.remove(user);
        if (!this.blockedUsers.contains(user)) {
            System.out.println("No such user found.");
        }
    }

    public void addReceivedProposal(String proposal){
        this.receivedProposals.add(proposal);
    }

    public void removeReceivedProposal(String proposal){
        this.receivedProposals.remove(proposal);
        if (!this.receivedProposals.contains(proposal)) {
            System.out.println("No such proposal found.");
        }

    }

    public void addSentProposal(String proposal) {
        this.sentProposals.add(proposal);

    }

    public void removeSentProposal(String proposal) {
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

    public ArrayList<String> getBlockedUsers() {
        if (this.blockedUsers.isEmpty()) {
            return new ArrayList<String>();
        }
        return this.blockedUsers;
    }

    public ArrayList<String> getReceivedProposals() {
        if (this.receivedProposals.isEmpty()) {
            return new ArrayList<String>();
        }
        return this.receivedProposals;
    }

    public ArrayList<String> getSentProposals() {
        if (this.sentProposals.isEmpty()) {
            return new ArrayList<String>();
        }
        return sentProposals;
    }
}
