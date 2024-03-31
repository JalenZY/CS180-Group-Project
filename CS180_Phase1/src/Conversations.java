import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Conversations.java
 *
 * This class contains all the methods required to format and add messages into a conversation
 * chain that can allow for users to view past messages per conversation. These conversation chains
 * will be stored in the conversations.txt file.
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
    private List<Messaging> messages;

    //Constructor
    public Conversations(String conversationID, List<String> participantIDs) {
        this.conversationID = conversationID;
        this.participantIDs = participantIDs;
        this.messages = new ArrayList<>();
    }

    //Method to add message to conversation
    public void addMessage(Messaging message) {
        messages.add(message);
    }

    //Method to get last message in conversation - not sure if will use
    public Messaging getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    //Method to break apart and assign variables from an imported conversation file String
    public Conversations ReadFormat(String conversationInfo) {
        String[] parts = conversationInfo.split("//");
        String conversationID = parts[0];
        List<String> participantIDs = Arrays.asList(parts[1].split(","));

        Conversations conversation2 = new Conversations(conversationID, participantIDs);

        if (parts.length > 2) {
            String[] messages2 = parts[2].split(","); //This might interfere with commas in original messages
            for (String messageContent : messages2) {
                String newMessageContent = Messaging.getConvertedContent(messageContent);
                Messaging message = new Messaging(GenerateMessageID(), participantIDs.get(0), participantIDs.get(1),
                        null, newMessageContent);
                conversation2.addMessage(message); //Creates new conversation set that will return message info
            }
        }
        return (conversation2); //Might need to clear conversation2, that way multiple messages
        // don't get printed when called???
    }

    public String GenerateMessageID() {
        return (String.format(("%s,%s,%s"), Messaging.getSenderID(), Messaging.getRecipientID(),
                Messaging.getTimestamp()));
    }

    //Setters
    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }
    public void setParticipantIDs(List<String> participantIDs) {
        this.participantIDs = participantIDs;
    }
    public void setMessages(List<Messaging> messages) {
        this.messages = messages;
    }

    //Getters
    public String getConversationID() {
        return conversationID;
    }
    public List<String> getParticipantIDs() {
        return participantIDs;
    }
    public List<Messaging> getMessages() {
        return messages;
    }

    public String toString() {
        //Format: "conversationID//participantsID//messages
        String totalmessages = messages.toString(); //Commas should be placed between messages content
        return (String.format("%s//%s//%s", conversationID, participantIDs, totalmessages));
    }
} //End Class