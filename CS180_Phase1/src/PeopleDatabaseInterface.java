import java.util.Date;
/**
 * PeopleDatabaseInterface.java
 *
 * Lists all the methods and their paramaters used by PeopleDatabase.java
 *
 * @author ThomasRalton - Borris, L105
 * @version April 1, 2024
 */
public interface PeopleDatabaseInterface {

    // Add a new user to the database
    void addUser(UserProfile user);

    // Remove a user from the database
    void removeUser(String userID);

    // Update a user's profile
    void updateUserProfile(String userID, UserProfile updatedProfile);

    // Block a user
    void blockUser(String blockerID, String blockedID, Date date);

    // Unblock a user
    void unblockUser(String blockerID, String blockedID, Date date);

    // Add a friend
    void addFriend(String user1ID, String user2ID, Date date);

    // Remove a friend
    void removeFriend(String user1ID, String user2ID, Date date);
}
