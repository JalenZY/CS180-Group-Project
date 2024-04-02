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

    @Before
    public void setUp() {
        database = new PeopleDatabase();
    }

    @Test
    public void testAddUser() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        database.addUser(user1);
        assertTrue(database.getUsers().containsKey(user1.getUserID()));
        assertEquals(user1, database.getUsers().get(user1.getUserID()));
    }

    @Test
    public void testRemoveUser() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        database.addUser(user1);
        database.removeUser(user1.getUserID());
        assertFalse(database.getUsers().containsKey(user1.getUserID()));
    }

    @Test
    public void testUpdateUserProfile() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        database.addUser(user1);

        UserProfile updatedProfile = new UserProfile("1", "updatedUser1", "Updated", "User", "updatedPassword", "1990-01-01", "male", "UpdatedReading", "UpdatedSwimming", "UpdatedTraveling", "UpdatedCooking", "New York", "USA", "Updated University");
        database.updateUserProfile(user1.getUserID(), updatedProfile);

        assertEquals("updatedUser1", database.getUsers().get(user1.getUserID()).getUsername());
        assertEquals("Updated", database.getUsers().get(user1.getUserID()).getUserFirstname());
        assertEquals("User", database.getUsers().get(user1.getUserID()).getUserLastname());
        assertEquals("updatedPassword", database.getUsers().get(user1.getUserID()).getPassword());
    }

    @Test
    public void testBlockUser() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        UserProfile user2 = new UserProfile("2", "user2", "Jane", "Smith", "abc123", "1995-05-15", "female", "Painting", "Hiking", "Photography", "Dancing", "Los Angeles", "USA", "College of ABC");
        database.addUser(user1);
        database.addUser(user2);
        database.blockUser(user1.getUserID(), user2.getUserID(), new Date());
        assertTrue(database.getBlockedUsers().containsKey(user2.getUserID()));
    }

    @Test
    public void testUnblockUser() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        UserProfile user2 = new UserProfile("2", "user2", "Jane", "Smith", "abc123", "1995-05-15", "female", "Painting", "Hiking", "Photography", "Dancing", "Los Angeles", "USA", "College of ABC");
        database.addUser(user1);
        database.addUser(user2);
        database.blockUser(user1.getUserID(), user2.getUserID(), new Date());
        database.unblockUser(user1.getUserID(), user2.getUserID(), new Date());
        assertFalse(database.getBlockedUsers().containsKey(user2.getUserID()));
    }

    @Test
    public void testRemoveFriend() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        UserProfile user2 = new UserProfile("2", "user2", "Jane", "Smith", "abc123", "1995-05-15", "female", "Painting", "Hiking", "Photography", "Dancing", "Los Angeles", "USA", "College of ABC");
        database.addUser(user1);
        database.addUser(user2);
        database.addFriend(user1.getUserID(), user2.getUserID(), new Date());
        database.removeFriend(user1.getUserID(), user2.getUserID(), new Date());
        assertFalse(database.getFriendships().containsKey(user1.getUserID() + "-" + user2.getUserID()));
    }

    @Test
    public void testAddFriend() {
        UserProfile user1 = new UserProfile("1", "user1", "John", "Doe", "password123", "1990-01-01", "male", "Reading", "Swimming", "Traveling", "Cooking", "New York", "USA", "University of XYZ");
        UserProfile user2 = new UserProfile("2", "user2", "Jane", "Smith", "abc123", "1995-05-15", "female", "Painting", "Hiking", "Photography", "Dancing", "Los Angeles", "USA", "College of ABC");
        database.addUser(user1);
        database.addUser(user2);
        database.addFriend(user1.getUserID(), user2.getUserID(), new Date());
        assertTrue(database.getFriendships().containsKey(user1.getUserID() + "-" + user2.getUserID()));
    }
}

