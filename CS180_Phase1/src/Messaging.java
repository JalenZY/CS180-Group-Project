import java.io.IOException;
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
 * @version April 15, 2024
 *
 */
public class Messaging {

    //Fields
    private String messageID; //Unique Message ID
    private String senderID; //userID of sender
    private String recipientID; //UserID of recipient
    private String date; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    private String content; //Message Content
    private boolean isRead; //Status of message - if been read or not
    private String conversationID;


    //Constructor - initialize values
    public Messaging(String messageID, String conversationID,  String senderID, String recipientID, String date, String content) {
        this.messageID = messageID; //This will be created as a count for everytime a message is sent
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.date = date;
        this.content = content.replace(",", ".--."); //Encrypt message by replacing all commas with dashes, that way
        // Split(",") doesn't prematurely split messages
        this.isRead = false;
        this.conversationID = conversationID;
    }

    //Setters
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setRead(boolean read) {
        isRead = read;
    }

    //Getters
    public String getMessageID() {
        return messageID;
    }
    public String getConversationID() {
        return (conversationID);
    }
    public String getSenderID() {
        return senderID;
    }
    public String getRecipientID() {
        return recipientID;
    }
    public String getDate() {
        return date;
    }
    public boolean isRead() {
        return isRead;
    }
    public String getContent() {
        //content.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
        return content.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
    }

    public static String getConvertedContent(String string) { //Un-encrypt the given encrypted message string
        //string.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
        return string.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way;
    }

    //Prints given content in format ready for messaging.txt file
    public String toString() {
        //Format: "conversationID//messageID//senderID//recipientID//date//content"
        return (String.format(("%s//%s//%s//%s//%s//%s"), conversationID, messageID, senderID, recipientID, date,
                content)); //.replace(".--.",",")

    }

//    public void writeToMessagesFile(String filename) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
//            // Format the Messaging object for writing to file
//            String formattedMessage = this.toString();
//            // Write the formatted message to the file
//            writer.write(formattedMessage);
//            writer.newLine(); // Add a newline after each message
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
} //End Class
