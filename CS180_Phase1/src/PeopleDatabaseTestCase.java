import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.Date;

/**
 * PeopleDatabaseTestCase.java
 *
 * This class contains testcases for the PeopleDatabase.java class
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
        user1 = new UserProfile("1", "user1", "John", "Doe", "john_doe@example.com", "password1");
        user2 = new UserProfile("2", "user2", "Jane", "Doe", "jane_doe@example.com", "password2");
    }

    @Test
    public void testAddUser() {
        database.addUser(user1);
        assertTrue(database.getUsers().containsKey(user1.getUserID()));
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
        UserProfile updatedProfile = new UserProfile(user1.getUserID(), "new_user", "John", "Doe", "newjohn_doe@example.com", "new_password");
        database.updateUserProfile(user1.getUserID(), updatedProfile);
        assertEquals("new_user", database.getUsers().get(user1.getUserID()).getUsername());
        assertEquals("NewJohn", database.getUsers().get(user1.getUserID()).getUserFirstname());
        assertEquals("Doe", database.getUsers().get(user1.getUserID()).getUserLastname());
        assertEquals("newjohn_doe@example.com", database.getUsers().get(user1.getUserID()).getEmail());
        assertEquals("new_password", database.getUsers().get(user1.getUserID()).getPassword());
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
