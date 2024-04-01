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
public class FriendsList extends Object implements FriendsListInterface {
    //Fields
    String user1ID; //Primary Users ID
    String user2ID; //Friends ID
    String friendshipID; //Unique identifier given to each friendship
    String status; //Status of Friendship: Accepted, Pending, Denied
    String since; //Data friendship was initiated (starts with pending, resets when accepted)
    String username1;
    String username2;
    String user1FirstName;
    String user1LastName;
    String user2FirstName;
    String user2LastName;
    String user1status; //Status of User1's acceptance of friendship - passed in by database
    String user2status; //Status of User2's acceptance of friendship - passed in by database
    private static Date date; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    //String date = "FIX THIS";  //date that friendship offer was made - resets to date that offer accepted or declined

    //Find and set userIDs given usernames
    public FriendsList(String username1, String username2, String user1status, String user2status, Date date) {
        this.username1 = username1;
        this.username2 = username2;
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

    //Checks to see if friendshipID already in file (meaning friendship already exists), if true, then return error
    //message, else return friendshipID for database to add to friendslist file.
    public String addFriend() {
        try { //Check to see if friendship exists already
            //FileReader freader1 = new FileReader("friendslist.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("friendslist.txt")); //

            String checkLine;
            boolean match = false;
            String checkID = String.format("FID_%s_%s_%s_%s", user1ID, user2ID, status, date);
            while ((checkLine = reader1.readLine()) != null && !match) {
                //String checkID = String.format("FID_%s_%s", user1ID, user2ID);
                if (checkLine.equals(checkID)) {
                    match = true;
                }
            }
            if (match) { //if match == true, so friendship already exists
                return ("Friendship Record Already Exists"); //Database can do with as like
            } else {
                this.status = "Active";
                return (checkID);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ("ERROR: METHOD FAILED");
    }

    public String removeFriend() {
        try { //Check to see if friendship exists already
            BufferedReader reader1 = new BufferedReader(new FileReader("friendslist.txt")); //

            String checkLine;
            boolean match = false;
            String checkID = String.format("FID_%s_%s_%s_%s", user1ID, user2ID, status, date);
            while ((checkLine = reader1.readLine()) != null && !match) {
                if (checkLine.equals(checkID)) {
                    match = true;
                }
            }
            if (match) { //if match == true, friendship exists
                this.status = "Declined";
                return (checkID); //returns FriendshipID to database to be searched for and removed from file
            } else {
                return ("Friendship Record Not Found"); //Database can do with as like
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ("ERROR: METHOD FAILED");
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
        return Objects.equals(getuser1ID(), that.getuser1ID()) && Objects.equals(getuser2ID(), that.getuser2ID())
                && Objects.equals(getfriendshipID(), that.getfriendshipID()) &&
                Objects.equals(getstatus(), that.getstatus()) && Objects.equals(getdate(), that.getdate());
    }

    //SETTERS - To be determined which are required
    public void setuser1ID(String user1ID) {
        this.user1ID = user1ID;
    }
    public void setuser2ID(String user2ID) {
        this.user2ID = user2ID;
    }
    public void setfriendshipID(String friendshipID) {
        this.friendshipID = friendshipID;
    }
    public void setstatus(String status) {
        this.status = status;
    }
    public void setsince(String since) {
        this.since = since;
    }
    public void setusername1(String username1) {
        this.username1 = username1;
    }
    public void setusername2(String username2) {
        this.username2 = username2;
    }
    public void setuser1FirstName(String user1FirstName) {
        this.user1FirstName = user1FirstName;
    }
    public void setuser1LastName(String user1LastName) {
        this.user1LastName = user1LastName;
    }
    public void setuser2FirstName(String user2FirstName) {
        this.user2FirstName = user2FirstName;
    }
    public void setuser2LastName(String user2LastName) {
        this.user2LastName = user2LastName;
    }
    public void setuser1status(String user1status) {
        this.user1status = user1status;
    }
    public void setuser2status(String user2status) {
        this.user2status = user2status;
    }
    public void setdate(Date date) {
        this.date = date;
    }

    //GETTERS - To be determined which are required
    public String getuser1ID() {
        return user1ID;
    }
    public String getuser2ID() {
        return user2ID;
    }
    public String getfriendshipID() {
        return friendshipID;
    }
    public String getstatus() {
        return status;
    }
    public String getsince() {
        return since;
    }
    public String getusername1() {
        return username1;
    }
    public String getusername2() {
        return username2;
    }
    public String getuser1FirstName() {
        return user1FirstName;
    }
    public String getuser1LastName() {
        return user1LastName;
    }
    public String getuser2FirstName() {
        return user2FirstName;
    }
    public String getuser2LastName() {
        return user2LastName;
    }
    public String getuser1status() {
        return user1status;
    }
    public String getuser2status() {
        return user2status;
    }
    public Date getdate() {
        return date;
    }
} //End Class
