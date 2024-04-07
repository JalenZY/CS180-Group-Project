import java.util.List;
/**
 * ConversationsInterface.java
 *
 * Lists all the methods and their paramaters used by Conversations.java
 *
 * @author ThomasRalton, L105
 * @version April 1, 2024
 */
public interface ConversationsInterface {
    // Method to add a message to the conversation
    void addMessage(Messaging message);

    // Method to get the last message in the conversation
    String getLastMessage();

    // Method to archive the conversation
    void archive();

    // Getter for the conversation ID
    String getConversationID();

    // Getter for the participant IDs
    List<String> getParticipantIDs();

    // Getter for the list of messages
    List<String> getMessages();

    // Method to write conversation details to a file
    void writeToFile(String filename);
}
