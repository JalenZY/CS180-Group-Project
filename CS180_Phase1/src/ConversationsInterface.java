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
    // Method
    void addMessage(Messaging message);

    // Getters
    String getLastMessage();
    String getconversationID();
    List<String> getparticipantIDs();
    List<String> getmessages();

    // Setters
    void setconversationID(String conversationID);
    void setparticipantIDs(List<String> participantIDs);
    void setmessages(List<String> messages);
}
