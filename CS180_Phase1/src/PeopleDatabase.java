import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * PeopleDatabase.java
 *
 * Lists all the methods and their parameters used by PeopleDatabase.java
 *
 * @author Zhengyi Jiang, L105
 * @version April 1, 2024
 */

public class PeopleDatabase {
    private Map<String, UserProfile> users; // Key: userID, Value: UserProfile
    private Map<String, String> friendships; // Key: friendshipID, Value: status
    private Map<String, String> blockedUsers; // Key: blockedID, Value: status

    public PeopleDatabase() {
        users = new HashMap<>();
        friendships = new HashMap<>();
        blockedUsers = new HashMap<>();
    }

    // Add a new user to the database
    public boolean addUser(UserProfile user) {
        if (!users.containsKey(user.getUserID())) {
            users.put(user.getUserID(), user);
            return true; // Success
        } else {
            System.out.println("User already exists.");
            return false; // Failure
        }
    }

    // Remove a user from the database
    public boolean removeUser(String userID) {
        if (users.containsKey(userID)) {
            users.remove(userID);
            return true; // Success
        } else {
            System.out.println("User does not exist.");
            return false; // Failure
        }
    }

    // Update a user's profile
    public boolean updateUserProfile(String userID, UserProfile updatedProfile) {
        if (users.containsKey(userID)) {
            users.put(userID, updatedProfile);
            return true; // Success
        } else {
            System.out.println("User does not exist.");
            return false; // Failure
        }
    }

    // Block a user
    public boolean blockUser(String blockerID, String blockedID, Date date) {
        // Assuming BlockedList constructor and addBlock() are correctly defined to support this
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        String blockRecord = block.addBlock();
        if (blockRecord != null && !blockedUsers.containsKey(block.getblockedID())) {
            blockedUsers.put(block.getblockedID(), "Blocked");
            System.out.println("User blocked successfully.");
            return true; // Success
        } else {
            System.out.println("Block operation failed.");
            return false; // Failure
        }
    }

    // Unblock a user
    public boolean unblockUser(String blockerID, String blockedID, Date date) {
        // Assuming BlockedList.removeBlock() is defined to support this
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        String blockRecord = block.removeBlock();
        if (blockRecord != null && blockedUsers.containsKey(block.getblockedID())) {
            blockedUsers.remove(block.getblockedID());
            System.out.println("User unblocked successfully.");
            return true; // Success
        } else {
            System.out.println("Unblock operation failed.");
            return false; // Failure
        }
    }

    // Add a friend
    public boolean addFriend(String user1ID, String user2ID, Date date) {
        // Assuming FriendsList constructor and addFriend() support this
        FriendsList friendship = new FriendsList(user1ID, user2ID, "Active", "Active", date);
        String friendRecord = friendship.addFriend();
        if (friendRecord != null && !friendships.containsKey(friendship.getfriendshipID())) {
            friendships.put(friendship.getfriendshipID(), "Active");
            System.out.println("Friend added successfully.");
            return true; // Success
        } else {
            System.out.println("Add friend operation failed.");
            return false; // Failure
        }
    }

    // Remove a friend
    public boolean removeFriend(String user1ID, String user2ID, Date date) {
        // Assuming FriendsList.removeFriend() is defined to support this
        FriendsList friendship = new FriendsList(user1ID, user2ID, "Active", "Active", date);
        String friendRecord = friendship.removeFriend();
        if (friendRecord != null && friendships.containsKey(friendship.getfriendshipID())) {
            friendships.remove(friendship.getfriendshipID());
            System.out.println("Friend removed successfully.");
            return true; // Success
        } else {
            System.out.println("Remove friend operation failed.");
            return false; // Failure
        }
    }

    // You can add your main method here for testing
}
