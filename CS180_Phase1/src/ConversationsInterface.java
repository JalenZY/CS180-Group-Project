import java.util.ArrayList;
import java.util.List;
/**
 * ConversationsInterface.java
 *
 * Lists all the methods and their paramaters used by Conversations.java
 *
 * @author ThomasRalton, L105
 * @version April 15, 2024
 */
public interface ConversationsInterface {
    // Method to add a message to the conversation
    void addMessage(String message);

    // Method to get the last message in the conversation
    String getLastMessage();

    // Method to break apart and assign variables from an imported conversation file String
    void ReadFormat(String conversationInfo);

    // Method to write conversation data to a file
    void writeToFile();

    // Setter methods
    void setConversationID(String conversationID);
    void setMessages(ArrayList<String> messages);
    void archive();

    // Getter methods
    String getConversationID();
    ArrayList<String> getMessages();
    boolean isArchived();
    String getSenderID();
    String getRecipientID();
}
