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
    String user1ID; //Primary Users ID
    String user2ID; //Friends ID
    String friendshipID; //Unique identifier given to each friendship
    String status; //Status of Friendship: Accepted, Pending, Denied
    String since; //Data friendship was initiated (starts with pending, resets when accepted)
    String userName1;
    String userName2;
    String user1FirstName;
    String user1LastName;
    String user2FirstName;
    String user2LastName;
    String user1status; //Status of User1's acceptance of friendship - passed in by database
    String user2status; //Status of User2's acceptance of friendship - passed in by database
    private Date date; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    //String date = "FIX THIS";  //date that friendship offer was made - resets to date that offer accepted or declined

    //Find and set userIDs given usernames
    public FriendsList(String username1, String username2, String user1status, String user2status, Date date) {
        this.userName1 = username1;
        this.userName2 = username2;
        this.user1status = user1status;
        this.user2status = user2status;
        this.date = date;
        boolean foundUser1 = false;
        boolean foundUser2 = false;

        //Set Status (Active + Active = Active) (Active + Pending = Pending) (Active + Declined = Declined)
        if (user1status.equals("Active") && user2status.equals("Active")) {
            this.status = "Active";
        } else if (user1status.equals("Pending") || user2status.equals("Pending")) {
            this.status = "Pending";
        } else if (user1status.equals("Declined") || user2status.equals("declined")) {
            this.status = "Declined";
        }

        //Search through userprofile.txt to find two accounts
        try {
            //FileReader freader1 = new FileReader("userprofile.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("userprofile.txt"));

            String checkLine;
            //Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,
            // hobby4,homeLocation,usersRegion,collegeName;
            //Run through file to find username1
            while (((checkLine = reader1.readLine()) != null) && !foundUser1 && !foundUser2) {
                String[] selectedLine = checkLine.split(","); //Splits line up by comma to search
                if (selectedLine[1].equals(username1) && !foundUser1) {
                    user1ID = selectedLine[0];
                    foundUser1 = true;
                } else if (selectedLine[1].equals(username2) && !foundUser2) {
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

    //Sets userIDs given users Names
    public FriendsList(String user1FirstName, String user1LastName, String user2FirstName, String user2LastName,
                       String user1status, String user2status, Date date) {
        this.user1FirstName = user1FirstName;
        this.user1LastName = user1LastName;
        this.user2FirstName = user2FirstName;
        this.user2LastName = user2LastName;
        this.date = date;
        boolean foundUser1 = false;
        boolean foundUser2 = false;

        //Set Status (Active + Active = Active) (Active + Pending = Pending) (Active + Declined = Declined)
        if (user1status.equals("Active") && user2status.equals("Active")) {
            this.status = "Active";
        } else if (user1status.equals("Pending") || user2status.equals("Pending")) {
            this.status = "Pending";
        } else if (user1status.equals("Declined") || user2status.equals("declined")) {
            this.status = "Declined";
        }

        //Search through userprofile.txt to find two accounts
        try {
            //FileReader freader1 = new FileReader("userprofile.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("userprofile.txt"));

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
            BufferedReader reader1 = new BufferedReader(new FileReader("friendslist.txt")); //

            String checkuser = username1;
            String checkLine;
            //Friendshiplist format: FID_username1_username2_status
            while ((checkLine = reader1.readLine()) != null) {
                String[] selectedLine = checkLine.split("_");
                if (selectedLine[1].equals(checkuser)) {
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
    public void setUser1status(String user1status) {
        this.user1status = user1status;
    }
    public void setUser2status(String user2status) {
        this.user2status = user2status;
    }
    public void setDate(Date date) {
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
    public String getUser1Status() {
        return user1status;
    }
    public String getUser2Status() {
        return user2status;
    }
    public Date getDate() {
        return date;
    }
} //End Class
