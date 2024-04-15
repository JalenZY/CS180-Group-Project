import static org.junit.Assert.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectMessagingDatabaseTest {

    @Test
    public void sendMessage_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv1";
        String senderId = "user1";
        String recipientId = "user2";
        String messageContent = "Hello, how are you?";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        // Assuming the date is generated internally in sendMessage method
        db.sendMessage(conversationId, senderId, recipientId, formattedDate, messageContent);
        Conversations conversation = db.getConversationObj(conversationId);
        assertNotNull("Conversation should not be null", conversation);
        assertEquals("Conversation ID should match", conversationId, conversation.getConversationID());
        assertEquals("Sender ID should match", senderId, conversation.getSenderID());
        assertEquals("Recipient ID should match", recipientId, conversation.getRecipientID());
        assertEquals("Message should be added to the conversation", 3, conversation.getMessages().size());
        assertEquals("Message content should match", messageContent, conversation.getMessages().get(0));
        assertTrue("User conversations should be updated for sender", db.getUserConversations(senderId).contains(conversationId));
        assertTrue("User conversations should be updated for recipient", db.getUserConversations(recipientId).contains(conversationId));
    }
//
    @Test
    public void getConversation_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv2";
        Conversations conversation = new Conversations(conversationId);
        db.getConversationMap().put(conversationId, conversation);

        assertEquals("Retrieved conversation should match", conversation, db.getConversation(conversationId));
    }

    @Test
    public void getUserConversations_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String userId = "user3";
        String conversationId1 = "conv3";
        String conversationId2 = "conv4";
        List<String> conversationIds = Arrays.asList(conversationId1, conversationId2);

        // Add conversation IDs to userConversations map
        db.getUserConversationsMap().put(userId, conversationIds);

        // Add Conversations to conversations map
        List<Conversations> expectedConversations = new ArrayList<>();
        for (String id : conversationIds) {
            Conversations conv = new Conversations(id);
            db.getConversationMap().put(id, conv);
            expectedConversations.add(conv);
        }

        // Retrieve user conversations and compare with expected conversations
        assertEquals("Retrieved conversations should match", expectedConversations, db.getUserConversations(userId));
    }

    @Test
    public void deleteConversation_Test() throws IOException {
        // Set up initial state
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv6";
        Conversations conversation = new Conversations(conversationId);
        db.getConversationMap().put(conversationId, conversation);
        db.getUserConversationsMap().put("user4", Collections.singletonList(conversationId));

        // Call the method to delete the conversation
        assertTrue("Conversation deletion should be successful", db.deleteConversation(conversationId));

        // Assert that the conversation is removed from conversations map
        assertNull("Conversation should be removed from conversations map", db.getConversationMap().get(conversationId));

        // Assert that the conversation ID is removed from user conversations
        assertFalse("Conversation should be removed from user conversations", db.getUserConversationsMap().containsValue(conversationId));

        // Assert that lines containing the conversation ID are removed from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"))) {
            String line;
            boolean conversationIdFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(conversationId)) {
                    conversationIdFound = true;
                    break;
                }
            }
            assertFalse("Lines containing conversation ID should be removed from the file", conversationIdFound);
        }
    }
}
