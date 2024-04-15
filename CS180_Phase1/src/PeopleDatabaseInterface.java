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

    // Helper method to read text files into an ArrayList
    ArrayList<String> readFileToArray(String filename);

    // Method to write changes to a text file
    void writeChangesToFile(String filename, ArrayList<String> updatedInfo);

    // Method to add a new user to the database
    boolean addUser(UserProfile user);

    // Method to remove a user from the database
    boolean removeUser(String userID);

    // Method to update a user's profile
    boolean updateUserProfile(String userID, String toBeUpdated, String updatedPart);

    // Method to block a user
    boolean blockUser(String blockerID, String blockedID, String date);

    // Method to unblock a user
    boolean unblockUser(String blockerID, String blockedID, String date);

    // Method to add a friend
    boolean addFriend(String user1ID, String user2ID, String date);

    // Method to accept a friend request
    boolean acceptFriend(String user1ID, String user2ID, String date);

    // Method to deny a friend request
    boolean deniedFriend(String user1ID, String user2ID, String date);

    // Method to remove a friend
    boolean removeFriend(String user1ID, String user2ID, String date);
}
