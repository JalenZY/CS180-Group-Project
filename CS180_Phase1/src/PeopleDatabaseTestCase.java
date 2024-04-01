import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.Date;

/**
 * PeopleDatabaseTestCase.java
 *
 * This class contains test cases for the PeopleDatabase.java class
 * It includes tests that check the methods of the classes to ensure they work
 *
 * @author Juan Rodriguez, L105
 * @version April 1, 2024
 */
public class PeopleDatabaseTestCase {

    private PeopleDatabase database;
    private UserProfile user1, user2;
    @Before
    public void setUp() {
        database = new PeopleDatabase();
        user1 = new UserProfile("1", "user1", "User", "One", "password", "2000-01-01", "Male", "Interest1", "Interest2", "Interest3", "City1", "Country1", "University1");
        user2 = new UserProfile("2", "user2", "User", "Two", "password", "2000-01-01", "Female", "Interest4", "Interest5", "Interest6", "City2", "Country2", "University2");
    }

    @Test
    public void testAddUser() {
        database.addUser(user1);
        assertTrue(database.getUsers().containsKey(user1.getUserID()));
        assertEquals(user1, database.getUsers().get(user1.getUserID()));
    }

    @Test
    public void testRemoveUser() {
        database.addUser(user1);
        database.removeUser(user1.getUserID());
        assertFalse(database.getUsers().containsKey(user1.getUserID()));
    }

    @Test
    public void testUpdateUserProfile() {
        database.addUser(user1);
        UserProfile updatedProfile = new UserProfile("1", "user1_updated", "Updated", "User", "updatedPassword", "2000-01-01", "Male", "UpdatedInterest1", "UpdatedInterest2", "UpdatedInterest3","UpdatedInterest4", "UpdatedCity1", "UpdatedCountry1", "UpdatedUniversity1");
        database.updateUserProfile(user1.getUserID(), updatedProfile);
        assertEquals("user1_updated", database.getUsers().get(user1.getUserID()).getUsername());
        assertEquals("Updated", database.getUsers().get(user1.getUserID()).getUserFirstname());
        assertEquals("User", database.getUsers().get(user1.getUserID()).getUserLastname());
        assertEquals("updatedPassword", database.getUsers().get(user1.getUserID()).getPassword());
    }

    @Test
    public void testBlockUser() {
        database.blockUser(user1.getUserID(), user2.getUserID(), new Date());
        assertTrue(database.getBlockedUsers().containsKey(user2.getUserID()));
    }

    @Test
    public void testUnblockUser() {
        database.blockUser(user1.getUserID(), user2.getUserID(), new Date());
        database.unblockUser(user1.getUserID(), user2.getUserID(), new Date());
        assertFalse(database.getBlockedUsers().containsKey(user2.getUserID()));
    }

    @Test
    public void testRemoveFriend() {
        database.addUser(user1);
        database.addUser(user2);
        database.addFriend(user1.getUserID(), user2.getUserID(), new Date());
        database.removeFriend(user1.getUserID(), user2.getUserID(), new Date());
        assertFalse(database.getFriendships().containsKey(user1.getUserID() + "-" + user2.getUserID()));
    }

    @Test
    public void testAddFriend() {
        database.addUser(user1);
        database.addUser(user2);
        database.addFriend(user1.getUserID(), user2.getUserID(), new Date());
        assertTrue(database.getFriendships().containsKey(user1.getUserID() + "-" + user2.getUserID()));
    }
}
