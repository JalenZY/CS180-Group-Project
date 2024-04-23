import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
/**
 * DirectMessagingDatabase.java
 *
 * The database for direct messaging
 *
 * @author Zhengyi Jiang/Thomas Ralston, L105
 * @version April 15, 2024
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
            return false;
        }

        //Retrieve or create conversation based on conversation ID
        //if there is no Conversation object associated with that conversation ID, then this method computes a new value
        Conversations conversation = conversations.computeIfAbsent(conversationID, k ->
               (new Conversations(conversationID, senderID, recipientID)));

        //Generate unique message ID
        String messageId = generateUniqueMessageId(); //Generate a unique message ID

        //Synchronize object for future-proof thread safety
        synchronized (conversation) {
            //Create and format new message object
            Messaging message = new Messaging(messageId, conversationID, senderID, recipientID, date, messageContent);
            //Add new message to database RAM text file - conversations message array list
            conversation.addMessage(message);
        }

        //Update user conversations - update both user1 and user2
        //updateUserConversations(senderID, conversationID);
        //updateUserConversations(recipientID,conversationID);

        //Write the conversation Object Key relation to a text file along with userConversation to another file
        return (writeToFile(conversationID, senderID, recipientID));
    }

    //Helper Method - Retrieve a conversation messages by ID
    public ArrayList<String> getConversation(String conversationID) {
        Conversations conv = conversations.get(conversationID);
        if (conv != null) {
            return conv.getMessages();
        } else {
            return new ArrayList<>(); // Return an empty list if conversationID is not found
        }
    }

    //Helper Method - Retrieve all conversations for a user - conversations as in object
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
        return tempFile.renameTo(file);
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
        Random random = new Random();
        // Generate until a unique ID is found
        do {
            Random rand = new Random();
            messageID = String.valueOf(rand.nextInt(100));
            //messageID = sb.toString();
            // Check if the generated ID already exists
            try {
                BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"));
                String line;
                boolean idExists = false;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("///");
                    if (parts.length > 1) {
                        String messageData = parts[3];
                        String[] messageParts = messageData.split("//");
                        if (messageParts.length > 1 && messageParts[1].equals(messageID)) {
                            // Found a matching message ID
                            idExists = true;
                            break;
                        }
                    }
                }
                reader.close();
                if (!idExists) {
                    unique = true; // Unique ID found, exit loop
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle or log the exception as needed
                unique = false; // Error occurred, retry generating the ID
            }
        } while (!unique);
        return messageID;
    }


    //Generate a unique conversation ID
    public String generateUniqueConversationID(String senderID, String recipientID) {
        //ConversationID Format: "senderIDRecipientID"
        String user2 = recipientID.substring(4);
        String conversationID = String.format("%s%s", senderID, user2);
        return conversationID;
    }

    //Method used to find the conversationID between two users given userID
    public String findConvID(String user1ID, String user2ID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"));
            String line;
            String user2 = user2ID.substring(4); //Get ID number
            String user1 = user1ID.substring(4); //Get ID number
            String ID1 = String.format("user%s%s", user1, user2);
            String ID2 = String.format("user%s%s", user2, user1);
            while ((line = reader.readLine()) != null) {
                if (line.contains(ID1)) {
                    String[] parts = line.split("///");
                    //Format: conversationID1///senderID1///recipientID1///message1,message2,message3
                    return parts[0];
                } else if (line.contains(ID2)) {
                    String[] parts = line.split("///");
                    //Format: conversationID1///senderID1///recipientID1///message1,message2,message3
                    return parts[0];
                }
            }
        } catch (FileNotFoundException e) {
            return "error";
        } catch (IOException e) {
            return "error";
        }
        return "No ID";
    }

    //Write to conversations text File -- Format: conversationID1///senderID1///recipientID1///message1,message2,message3
    //1.Get conversationID and Conversation text from Map
    //2.Take Text Value and turn into conversation object
    //3.Use conversation Object to get information about conversation
    //conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?
    //conv2///sender2///recipient2///conv2//msg3//sender2//recipient2//2024-04-13//Hello, how are you?,conv2//msg4//sender2//recipient2//2024-04-14//I'm fine
    //conv3///sender3///recipient3///conv3//msg6//sender3//recipient3//2024-04-15//What are you doing?conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?,conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?,conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?user7339///null///null///conv3///null///null///[[conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?, conv2///sender2///recipient2///conv2//msg3//sender2//recipient2//2024-04-13//Hello, how are you?,conv2//msg4//sender2//recipient2//2024-04-14//I'm fineconv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?[]conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?
    public boolean writeToFile(String conversationID, String senderID, String recipientID) {
        try {
            ArrayList<String> conv = readFileToArray("conversations.txt"); //Creates copy of text file

            //Checks if conversation exists in file, if it does, then removes existing conversation string from conv
            boolean contains = false;
            Iterator<String> iterator = conv.iterator();
            while (iterator.hasNext()) {
                String userString = iterator.next();
                if (userString.contains(conversationID)) {
                    contains = true;
                    iterator.remove(); //Remove existing conversation string
                    break;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("conversations.txt", false));
                //If conversation.txt does not contain conversation, then read from Map Array
                //Conversations conv3 = new Conversations(conversationID, senderID, recipientID); //get conversation ID from conversations Map List
                Conversations conv3 = conversations.get(conversationID);
                String start = String.format("%s///%s///%s///", conversationID, conv3.getSenderID(), conv3.getRecipientID());
                StringBuilder conversationBuilder = new StringBuilder();
                int passes = 0;

                for (String message : conv3.getMessages()) { //get messages from Map List
                    if (passes == 0) {
                        conversationBuilder.append(message);
                    } else {
                        conversationBuilder.append(",").append(message);
                    }
                    passes++;
                }
                ArrayList<String> specificConv = new ArrayList<>();
                specificConv.add(start + conversationBuilder);

                ArrayList<String> combinedList = new ArrayList<>();
                combinedList.addAll(conv);
                combinedList.addAll(specificConv);

                //Removes Blank Space from beginning of text
                for (int i = 0; i < combinedList.size(); i++) {
                    // Check if it's the first line
                    if (i != 0) {
                        writer.newLine(); //Add a newline character if it's not the first line
                    }
                    writer.write(combinedList.get(i)); //Write the line to the file
                }
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    //Helper Method that Writes Text Files as an ArrayList
    public ArrayList<String> readFileToArray(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //Skip empty lines or lines with only whitespace characters
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
            return lines;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
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

    public ArrayList<String> printMessages (String conversationID, String user1ID) {
        ArrayList<String> error = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"))) {
            String line;
            ArrayList<String> completeConversation = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.contains(conversationID)) {
                    String[] parts = line.split("///");
                    String recipientID = parts[2];
                    String user2UserName = new PeopleDatabase("UserProfileList.txt", "FriendshipList.txt", "blockedUserList.txt" ).userIDToUserName(recipientID);
                    String[] messagesParts = parts[3].split(",");
                    //Format: "conversationID//messageID//senderID//recipientID//date//content"
                    for (String messageData : messagesParts) {
                        String[] messageParts = messageData.split("//");
                        if (messageParts.length == 6) { //Check if the correct number of parts
                            String content = messageParts[5]; //Unencrypt the message
                            content = content.replace(".--.", ",");
                            String newInsert;
                            if (messageParts[3].equals(user1ID)) {
                                newInsert = String.format(("%s: " + content), user2UserName);
                            } else {
                                newInsert = String.format("You: " + content);
                            }
                            completeConversation.add(newInsert);
                        }
                    }
                }
            }
            return (completeConversation);
        } catch (FileNotFoundException e) {
            return (error);
        } catch (IOException e) {
            return (error);
        }
    }

} //End Class
