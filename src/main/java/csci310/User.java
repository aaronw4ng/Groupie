package csci310;

import java.util.Arrays;
import java.util.Iterator;

public class User {
    String password;
    String username;
    String[] blockedUsers;
    String[] receivedProposals;
    String[] proposedProposals;
    String[] sentProposals;

    public void addBlockedUser(String user) {
    }

    public void removeBlockedUser(String user) {
    }

    public void addReceivedProposal(String proposal){
    }

    public void removeReceivedProposal(String proposal){
    }

    public void addSentProposal(String proposal) {
    }

    public void removeSentProposal(String proposal) {
    }

    public void setUsername(String username) {

    }

    public void setPassword(String password) {
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String[] getBlockedUsers() {
        return blockedUsers;
    }

    public String[] getReceivedProposals() {
        return receivedProposals;
    }

    public String[] getSentProposals() {
        return proposedProposals;
    }
}
