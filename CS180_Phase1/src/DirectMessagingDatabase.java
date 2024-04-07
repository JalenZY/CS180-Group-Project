import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
/**
 * DirectMessagingDatabase.java
 *
 * The database for direct messagings
 *
 * @author Zhengyi Jiang, L105
 * @version April 1, 2024
 */
public class DirectMessagingDatabase {
    //Map to store conversations with conversation ID as key and Conversations object as value
    private final Map<String, Conversations> conversations = new HashMap<>();
    //Map to store user conversations with user ID as key and list of conversation IDs as value
    private final Map<String, List<String>> userConversations = new HashMap<>();

    //Send a message to a conversation; create the conversation if it does not exist
    public void sendMessage(String conversationId, String senderId, String recipientId, String messageContent) {
        //Validate input parameters
        if (conversationId == null || conversationId.isEmpty() || senderId == null || senderId.isEmpty() ||
                recipientId == null || recipientId.isEmpty() || messageContent == null || messageContent.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        //Retrieve or create conversation based on conversation ID
        Conversations conversation = conversations.computeIfAbsent(conversationId, k ->
                //new Conversations(conversationId, new ArrayList<>()));
                new Conversations(generateUniqueConversationId(), new ArrayList<>()));

        //Generate unique message ID
        String messageId =  generateUniqueMessageId(); //Generate a unique message ID

        //Format current date
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        //Create new Messaging object with message details
        Messaging message = new Messaging(messageId, conversationId, senderId, recipientId, date, messageContent, false);

        //Add message to the conversation
        conversation.addMessage(message);

        //Update user conversations
        updateUserConversations(senderId, conversationId);
    }

    //Retrieve a conversation by ID
    public Conversations getConversation(String conversationId) {
        return conversations.get(conversationId);
    }

    //Retrieve all conversations for a user
    public List<Conversations> getUserConversations(String userId) {
        List<String> conversationIds = userConversations.getOrDefault(userId, new ArrayList<>());
        List<Conversations> userConvs = new ArrayList<>();
        //Iterate through user's conversation IDs and add corresponding conversations to the list
        for (String id : conversationIds) {
            Conversations conv = conversations.get(id);
            if (conv != null && !conv.isArchived()) {
                userConvs.add(conv);
            }
        }
        return userConvs;
    }

    //Archive a conversation
    public void archiveConversation(String conversationId) {
        Conversations conversation = conversations.get(conversationId);
        if (conversation != null) {
            conversation.archive();
        }
    }

    //Delete a conversation
    public void deleteConversation(String conversationId) {
        //Remove conversation from conversations map
        conversations.remove(conversationId);
        //Remove conversation ID from all user conversations lists
        //Create a copy to avoid ConcurrentModificationException
        List<String> conversationIdsCopy = new ArrayList<>(userConversations.keySet());
        for (List<String> list : userConversations.values()) {
            list.remove(conversationId);
        }
    }

    //Helper method to update user conversations list
    private void updateUserConversations(String userId, String conversationId) {
        //Add conversation ID to the list of user conversations
        userConversations.computeIfAbsent(userId, k -> new ArrayList<>()).add(conversationId);
    }

    //Additional getters for class properties
    public Map<String, Conversations> getConversations() {
        //Return a copy of the conversations map to prevent modification outside the class
        return new HashMap<>(conversations);
    }

    public Map<String, List<String>> getUserConversationsMap() {
        //Return a copy of the userConversations map to prevent modification outside the class
        return new HashMap<>(userConversations);
    }

    // Generate a unique conversation ID
    private String generateUniqueMessageId() {
        String messageID;
        // Generate until a unique ID is found
        do {
            messageID = UUID.randomUUID().toString();
        } while (conversations.containsKey(messageID));
        return messageID;
    }

    // Generate a unique conversation ID
    private String generateUniqueConversationId() {
        String conversationId;
        // Generate until a unique ID is found
        do {
            conversationId = UUID.randomUUID().toString();
        } while (conversations.containsKey(conversationId));
        return conversationId;
    }

    // Write conversations and userConversations to a text file
    public void writeToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("directmessagingdatabase.txt"))) {
            for (Map.Entry<String, Conversations> entry : conversations.entrySet()) {
                String conversationID = entry.getKey();
                Conversations conv = entry.getValue();
                List<String> participantIDs = new ArrayList<>(conv.getParticipantIDs());
                List<String> messaging = conv.getMessages();
                StringBuilder totalMessages = new StringBuilder();
                for (String message : messaging) {
                    totalMessages.append(",").append(message);
                }
                writer.println(String.format("%s///%s///%s", conversationID, participantIDs, totalMessages.substring(1)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read conversations and userConversations from a text file
    public void readFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("directmessagingdatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("///");
                if (parts.length == 3) {
                    String conversationID = parts[0];
                    List<String> participantIDs = Arrays.asList(parts[1].split(", "));
                    Conversations conv = new Conversations(conversationID, participantIDs);
                    String[] messagesData = parts[2].split(",");
                    for (String messageData : messagesData) {
                        String[] messageParts = messageData.split("//");
                        if (messageParts.length == 7) { // Check if the correct number of parts
                            String messageID = messageParts[0];
                            String senderID = messageParts[1];
                            String recipientID = messageParts[2];
                            String timestamp = messageParts[3];
                            boolean isRead = Boolean.parseBoolean(messageParts[4]);
                            String content = messageParts[5];
                            // Create Messaging object with parsed data
                            Messaging message = new Messaging(messageID, conversationID, senderID, recipientID,
                                    timestamp, content, isRead);
                            conv.addMessage(message);
                        }
                    }
                    //Add the conversation to the database
                    conversations.put(conversationID, conv);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

} //End Class
