package csci310;

public class UserAvailability {
    public String userName;
    public int userId;
    public boolean isAvailable;
    public boolean didIBlock;
    public String until; // this is only for checking profile user status
    UserAvailability(String userName, int userId, boolean isAvailable, boolean didIBlock){
        this.userName = userName;
        this.userId = userId;
        this.isAvailable = isAvailable;
        this.didIBlock = didIBlock;
    }
}
