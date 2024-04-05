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
    // Method to add message to conversation
    public void addMessage(Messaging message);

    // Method to get last message in conversation
    public String getLastMessage();

    // Method to break apart and assign variables from an imported conversation file String
    public Conversations ReadFormat(String conversationInfo);

    // Setters
    public void setconversationID(String conversationID);
    public void setparticipantIDs(List<String> participantIDs);
    public void setmessages(List<String> messages);

    // Getters
    public String getconversationID();
    public List<String> getparticipantIDs();
    public List<String> getmessages();

    // Method to write conversation to a file
    public void writeToFile(String filename);
}
