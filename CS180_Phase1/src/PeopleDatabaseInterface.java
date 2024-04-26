import java.util.ArrayList;
import java.util.Date;
/**
 * PeopleDatabaseInterface.java
 *
 * Lists all the methods and their paramaters used by PeopleDatabase.java
 *
 * @author ThomasRalton - Borris, L105
 * @version April 15, 2024
 */
public interface PeopleDatabaseInterface {

    boolean addUser(UserProfile user);
    boolean removeUser(String userID);
    boolean updateUserProfile(String userID, String toBeUpdated, String updatedPart);
    boolean blockUser(String blockerID, String blockedID, String date);
    boolean unblockUser(String blockerID, String blockedID);
    boolean addFriend(String user1ID, String user2ID, String date);
    boolean acceptFriend(String user1ID, String user2ID, String date);
    boolean deniedFriend(String user1ID, String user2ID);
    boolean removeFriend(String user1ID, String user2ID);
    boolean checkSpecificBlock(String unBlockerID, String blockedID);
    boolean checkBlock(String user1ID, String user2ID);
    boolean checkFriend(String user1ID, String user2ID);
    boolean checkExistingFriend(String user1ID, String user2ID);
    boolean checkSpecificFriend(String user1Name, String user2Name);
    String userNameToUserID(String userName);
    String userIDToUserName(String userID);
    String searchForUser(String textComponent);
    boolean checkUser(String userName);
    boolean userLogin(String userName, String password);
    ArrayList<String> printFriends(String userID);
}
