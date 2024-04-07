import java.io.IOException;
import java.util.Date;
/**
 * MessagingInterface.java
 *
 * Lists all the methods and their paramaters used by Messaging.java
 *
 * @author ThomasRalton, L105
 * @version April 1, 2024
 */
public interface MessagingInterface {
    // Method to write the message to the messages file
    void writeToMessagesFile(String filename) throws IOException;

    // Getter for the message ID
    String getMessageID();

    // Getter for the conversation ID
    String getConversationID();

    // Getter for the sender ID
    String getSenderID();

    // Getter for the recipient ID
    String getRecipientID();

    // Getter for the timestamp
    String getTimestamp();

    // Getter for the read status
    boolean isRead();

    // Getter for the message content
    String getContent();
}
