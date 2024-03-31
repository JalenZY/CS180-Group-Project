import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
/**
 * BlockedList.java
 *
 * This class provides all the methods necessary for the database to create, add, remove, edit, and compare
 * users to the Blocked List - All Writing and removing from the database will be handled by the database
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  - If user is unblocked, should we remove them from blocked list or just change their status to non-active????
 *  - Finalize BlockedID string format
 * - Fix Date
 *
 *
 * @author Thomas Ralston, L105
 *
 * @version March 31, 2024
 *
 */
public class BlockedList extends Object {
    //Fields
    String user1ID; //Primary Users ID
    String user2ID; //Blocked Users ID
    String blockedID; //Unique identifier given to each Blocked relationship
    String status; //Status of Block: Accepted
    String since; //Date block was initiated
    String username1;
    String username2;
    String user1FirstName;
    String user1LastName;
    String user2FirstName;
    String user2LastName;
    String date = "FIX THIS";  //date that friendship offer was made - resets to date that offer accepted or declined

    //Find and set userIDs given usernames
    public BlockedList(String username1, String username2, String date) {
        this.username1 = username1;
        this.username2 = username2;
        this.date = date;
        boolean foundUser1 = false;
        boolean foundUser2 = false;

        //Search through userprofile.txt to find two accounts
        try {
            //FileReader freader1 = new FileReader("userprofile.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("userprofile.txt"));

            String checkLine;
            //Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,
            // hobby1,hobby2,hobby3,hobby4,
            // homeLocation,usersRegion,collegeName;
            while (((checkLine = reader1.readLine()) != null) && !foundUser1 && !foundUser2) {
                //Run through file to find username1
                String[] selectedLine = checkLine.split(","); //Splits line up by comma to search
                if (selectedLine[1].equals(username1) && !foundUser1) {
                    user1ID = selectedLine[0];
                    foundUser1 = true;
                } else if (selectedLine[1].equals(username2) && !foundUser2) {
                    user2ID = selectedLine[0];
                    foundUser2 = true;
                }
            }
            //Create BlockedID
            if (foundUser1 && foundUser2) {
                blockedID = String.format("BID_%s_%s_%s", user1ID, user2ID, date); //Temporary BlockedID
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sets userIDs given users Names
    public BlockedList(String user1FirstName, String user1LastName, String user2FirstName, String user2LastName,
                       String date) {
        this.user1FirstName = user1FirstName;
        this.user1LastName = user1LastName;
        this.user2FirstName = user2FirstName;
        this.user2LastName = user2LastName;
        this.date = date;
        boolean foundUser1 = false;
        boolean foundUser2 = false;

        //Search through userprofile.txt to find two accounts
        try {
            //FileReader freader1 = new FileReader("userprofile.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("userprofile.txt"));

            String checkLine;
            //Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,
            // hobby4, homeLocation,usersRegion,collegeName;
            while ((checkLine = reader1.readLine()) != null && !foundUser1 && !foundUser2) {
                String[] selectedLine = checkLine.split(","); //Splits line up by comma to search
                //Check and see if user1 name matches
                if (selectedLine[2].equals(user1FirstName) && (selectedLine[3].equals(user1LastName)) && !foundUser1) {
                    user1ID = selectedLine[0];
                    foundUser1 = true;
                } else if (selectedLine[2].equals(user2FirstName) && (selectedLine[3].equals(user2LastName)
                        && !foundUser2)) {
                    user2ID = selectedLine[0];
                    foundUser2 = true;
                }
            }
            //Create blockedID
            if (foundUser1 && foundUser2) {
                blockedID = String.format("BID_%s_%s_%s", user1ID, user2ID, date); //Temporary BlockedID - need Finaliz

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Checks to see if blockedID already in file (meaning friendship already exists), if true, then return error
    //message, else return blockedID for database to add to friendslist file.
    public String AddBlock() {
        try { //Check to see if block already exists
            //FileReader freader1 = new FileReader("friendslist.txt");
            BufferedReader reader1 = new BufferedReader(new FileReader("blockedlist.txt")); //

            String checkLine;
            boolean match = false;
            String checkID = String.format("BID_%s_%s_%s", user1ID, user2ID, date);
            while ((checkLine = reader1.readLine()) != null && !match) {
                //String checkID = String.format("FID_%s_%s", user1ID, user2ID);
                if (checkLine.equals(checkID)) {
                    match = true; //Check to make sure record doesn't already exist
                }
            }
            if (match) { //if match == true, so friendship already exists
                return ("Block Record Already Exists"); //Database can do with as like
            } else {
                return (checkID);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ("ERROR: METHOD FAILED");
    }

    public String RemoveBlock() {
        try { //Check to see if friendship exists already
            BufferedReader reader1 = new BufferedReader(new FileReader("blockedlist.txt")); //

            String checkLine;
            boolean match = false;
            String checkID = String.format("BID_%s_%s_%s", user1ID, user2ID, date);
            while ((checkLine = reader1.readLine()) != null && !match) {
                if (checkLine.equals(checkID)) {
                    match = true;
                }
            }
            if (match) { //if match == true, friendship exists
                return (checkID); //returns blockedID to database to be searched for and removed from file
            } else {
                return ("Block Records Not Found"); //Database can do with as like
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ("ERROR: METHOD FAILED");
    }

    //Return String array of Blocked usernames that A user has
    public ArrayList<String> BlockedCompList(String username1) {
        ArrayList<String> blockedCompList = new ArrayList<>();
        try { //Check to see if friendship exists already
            BufferedReader reader1 = new BufferedReader(new FileReader("blockedlist.txt")); //

            String checkuser = username1; //finding all blocked contacts for this user
            String checkLine;
            //blockedlist format: BID_username1_username2_status
            while ((checkLine = reader1.readLine()) != null) {
                String[] selectedLine = checkLine.split("_");
                if (selectedLine[1].equals(checkuser)) {
                    blockedCompList.add(String.format(selectedLine[2])); //Adds username2 to BlockedCompList
                    //Should we include some type of formatting????????
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return (blockedCompList);
    }

    //Checks if given Block "o" is equal to classes Blocked
    //Not sure when this will get used, but figured I'd include it
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockedList that)) return false;
        return Objects.equals(getUser1ID(), that.getUser1ID()) && Objects.equals(getUser2ID(),
                that.getUser2ID()) && Objects.equals(getblockedID(), that.getblockedID()) && Objects.equals(getStatus(),
                that.getStatus()) && Objects.equals(getDate(), that.getDate());
    }

    //SETTERS - To be determined which are required
    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }
    public void setUser2ID(String user2ID) {
        this.user2ID = user2ID;
    }
    public void setblockedID(String blockedID) {
        this.blockedID = blockedID;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setSince(String since) {
        this.since = since;
    }
    public void setUsername1(String username1) {
        this.username1 = username1;
    }
    public void setUsername2(String username2) {
        this.username2 = username2;
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
    public String getblockedID() {
        return blockedID;
    }
    public String getStatus() {
        return status;
    }
    public String getSince() {
        return since;
    }
    public String getUsername1() {
        return username1;
    }
    public String getUsername2() {
        return username2;
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

