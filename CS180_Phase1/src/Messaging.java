import java.io.IOException;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
/**
 * Messaging.java
 *
 * This class contains all the methods required to format and store all Direct Messaging messages
 * to the messaging.txt file. This class also writes to the messages.txt file when a new message is created
 * Note, This class also "encrypts" the messages by replacing all commas with ".--.", this is done
 * to make sure that there are no errors when using split(",") later on in the classes.
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * - Format Date like: MM-dd-yyyy HH:mm:ss  -- Military Time
 * - Static Variables!!!!?????????
 * - Check Message "encryption"
 *
 * @author Thomas Ralston, L105
 *
 * @version March 31, 2024
 *
 */
public class Messaging implements MessagingInterface {

    //Fields
    private String messageID; //Unique Message ID
    public String senderID; //userID of sender
    public String recipientID; //UserID of recipient
    private Date timestamp; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    private String content; //Message Content
    private boolean isRead; //Status of message - if been read or not

    //Constructor - initialize values
    //MessageID Format: "senderID,recipientID,timestamp"
    public Messaging(String messageID, String senderID, String recipientID, Date timestamp, String content) {
        this.messageID = messageID; //This will be created as a count for everytime a message is sent
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.timestamp = timestamp;
        this.content = content.replace(",",".--."); //Encrypt message by replacing all commas with dashes, that way
        // Split(",") doesn't prematurely split messages
        this.isRead = false;

        Messaging message = new Messaging(messageID, senderID, recipientID, timestamp,content);
// Call other methods to set message details if needed
        message.writeToMessagesFile("messages.txt");
    }

    //Setters
    public void setmessageID(String messageID) {
        this.messageID = messageID;
    }
    public void setsenderID(String senderID) {
        this.senderID = senderID;
    }
    public void setrecipientID(String recipientID) {
        this.recipientID = recipientID;
    }
    public void setimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public void setcontent(String content) {
        this.content = content;
    }
    public void setread(boolean read) {
        isRead = read;
    }

    //Getters
    public String getMessageID() {
        return messageID;
    }
    public String getsenderID() {
        return senderID;
    }
    public String getrecipientID() {
        return recipientID;
    }
    public Date gettimestamp() {
        return timestamp;
    }
    public String getcontent() {
        //content.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
        return content.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
    }

    public static String getconvertedContent(String string) { //Un-encrypt the given encrypted message string
        //string.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
        return string.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way;
    }
    public boolean isRead() {
        return isRead;
    }

    //Prints given content in format ready for messaging.txt file
    public String tostring() {
        //Format: "messageID//senderID//recipientID//timestamp//isRead//content"
        return (String.format(("%s//%s//%s//%s//%s//%s"), messageID, senderID, recipientID, timestamp,isRead,
                content.replace(".--.",",")));

    }

    public void writeToMessagesFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // Format the Messaging object for writing to file
            String formattedMessage = this.toString();
            // Write the formatted message to the file
            writer.write(formattedMessage);
            writer.newLine(); // Add a newline after each message
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} //End Class
