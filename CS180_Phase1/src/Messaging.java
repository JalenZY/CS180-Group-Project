import java.util.Date;
/**
 * Messaging.java
 *
 * This class contains all the methods required to format and store all Direct Messaging messages
 * to the messaging.txt file. The writing of this file will be handled by the database, but all
 * formatting will be taken care by this class
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * - Format Date like: MM-dd-yyyy HH:mm:ss  -- Military Time
 * - Static Variables!!!!?????????
 * - Check Message "encryption"
 *
 *
 * @author Thomas Ralston, L105
 *
 * @version March 31, 2024
 *
 */
public class Messaging {

    //Fields
    private String messageID; //Unique Message ID
    private static String senderID; //userID of sender
    private static String recipientID; //UserID of recipient
    private static Date timestamp; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    private static String content; //Message Content
    private boolean isRead; //Status of message - if been read or not

    //Constructor - initialize values
    //MessageID Format: "senderID,recipientID,timestamp"
    public Messaging(String messageID, String senderID, String recipientID, Date timestamp, String content) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.timestamp = timestamp;
        this.content = content.replace(",",".--."); //Encrypt message by replacing all commas with dashes, that way
        // Split(",") doesn't prematurely split messages
        this.isRead = false;
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
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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
    public static String getSenderID() {
        return senderID;
    }
    public static String getRecipientID() {
        return recipientID;
    }
    public static Date getTimestamp() {
        return timestamp;
    }
    public static String getContent() {
        content.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
        return content;
    }

    public static String getConvertedContent(String string) { //Un-encrypt the given encrypted message string
        string.replace(".--.",","); //Un-Encrypt message by replacing all dashes with commas, that way
        return content;
    }
    public boolean isRead() {
        return isRead;
    }

    //Prints given content in format ready for messaging.txt file
    public String toString() {
        //Format: "messageID//senderID//recipientID//timestamp//isRead//content"
        return (String.format(("%s//%s//%s//%s//%s//%s"), messageID, senderID, recipientID, timestamp,isRead,
                Messaging.getContent()));

    }
} //End Class