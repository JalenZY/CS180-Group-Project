import java.util.ArrayList;
import java.util.Date;

/**
 * BlockedListInterface.java
 * <p>
 * Lists all the methods and their paramaters used by BlockedList.java
 *
 * @author ThomasRalton, L105
 * @version April 15, 2024
 */
public interface BlockedListInterface {
    // Method to compare the blocked list of a user
    ArrayList<String> blockedCompList(String username1);

    // Method to check if two BlockedList objects are equal
    boolean equals(Object o);

    // Getters
    String getUser1ID();
    String getUser2ID();
    String getBlockedID();
    String getStatus();
    String getSince();
    String getUserName1();
    String getUserName2();
    String getUser1FirstName();
    String getUser1LastName();
    String getUser2FirstName();
    String getUser2LastName();
    String getDate();

    // Setters
    void setUser1ID(String user1ID);
    void setUser2ID(String user2ID);
    void setBlockedID(String blockedID);
    void setStatus(String status);
    void setSince(String since);
    void setUserName1(String username1);
    void setUserName2(String username2);
    void setUser1FirstName(String user1FirstName);
    void setUser1LastName(String user1LastName);
    void setUser2FirstName(String user2FirstName);
    void setUser2LastName(String user2LastName);
    void setDate(String date);
}
