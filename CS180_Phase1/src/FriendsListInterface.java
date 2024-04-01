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

    // Methods
    String addFriend();
    String removeFriend();
    ArrayList<String> friendsCompList(String username1);

    // Getters
    String getuser1ID();
    String getuser2ID();
    String getfriendshipID();
    String getstatus();
    String getsince();
    String getusername1();
    String getusername2();
    String getuser1FirstName();
    String getuser1LastName();
    String getuser2FirstName();
    String getuser2LastName();
    String getuser1status();
    String getuser2status();
    Date getdate();

    // Setters
    void setuser1ID(String user1ID);
    void setuser2ID(String user2ID);
    void setfriendshipID(String friendshipID);
    void setstatus(String status);
    void setsince(String since);
    void setusername1(String username1);
    void setusername2(String username2);
    void setuser1FirstName(String user1FirstName);
    void setuser1LastName(String user1LastName);
    void setuser2FirstName(String user2FirstName);
    void setuser2LastName(String user2LastName);
    void setuser1status(String user1status);
    void setuser2status(String user2status);
    void setdate(Date date);
}
