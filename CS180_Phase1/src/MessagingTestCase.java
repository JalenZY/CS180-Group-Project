import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class MessagingTestCase {

    private Messaging message;

    @Before
    public void setUp() {
        message = new Messaging("MSG_123", "user1", "user2", new Date(), "Hey");
    }

    @Test
    public void testSetRead() {
        assertFalse(message.isRead());
        message.setRead(true);
        assertTrue(message.isRead());
    }

    @Test
    public void testGetTimestamp() {
        Date timestamp = new Date();

        message.setTimestamp(timestamp);

        assertEquals(timestamp, Messaging.getTimestamp());
    }

    @Test
    public void testGetConvertedContent() {
        String content = "Hey";
        String decryptedContent = Messaging.getConvertedContent(content);
        assertEquals(content, decryptedContent);
    }
}
