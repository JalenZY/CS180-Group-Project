import java.util.ArrayList;
import java.util.Date;
/**
 * FriendsListInterface.java
 *
 * Lists all the methods and their paramaters used by FriendsList.java
 *
 * @author ThomasRalton, L105
 * @version April 1, 2024
 */
public interface FriendsListInterface {

    // Method to return a list of friend's usernames for a given user
    public ArrayList<String> friendsCompList(String username1);

    // Method to check if given Friendship "o" is equal to classes friendship
    public boolean equals(Object o);

    // Setters
    public void setUser1ID(String user1ID);
    public void setUser2ID(String user2ID);
    public void setFriendshipID(String friendshipID);
    public void setStatus(String status);
    public void setSince(String since);
    public void setUsername1(String username1);
    public void setUsername2(String username2);
    public void setUser1FirstName(String user1FirstName);
    public void setUser1LastName(String user1LastName);
    public void setUser2FirstName(String user2FirstName);
    public void setUser2LastName(String user2LastName);
    public void setUser1status(String user1status);
    public void setUser2status(String user2status);
    public void setDate(Date date);

    // Getters
    public String getUser1ID();
    public String getUser2ID();
    public String getFriendshipID();
    public String getStatus();
    public String getSince();
    public String getUserName1();
    public String getUsername2();
    public String getUser1FirstName();
    public String getUser1LastName();
    public String getUser2FirstName();
    public String getUser2LastName();
    public String getUser1Status();
    public String getUser2Status();
    public Date getDate();
}
