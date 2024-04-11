import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class PeopleDatabaseTestCase {

    @Test
    public void addUser_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        UserProfile user = new UserProfile("testUser");
        
        db.addUser(user);
        ArrayList<String> users = db.readFileToArray("userProfileList.txt");
        assertTrue("User should be added", users.contains(user.getUserName()));
    }

    @Test
    public void removeUser_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String userID = "testUser";

        db.removeUser(userID);
        ArrayList<String> users = db.readFileToArray("userProfileList.txt");
        assertFalse("User should be removed", users.contains(userID));
    }

    @Test
    public void updateUserProfile_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String userID = "testUser";
        String toBeUpdated = "userName";
        String updatedPart = "newUserName";

        db.updateUserProfile(userID, toBeUpdated, updatedPart);
        ArrayList<String> users = db.readFileToArray("userProfileList.txt");
        assertTrue("User profile should be updated", users.contains(updatedPart));
    }

    @Test
    public void blockUser_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String blockerID = "user1";
        String blockedID = "user2";
        Date date = new Date();

        db.blockUser(blockerID, blockedID, date);
        ArrayList<String> blockedUsers = db.readFileToArray("blockedUserList.txt");
        assertTrue("User should be blocked", blockedUsers.contains("BID_user1_user2_" + date.toString()));
    }

    @Test
    public void unblockUser_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String blockerID = "user1";
        String blockedID = "user2";
        Date date = new Date();

        db.unblockUser(blockerID, blockedID, date);
        ArrayList<String> blockedUsers = db.readFileToArray("blockedUserList.txt");
        assertFalse("User should be unblocked", blockedUsers.contains("BID_user1_user2_" + date.toString()));
    }

    @Test
    public void addFriend_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String user1ID = "user1";
        String user2ID = "user2";
        Date date = new Date();

        db.addFriend(user1ID, user2ID, date);
        ArrayList<String> friendships = db.readFileToArray("friendshipList.txt");
        assertTrue("Friendship should be added", friendships.contains("FID_user1_user2_Active_Active_" + date.toString()));
    }

    @Test
    public void removeFriend_Test() {
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String user1ID = "user1";
        String user2ID = "user2";
        Date date = new Date();

        db.removeFriend(user1ID, user2ID, date);
        ArrayList<String> friendships = db.readFileToArray("friendshipList.txt");
        assertFalse("Friendship should be removed", friendships.contains("FID_user1_user2_Active_Active_" + date.toString()));
    }

}
