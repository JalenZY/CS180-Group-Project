import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class FriendsListTestCase {

    private FriendsList friendsList;

    @Before
    public void setUp() {
        String username1 = "user1";
        String username2 = "user2";
        String user1status = "Active";
        String user2status = "Pending";
        String date = "2024-03-31";
        friendsList = new FriendsList(username1, username2, user1status, user2status, date);
    }

    @Test
    public void testAddFriend() {
        String result = friendsList.AddFriend();
        assertEquals("FID_user1_user2_Active_2024-03-31", result);
    }

    @Test
    public void testRemoveFriend() {
        String result = friendsList.RemoveFriend();
        assertEquals("Friendship Record Not Found", result);
    }

    @Test
    public void testFriendsCompList() {
        ArrayList<String> expectedList = new ArrayList<>(Arrays.asList("user2"));
        ArrayList<String> resultList = friendsList.FriendsCompList("user1");
        assertEquals(expectedList, resultList);
    }
}
