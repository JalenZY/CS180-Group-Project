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

    // Add a new user to the database
    boolean addUser(UserProfile user);

    // Remove a user from the database
    boolean removeUser(String userID);

    // Update a user's profile
    boolean updateUserProfile(String userID, String toBeUpdated, String updatedPart);

    // Block a user
    boolean blockUser(String blockerID, String blockedID, String date);

    // Unblock a user
    boolean unblockUser(String blockerID, String blockedID);

    // Add a friend
    boolean addFriend(String user1ID, String user2ID, String date);

    // Accept a friend request
    boolean acceptFriend(String user1ID, String user2ID, String date);

    // Deny a friend request
    boolean denyFriend(String user1ID, String user2ID);

    // Remove a friend
    boolean removeFriend(String user1ID, String user2ID);

    // Check if a specific block exists between two users
    boolean checkSpecificBlock(String unBlockerID, String blockedID);

    // Check if a block exists between two users
    boolean checkBlock(String user1ID, String user2ID);

    // Check if two users are friends
    boolean checkFriend(String user1ID, String user2ID);

    // Method to get userID given userName
    String userNameToUserID(String userName);

    // Helper Method that Reads Text Files as an ArrayList
    ArrayList<String> readFileToArray(String filename);

    // Method to rewrite text Files
    void writeChangesToFile(String filename, ArrayList<String> updatedInfo);
}
