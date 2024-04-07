import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Conversations.java
 *
 * This class contains all the methods required to format and add messages into a conversation
 * chain that can allow for users to view past messages per conversation. These conversation chains
 * will be stored in the conversations.txt file.
 * Format: "conversationID///participantsID///messages
 * messages format: "messageID//senderID//recipientID//timestamp//isRead//content"
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * - Format Date like: "MM-dd-yyyy HH:mm:ss" -- Military Time
 * - Might need to "encrypt" commas in given messages as they could result in false splitting
 * - sanity Check ReadFormat method
 *
 * @author Thomas Ralston, L105
 *
 * @version March 31, 2024
 *
 */
public class Conversations {

    //Fields
    private String conversationID;
    private List<String> participantIDs; //Passed in by database - could be formatted in clas
    private List<String> messages;
    private boolean archived;
    private boolean isRead;

    //Constructor
    public Conversations(String conversationID, List<String> participantIDs) {
        this.conversationID = conversationID;
        this.participantIDs = participantIDs;
        this.messages = new ArrayList<>();
    }

    //Method to add message to conversation
    public void addMessage(Messaging message) {
        messages.add(message.toString()); //Adds the message plus extra info about it in proper format
    }

    //Method to get last message in conversation - not sure if will use
    public String getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    //Method to break apart and assign variables from an imported conversation file String
    //Format: "conversationID//participantsID//message1,message2,message3,....
    //message Format: "messageID//conversationID//senderID//recipientID//timestamp//isRead//content"
    public Conversations ReadFormat(String conversationInfo) {
        String[] parts = conversationInfo.split("///");
        String conversationID = parts[0];
        List<String> participantIDs = Arrays.asList(parts[1].split(","));

        Conversations conversation2 = new Conversations(conversationID, participantIDs);

        if (parts.length > 2) {
            String[] messages2 = parts[2].split(","); //This might interfere with commas in original messages
            for (String messageContent : messages2) { //messages2 in format of messages toString
                //Format: "messageID//conversationID//senderID//recipientID//timestamp//isRead//content"
                List<String> messageDetails = Arrays.asList(messageContent.split("//"));
                String messageID = messageDetails.get(0);
                String timestamp = messageDetails.get(4); // Timestamp is at index 4
                boolean isRead = Boolean.parseBoolean(messageDetails.get(5));
                String content = messageDetails.get(6);

                String newMessageContent = Messaging.getConvertedContent(messageContent);
                Messaging message = new Messaging(messageID, conversationID, participantIDs.get(0), participantIDs.get(1),
                        timestamp, content, isRead);
                conversation2.addMessage(message); //Creates new conversation set that will return message info
            }
        }
        return (conversation2); //Might need to clear conversation2, that way multiple messages
        // don't get printed when called???
    }

    //Setters
    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }
    public void setParticipantIDs(List<String> participantIDs) {
        this.participantIDs = participantIDs; //new ArrayList<>(participantIds);????
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

//    public void setMessages(List<Messaging> messages) {
//        this.messages = messages;
//    }

    public void archive() {
        this.archived = true;
    }

    //Getters
    public String getConversationID() {
        return conversationID;
    }
    public List<String> getParticipantIDs() {
        return participantIDs;
        //return new ArrayList<>(participantIds);
    }
    public List<String> getMessages() {
        return messages;
        //return new ArrayList<>(messages);
    }
    public boolean isArchived() {
        return archived;
    }

    public String toString() {
        //Format: "conversationID///participantsID///messages
        //messages format: "messageID//senderID//recipientID//timestamp//isRead//content"
        String totalMessages = messages.toString(); //Commas should be placed between messages content
        return (String.format("%s///%s///%s", conversationID, participantIDs, totalMessages));
    }

    public void writeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write the conversation ID and participant IDs
            writer.write(conversationID + "///" + String.join(",", participantIDs) + "///\n");

            // Write each message
            for (String message : messages) {
                writer.write(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

} //End Class
