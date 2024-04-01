import static org.junit.Assert.*;
import org.junit.Before;
import org.jUnit.Test;
import java.util.ArrayList;

public class BlockedListTestCase {

    private BlockedList blockedList;

    @Before
    public void setUp() {
        blockedList = new BlockedList("user1", "user2", "2024-04-01");
    }

    @Test
    public void testAddBlock() {
        String blockID = blockedList.AddBlock();
        assertNotNull(blockID);
        System.out.println("Block ID: " + blockID);
    }

    @Test
    public void testRemoveBlock() {
        String blockID = blockedList.RemoveBlock();
        assertNotNull(blockID);
        System.out.println("Removed Block ID: " + blockID);
    }

    @Test
    public void testBlockedCompList() {
        ArrayList<String> blockedUsers = blockedList.BlockedCompList("user1");
        assertNotNull(blockedUsers);
        System.out.println("Blocked Users for user1: " + blockedUsers);
    }
}
