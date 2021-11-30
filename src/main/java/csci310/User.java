package csci310;

import java.util.ArrayList;

public class User {
    public int userId;
    public String username;
    public String password;
    public ArrayList<User> blockedUsers;
    public ArrayList<Proposal> receivedProposals;
    public ArrayList<Proposal> sentProposals;
    Boolean accepted;
    Boolean responded;

    public User(String username) throws Exception {
        this.username = username;
        this.blockedUsers = new ArrayList<>();
        this.receivedProposals = new ArrayList<>();
        this.sentProposals = new ArrayList<>();
    }

    public User() {
        this.blockedUsers = new ArrayList<>();
        this.receivedProposals = new ArrayList<>();
        this.sentProposals = new ArrayList<>();
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
            return new ArrayList<>();
        }
        return this.blockedUsers;
    }

    public ArrayList<Proposal> getReceivedProposals() {
        if (this.receivedProposals.isEmpty()) {
            return new ArrayList<>();
        }
        return this.receivedProposals;
    }

    public ArrayList<Proposal> getSentProposals() {
        if (this.sentProposals.isEmpty()) {
            return new ArrayList<>();
        }
        return sentProposals;
    }

    public void addBlockedUser(User user) throws Exception {
            // if this user is already blocked, then don't add them
            if (!blockedUsers.contains(user)) {
                // user is not yet blocked, then add them to blocked list
                blockedUsers.add(user);
            }
            else {System.out.print("User already blocked."); }
    }

    public void removeBlockedUser(User user) {
        // if user is on blocked list, then remove; otherwise, do nothing
        // note: remove function already checks if user in list before trying to remove
        blockedUsers.remove(user);
    }

    public void addReceivedProposal(Proposal proposal){
        this.receivedProposals.add(proposal);
    }

    public void removeReceivedProposal(Proposal proposal){
        // TODO: Need to implement contains for proposal class in order for this to work
        // if proposal is not in received list, then do nothing
        if (!this.receivedProposals.contains(proposal)) {
            System.out.print("No such proposal found.");
            return;
        }
        this.receivedProposals.remove(proposal);
    }

    public void addSentProposal(Proposal proposal) {
        // TODO: should prob check if proposal is already added?
        this.sentProposals.add(proposal);
    }

    public void removeSentProposal(Proposal proposal) {
        // TODO: Need to implement contains for proposal class in order for this to work
        // if proposal is not in sent list, then do nothing
        if (!this.sentProposals.contains(proposal)) {
            System.out.print("No such proposal found.");
            return;
        }
        this.sentProposals.remove(proposal);
    }

    @Override
    public boolean equals(Object otherUser) {
        // Check if otherUser is instance of User or null, then return false
        if (!(otherUser instanceof User)) {
            return false;
        }
        // If the object is compared with itself then return true
        if (otherUser == this) {
            return true;
        }
        // typecast to User so that we can compare data members
        User castUser = (User) otherUser;
        // Compare if they are same username since Users must have unique usernames and return accordingly
        return username.equalsIgnoreCase(castUser.getUsername());
    }

    public boolean login () { return true; }

    public boolean register() {
        return true;
    }
}
