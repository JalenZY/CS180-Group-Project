import java.io.*;
import java.util.*;
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
    public boolean sendMessage(String conversationID, String senderID, String recipientID, String date, String messageContent) {
        //Validate input parameters
        //Conversation ID is created and passed in by server when a new conversation platform is opened for the 1st time
        if (conversationID == null || conversationID.isEmpty() || senderID == null || senderID.isEmpty() ||
                recipientID == null || recipientID.isEmpty() || messageContent == null || messageContent.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        //Retrieve or create conversation based on conversation ID
        Conversations conversation = conversations.computeIfAbsent(conversationID, k ->
               (new Conversations(conversationID, senderID, recipientID)));

        //Generate unique message ID
        String messageId = generateUniqueMessageId(); //Generate a unique message ID

        //Synchronize object for future-proof thread safety
        synchronized (conversation) {
            //Create and format new message object
            Messaging message = new Messaging(messageId, conversationID, senderID, recipientID, date, messageContent);
            conversation.addMessage(message); //Add new message to database text file
        }

        //Update user conversations - update both user1 and user2
        updateUserConversations(senderID, conversationID);
        updateUserConversations(recipientID,conversationID);

        //Write the conversation Object Key relation to a text file along with userConversation to another file
        return (writeToFile());
    }

    //Retrieve a conversation messages by ID
    public ArrayList<String> getConversation(String conversationID) {
        Conversations conv = conversations.get(conversationID);
        if (conv != null) {
            return conv.getMessages();
        } else {
            return new ArrayList<>(); // Return an empty list if conversationID is not found
        }
    }

    //Retrieve all conversations for a user - conversations as in object
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

    //Delete a conversation - conversation object and all respective messages in process
    public boolean deleteConversation(String conversationId) {
        //Check if the conversation ID exists
        if (!conversations.containsKey(conversationId)) {
            return false;
        }

        //Remove conversation from conversations map
        conversations.remove(conversationId);

        //Remove conversation ID from all user conversations lists using Iterator for safe removal
        for (List<String> userList : userConversations.values()) {
            Iterator<String> iterator = userList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(conversationId)) {
                    iterator.remove(); // Safe removal using Iterator
                }
            }
        }

        // Remove lines containing conversation ID from the file
        return (removeConversationIDMessages(conversationId));
    }

    //Helper method to remove conversation messages from arrays
    private boolean removeConversationIDMessages(String conversationId) {
        String filePath = "conversations.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(conversationId)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            //e.printStackTrace(); // Handle or log the exception as needed
            return false;
        }

        // Rename the temporary file to the original file
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        if (tempFile.renameTo(file)) {
            return true;
        } else {
            return false;
        }
    }


    //Helper method to update user conversations list
    private void updateUserConversations(String userId, String conversationId) {
        if (userConversations.containsKey(userId) && userConversations.get(userId).contains(conversationId)) {
            //Conversation ID already exists for the user, no need to write it again
            return;
        }
        //Add conversation ID to the list of user conversations
        userConversations.computeIfAbsent(userId, k -> new ArrayList<>()).add(conversationId);
    }

    //Additional getters for class properties
    public Map<String, Conversations> getConversationMap() {
        //Return a copy of the conversations map to prevent modification outside the class
        return new HashMap<>(conversations);
    }

    public Conversations getConversationObj(String conversationID) {
        //Return
        return (conversations.get(conversationID));
    }

    public Map<String, List<String>> getUserConversationsMap() {
        //Return a copy of the userConversations map to prevent modification outside the class
        return new HashMap<>(userConversations);
    }

    //Helper Method, Generate a unique Message ID
    private String generateUniqueMessageId() {
        String messageID;
        boolean unique = true;
        //Generate until a unique ID is found - checks against all other message ID
        do {
            messageID = UUID.randomUUID().toString();
            try  {
                BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("///");
                    String messageData = parts[3];
                    String[] messageParts = messageData.split("//");

                    if (parts.length > 1 && messageParts[1].equals(messageID)) { //Check against all other message IDs
                        //Found a matching message ID
                        unique = false; //Not unique
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); //Handle or log the exception as needed
            }
        } while (!unique);
        return messageID;
    }

    //Generate a unique conversation ID
    private String generateUniqueConversationID(String senderID, String recipientID) {
        //ConversationID Format: "senderIDRecipientID"
        String conversationID = String.format("%s%s", senderID, recipientID);
        return conversationID;
    }

    // Write conversations and userConversations to a text file
    public boolean writeToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("conversations.txt"))) {
            for (Map.Entry<String, Conversations> entry : conversations.entrySet()) {
                String conversationID = entry.getKey();
                Conversations conv = entry.getValue();
                String senderID = conv.getSenderID();
                String recipientID = conv.getRecipientID();
                List<String> messaging = conv.getMessages();
                StringBuilder totalMessages = new StringBuilder();
                for (String message : messaging) {
                    totalMessages.append(",").append(message);
                }
                //Format: conversationID1///senderID1///recipientID1///message1,message2,message3
                writer.println(String.format("%s///%s///%s///%s", conversationID, senderID, recipientID,
                        totalMessages.substring(1)));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // Read conversations and userConversations from a text file
    //Writes to a Map
    public boolean readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("///");
                if (parts.length == 4) {
                    String conversationID = parts[0];
                    String senderID = parts[1];
                    String recipientID = parts[2];
                    Conversations conv = new Conversations(conversationID, senderID, recipientID);
                    String[] messagesData = parts[3].split(",");
                    //Format: "conversationID//messageID//senderID//recipientID//date//content"
                    for (String messageData : messagesData) {
                        String[] messageParts = messageData.split("//");
                        if (messageParts.length == 6) { // Check if the correct number of parts
                            conversationID = messageParts[0];
                            String messageID = messageParts[1];
                            senderID = messageParts[2];
                            recipientID = messageParts[3];
                            String date = messageParts[4];
                            String content = messageParts[5];
                            // Create Messaging object with parsed data
                            Messaging message = new Messaging(messageID, conversationID, senderID, recipientID,
                                    date, content);
                            conv.addMessage(message);
                        }
                    }
                    //Add the conversation to the Map database
                    conversations.put(conversationID, conv);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

} //End Class
