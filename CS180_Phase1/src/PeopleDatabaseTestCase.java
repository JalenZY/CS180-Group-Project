import static org.junit.Assert.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
/**
 * PeopleDatabaseTestCase.java
 *
 * This class contains testcases for the PeopleDatabase.java class
 * It includes tests that check the methods of the classes to ensure they work
 *
 * @author Juan Rodriguez/Thomas Ralston, L105
 * @version April 15, 2024
 */
public class PeopleDatabaseTestCase {

    //@Test
//    public void addUser_Test() {  //UserID1,testUser,John,Doe,password123,1990-01-01,male,Reading,Swimming,Traveling,Cooking,New York,USA,University of XYZ
//        PeopleDatabase db = new PeopleDatabase("UserProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
//        UserProfile user = new UserProfile("UserID1");
//
//        boolean result = db.addUser(user);
//        assertTrue("User should be added", result);
//    }

    @Test
    public void removeUser_Test() { //UserID2,user4,Sarah,Smith,pass456,1995-05-15,female,Painting,Hiking,Photography,Yoga,Los Angeles,USA,Community College
        String userProfileList = "userProfileList.txt";
        String friendshipList = "friendshipList.txt";
        String blockedUserList = "blockedUserList.txt";

        PeopleDatabase peopleDB = new PeopleDatabase(userProfileList, friendshipList, blockedUserList);

        String userIDToRemove = "UserID2";

        boolean result = peopleDB.removeUser(userIDToRemove);
        assertTrue("User should be removed", result);
    }

    @Test
    public void updateUserProfile_Test() {  //UserID1,testUser,John,Doe,password123,1990-01-01,male,Reading,Swimming,Traveling,Cooking,New York,USA,University of XYZ
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String userID = "UserID1";
        String toBeUpdated = "userName";
        String updatedPart = "newUserName";

        boolean result = db.updateUserProfile(userID, toBeUpdated, updatedPart);
        assertTrue("User profile should be updated", result);
    }

    @Test
    public void blockUser_Test() { //BID_user9_user10_2024-04-11 15:40:10
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String blockerID = "user9";
        String blockedID = "user10";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        boolean result = db.blockUser(blockerID, blockedID, formattedDate);
        assertTrue("User should be blocked", result);

        // Check if the blocked user is added to the blockedUserList
        ArrayList<String> blockedUsers = db.readFileToArray("blockedUserList.txt");
        assertTrue("Blocked user should be in the list", blockedUsers.contains("BID_user9_user10_" + formattedDate));
    } //Works, Says it doesn't work

    @Test
    public void unblockUser_Test() { //BID_user1_user2_2024-04-11 09:45:20
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String blockerID = "user1";
        String blockedID = "user2";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        boolean result = db.unblockUser(blockerID, blockedID, formattedDate);
        assertTrue("User should be unblocked", result);

        // Check if the unblocked user is removed from the blockedUserList
        ArrayList<String> blockedUsers = db.readFileToArray("blockedUserList.txt");
        assertFalse("Unblocked user should not be in the list", blockedUsers.contains("BID_user1_user2_" + formattedDate));
    }

    @Test
    public void addFriend_Test() { //FID_user3_user4_Pending_2024-04-12 13:38:44
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String user1ID = "user3";
        String user2ID = "user4";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        boolean result = db.addFriend(user1ID, user2ID, formattedDate);
        assertTrue("Friendship should be added", result);
    }

    @Test
    public void removeFriend_Test() { //FID_user3_user4_Active_2024-04-12 13:38:44
        PeopleDatabase db = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
        String user1ID = "user5";
        String user2ID = "user6";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        boolean result = db.removeFriend(user1ID, user2ID, formattedDate);
        assertTrue("Friendship should be removed", result);
    }

}
