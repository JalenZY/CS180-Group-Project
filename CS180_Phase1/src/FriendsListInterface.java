import java.util.ArrayList;
import java.util.Date;
/**
 * FriendsListInterface.java
 *
 * Lists all the methods and their paramaters used by FriendsList.java
 *
 * @author ThomasRalton, L105
 * @version April 15, 2024
 */
public interface FriendsListInterface {

    // Method to return a list of friend's usernames that a user has
    ArrayList<String> friendsCompList(String username1);

    // Method to check if a given Friendship object is equal to the class's friendship
    boolean equals(Object o);

    // Setters
    void setUser1ID(String user1ID);
    void setUser2ID(String user2ID);
    void setFriendshipID(String friendshipID);
    void setStatus(String status);
    void setSince(String since);
    void setUsername1(String username1);
    void setUsername2(String username2);
    void setUser1FirstName(String user1FirstName);
    void setUser1LastName(String user1LastName);
    void setUser2FirstName(String user2FirstName);
    void setUser2LastName(String user2LastName);
    void setDate(String date);

    // Getters
    String getUser1ID();
    String getUser2ID();
    String getFriendshipID();
    String getStatus();
    String getSince();
    String getUserName1();
    String getUsername2();
    String getUser1FirstName();
    String getUser1LastName();
    String getUser2FirstName();
    String getUser2LastName();
    String getDate();
}
