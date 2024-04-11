import static org.junit.Assert.*;
import org.junit.Test;

public class DirectMessagingDatabaseTest {

    @Test
    public void sendMessage_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv1";
        String senderId = "user1";
        String recipientId = "user2";
        String messageContent = "Hello, how are you?";
        
        db.sendMessage(conversationId, senderId, recipientId, messageContent);
        Conversations conversation = db.getConversation(conversationId);
        assertNotNull("Conversation should not be null", conversation);
        assertEquals("Conversation ID should match", conversationId, conversation.getConversationId());
        assertEquals("Message should be added to the conversation", 1, conversation.getMessages().size());
        assertTrue("User conversations should be updated for sender", db.getUserConversations(senderId).contains(conversation));
        assertTrue("User conversations should be updated for recipient", db.getUserConversations(recipientId).contains(conversation));
    }

    @Test
    public void getConversation_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv2";
        Conversations conversation = new Conversations(conversationId, new ArrayList<>());
        db.getConversations().put(conversationId, conversation);

        assertEquals("Retrieved conversation should match", conversation, db.getConversation(conversationId));
    }

    @Test
    public void getUserConversations_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String userId = "user3";
        String conversationId1 = "conv3";
        String conversationId2 = "conv4";
        List<Conversations> expectedConversations = new ArrayList<>();
        Conversations conversation1 = new Conversations(conversationId1, new ArrayList<>());
        Conversations conversation2 = new Conversations(conversationId2, new ArrayList<>());
        expectedConversations.add(conversation1);
        expectedConversations.add(conversation2);
        
        db.getUserConversationsMap().put(userId, Arrays.asList(conversationId1, conversationId2));

        assertEquals("Retrieved conversations should match", expectedConversations, db.getUserConversations(userId));
    }

    @Test
    public void archiveConversation_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv5";
        Conversations conversation = new Conversations(conversationId, new ArrayList<>());
        db.getConversations().put(conversationId, conversation);

        db.archiveConversation(conversationId);
        assertTrue("Conversation should be archived", conversation.isArchived());
    }

    @Test
    public void deleteConversation_Test() {
        DirectMessagingDatabase db = new DirectMessagingDatabase();
        String conversationId = "conv6";
        Conversations conversation = new Conversations(conversationId, new ArrayList<>());
        db.getConversations().put(conversationId, conversation);
        db.getUserConversationsMap().put("user4", Collections.singletonList(conversationId));

        db.deleteConversation(conversationId);
        assertNull("Conversation should be removed from conversations map", db.getConversation(conversationId));
        assertFalse("Conversation should be removed from user conversations", db.getUserConversationsMap().containsValue(conversationId));
    }

}
