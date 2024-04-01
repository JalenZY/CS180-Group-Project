import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeopleDatabase {
    private Map<String, UseriProfile> users; // Key: userID, Value: UserProfile
    private Map<String, String> friendships; // Key: friendshipID, Value: status
    private Map<String, String> blockedUsers; // Key: blockedID, Value: status

    public PeopleDatabase() {
        users = new HashMap<>();
        friendships = new HashMap<>();
        blockedUsers = new HashMap<>();
    }

    // Add a new user to the database
    public void addUser(UserProfile user) {
        if (!users.containsKey(user.getUserID())) {
            users.put(user.getUserID(), user);
        } else {
            System.out.println("User already exists.");
        }
    }

    // Remove a user from the database
    public void removeUser(String userID) {
        if (users.containsKey(userID)) {
            users.remove(userID);
        } else {
            System.out.println("User does not exist.");
        }
    }

    // Update a user's profile
    public void updateUserProfile(String userID, UserProfile updatedProfile) {
        if (users.containsKey(userID)) {
            users.put(userID, updatedProfile);
        } else {
            System.out.println("User does not exist.");
        }
    }

    // Block a user
    public void blockUser(String blockerID, String blockedID, Date date) {
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        String blockRecord = block.addBlock();
        if (blockRecord != null && !blockedUsers.containsKey(block.getblockedID())) {
            blockedUsers.put(block.getblockedID(), "Blocked");
            System.out.println("User blocked successfully.");
        } else {
            System.out.println("Block operation failed.");
        }
    }

    // Unblock a user
    public void unblockUser(String blockerID, String blockedID, Date date) {
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        String blockRecord = block.removeBlock();
        if (blockRecord != null && blockedUsers.containsKey(block.getblockedID())) {
            blockedUsers.remove(block.getblockedID());
            System.out.println("User unblocked successfully.");
        } else {
            System.out.println("Unblock operation failed.");
        }
    }

    // Add a friend
    public void addFriend(String user1ID, String user2ID, Date date) {
        FriendsList friendship = new FriendsList(user1ID, user2ID, "Active", "Active", date);
        String friendRecord = friendship.addFriend();
        if (friendRecord != null && !friendships.containsKey(friendship.getfriendshipID())) {
            friendships.put(friendship.getfriendshipID(), "Active");
            System.out.println("Friend added successfully.");
        } else {
            System.out.println("Add friend operation failed.");
        }
    }

    // Remove a friend
    public void removeFriend(String user1ID, String user2ID, Date date) {
        FriendsList friendship = new FriendsList(user1ID, user2ID, "Active", "Active", date);
        String friendRecord = friendship.removeFriend();
        if (friendRecord != null && friendships.containsKey(friendship.getfriendshipID())) {
            friendships.remove(friendship.getfriendshipID());
            System.out.println("Friend removed successfully.");
        } else {
            System.out.println("Remove friend operation failed.");
        }
    }

    // Main method for demonstration purposes

}