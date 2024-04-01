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
    // Setters
    void setmessageID(String messageID);
    void setsenderID(String senderID);
    void setrecipientID(String recipientID);
    void setimestamp(Date timestamp);
    void setcontent(String content);
    void setread(boolean read);

    // Getters
    String getMessageID();
    String getsenderID();
    String getrecipientID();
    Date gettimestamp();
    String getcontent();
    boolean isRead();
    //static String getconvertedContent(String string);
    String tostring();
}
