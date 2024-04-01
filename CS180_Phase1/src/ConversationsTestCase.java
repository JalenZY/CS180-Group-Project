import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ConversationsTestCase {
    private Conversations conversation;

    @Before
    public void setUp() {
        String conversationID = "C1";
        List<String> participantIDs = Arrays.asList("user1", "user2");
        conversation = new Conversations(conversationID, participantIDs);
    }
    @Test
    public void testAddMessage() {
        Messaging message = new Messaging("M1", "user1", "user2", "2024-03-31 12:00:00", "Hello");
        conversation.addMessage(message);
        List<Messaging> messages = conversation.getMessages();
        assertEquals(1, messages.size());
        assertEquals(message, messages.get(0));
    }
    @Test
    public void testReadFormat() {
        String conversationInfo = "C1//user1,user2//M1,user1,user2,2024-03-31 12:00:00,Hello";
        Conversations formattedConversation = conversation.ReadFormat(conversationInfo);
        assertNotNull(formattedConversation);
        assertEquals("C1", formattedConversation.getConversationID());
        List<String> participantIDs = formattedConversation.getParticipantIDs();
        assertEquals(2, participantIDs.size());
        assertTrue(participantIDs.contains("user1"));
        assertTrue(participantIDs.contains("user2"));
        List<Messaging> messages = formattedConversation.getMessages();
        assertEquals(1, messages.size());
        Messaging message = messages.get(0);
        assertEquals("M1", message.getMessageID());
        assertEquals("user1", message.getSenderID());
        assertEquals("user2", message.getRecipientID());
        assertEquals("2024-04-01 12:00:00", message.getTimestamp());
        assertEquals("Hello", message.getContent());
    }
}
