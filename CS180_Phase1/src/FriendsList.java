import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
/**
 * FriendsList.java
 *
 * This class provides all the methods necessary for the database to create, add, remove, edit, and compare
 * users to the Friends List - All Writing and removing from the database will be handled by the database
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  - How do we want to handle same names??? Luke Smith or so
 *  - Clarify how Status of friendship acceptance is handled/Set
 *  - How to set/get Timestamp for activation dates - database will have to handle this
 *  - Finalize FreindshipID format
 *
 *
 * @author Thomas Ralston, L105
 *
 * @version March 31, 2024
 *
 */
public class FriendsList extends Object {
    //Fields
    private String user1ID; //Primary Users ID
    private String user2ID; //Friends ID
    private String friendshipID; //Unique identifier given to each friendship
    private String status; //Status of Friendship: Accepted, Pending, Denied
    private String since; //Data friendship was initiated (starts with pending, resets when accepted)
    private String userName1;
    private String userName2;
    private String user1FirstName;
    private String user1LastName;
    private String user2FirstName;
    private String user2LastName;
    //private String user1status; //Status of User1's acceptance of friendship - passed in by database
    //private String user2status; //Status of User2's acceptance of friendship - passed in by database
    private String date; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    //String date = "FIX THIS";  //date that friendship offer was made - resets to date that offer accepted or declined

    //Find and set userIDs given usernames
    public FriendsList(String userID1, String userID2, String status, String date) {
        this.user1ID = userID1;
        this.user2ID = userID2;
        this.status = status;
        this.date = date;
        boolean foundUser1 = false;
        boolean foundUser2 = false;
        //Create Friendship ID
        this.friendshipID = String.format("FID_%s_%s_%s_%s", user1ID, user2ID, status, date); //Temp. friendshipID
    }

    //Sets userIDs given users Names
    public FriendsList(String user1FirstName, String user1LastName, String user2FirstName, String user2LastName,
                       String status, String date) {
        this.user1FirstName = user1FirstName;
        this.user1LastName = user1LastName;
        this.user2FirstName = user2FirstName;
        this.user2LastName = user2LastName;
        this.status = status;
        this.date = date;
        boolean foundUser1 = false;
        boolean foundUser2 = false;

        //Search through userprofile.txt to find two accounts
        try {
            //FileReader freader1 = new FileReader("userprofile.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("userProfileList.txt"));

            String checkLine;
            //Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,
            // hobby4,homeLocation,usersRegion,collegeName;
            while ((checkLine = reader1.readLine()) != null && !foundUser1 && !foundUser2) {
                String[] selectedLine = checkLine.split(","); //Splits line up by comma to search
                if (selectedLine[2].equals(user1FirstName) && (selectedLine[3].equals(user1LastName)) && !foundUser1) {
                    //Check and see if user1 name matches
                    user1ID = selectedLine[0];
                    foundUser1 = true;
                } else if (selectedLine[2].equals(user2FirstName) && (selectedLine[3].equals(user2LastName)
                        && !foundUser2)) {
                    user2ID = selectedLine[0];
                    foundUser2 = true;
                }
            }
            //Create Friendship ID
            if (foundUser1 && foundUser2) {
                friendshipID = String.format("FID_%s_%s_%s_%s", user1ID, user2ID, status, date); //Temp. friendshipID
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Return String array of Friend's usernames that A user has
    public ArrayList<String> friendsCompList(String username1) {
        ArrayList<String> friendCompList = new ArrayList<>();
        try { //Check to see if friendship exists already
            BufferedReader reader1 = new BufferedReader(new FileReader("friendsList.txt")); //

            String checkUser = username1;
            String checkLine;
            //Friendshiplist format: FID_username1_username2_status
            while ((checkLine = reader1.readLine()) != null) {
                String[] selectedLine = checkLine.split("_");
                if (selectedLine[1].equals(checkUser)) {
                    friendCompList.add(selectedLine[2]); //Adds username2 to friendsCompList
                    //Should we include some type of formatting????????
                }
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        return (friendCompList);
    }

    //Checks if given Friendship "o" is equal to classes friendship
    //Not sure when this will get used, but figured I'd include it
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendsList that)) return false;
        return Objects.equals(getUser1ID(), that.getUser1ID()) && Objects.equals(getUser2ID(), that.getUser2ID())
                && Objects.equals(getFriendshipID(), that.getFriendshipID()) &&
                Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getDate(), that.getDate());
    }

    //SETTERS - To be determined which are required
    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }
    public void setUser2ID(String user2ID) {
        this.user2ID = user2ID;
    }
    public void setFriendshipID(String friendshipID) {
        this.friendshipID = friendshipID;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setSince(String since) {
        this.since = since;
    }
    public void setUsername1(String username1) {
        this.userName1 = username1;
    }
    public void setUsername2(String username2) {
        this.userName2 = username2;
    }
    public void setUser1FirstName(String user1FirstName) {
        this.user1FirstName = user1FirstName;
    }
    public void setUser1LastName(String user1LastName) {
        this.user1LastName = user1LastName;
    }
    public void setUser2FirstName(String user2FirstName) {
        this.user2FirstName = user2FirstName;
    }
    public void setUser2LastName(String user2LastName) {
        this.user2LastName = user2LastName;
    }
    public void setDate(String date) {
        this.date = date;
    }

    //GETTERS - To be determined which are required
    public String getUser1ID() {
        return user1ID;
    }
    public String getUser2ID() {
        return user2ID;
    }
    public String getFriendshipID() {
        return friendshipID;
    }
    public String getStatus() {
        return status;
    }
    public String getSince() {
        return since;
    }
    public String getUserName1() {
        return userName1;
    }
    public String getUsername2() {
        return userName2;
    }
    public String getUser1FirstName() {
        return user1FirstName;
    }
    public String getUser1LastName() {
        return user1LastName;
    }
    public String getUser2FirstName() {
        return user2FirstName;
    }
    public String getUser2LastName() {
        return user2LastName;
    }
    public String getDate() {
        return date;
    }
} //End Class
