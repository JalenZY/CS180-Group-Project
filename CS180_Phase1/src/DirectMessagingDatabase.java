import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * DirectMessagingDatabase.java
 *
 * The database for direct messagings
 *
 * @author Zhengyi Jiang, L105
 * @version April 1, 2024
 */


public class DirectMessagingDatabase {
    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Map<String, List<String>> userConversations = new HashMap<>();

    // Send a message to a conversation; create the conversation if it does not exist
    public void sendMessage(String conversationId, String senderId, String messageContent) {
        Conversation conversation = conversations.computeIfAbsent(conversationId, k -> new Conversation(conversationId, new ArrayList<>()));
        String messageId = UUID.randomUUID().toString();
        Messaging message = new Messaging(messageId, conversationId, senderId, messageContent, new Date(), false);
        conversation.addMessage(message);

        // Add conversation to sender's and recipients' conversation lists
        updateUserConversations(senderId, conversationId);
    }

    // Retrieve a conversation by ID
    public Conversation getConversation(String conversationId) {
        return conversations.get(conversationId);
    }

    // Retrieve all conversations for a user
    public List<Conversation> getUserConversations(String userId) {
        List<String> conversationIds = userConversations.getOrDefault(userId, new ArrayList<>());
        List<Conversation> userConvs = new ArrayList<>();
        for (String id : conversationIds) {
            Conversation conv = conversations.get(id);
            if (conv != null && !conv.isArchived()) {
                userConvs.add(conv);
            }
        }
        return userConvs;
    }

    // Archive a conversation
    public void archiveConversation(String conversationId) {
        Conversation conversation = conversations.get(conversationId);
        if (conversation != null) {
            conversation.archive();
        }
    }

    // Delete a conversation
    public void deleteConversation(String conversationId) {
        conversations.remove(conversationId);
        // Remove the conversation from all users' lists
        for (List<String> list : userConversations.values()) {
            list.remove(conversationId);
        }
    }

    // Helper method to update user conversations list
    private void updateUserConversations(String userId, String conversationId) {
        userConversations.computeIfAbsent(userId, k -> new ArrayList<>()).add(conversationId);
    }
}

class Conversation {
    private String conversationId;
    private List<String> participantIds;
    private List<Messaging> messages = new ArrayList<>();
    private boolean archived;

    public Conversation(String conversationId, List<String> participantIds) {
        this.conversationId = conversationId;
        this.participantIds = participantIds;
    }

    public void addMessage(Messaging message) {
        messages.add(message);
    }

    public void archive() {
        this.archived = true;
    }

    public boolean isArchived() {
        return archived;
    }

    // Additional methods for Conversation management
}

class Messaging {
    private String messageId;
    private String conversationId;
    private String senderId;
    private String content;
    private Date timestamp;
    private boolean isRead;

    public Messaging(String messageId, String conversationId, String senderId, String content, Date timestamp, boolean isRead) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    // Getters and setters
}

