import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
/**
 * BlockedList.java
 *
 * This class provides all the methods necessary for the database to create, add, remove, edit, and compare
 * users to the Blocked List - This class also writes and removes information to the blockedlist.txt
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  - If user is unblocked, should we remove them from blocked list or just change their status to non-active????
 *  - Finalize BlockedID string format
 *  - Fix Date
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
    private String date; //TimeStamp passed in by database - format:MM-dd-yyyy HH:mm:ss  -- Military Time
    //String date = "FIX THIS";  //date that friendship offer was made - resets to date that offer accepted or declined

    public BlockedList(String user1ID, String user2ID, String date) {
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.date = date;
        blockedID = String.format("BID_%s_%s_%s", user1ID, user2ID, date); //Temporary BlockedID
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
            BufferedReader reader1 = new BufferedReader(new FileReader("userProfileList.txt"));

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

    //FIIIIIIIIIIIIIIIIIIIXXXXXXXXXXXXX!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Will need to sort through userProfile databse to get username or userID respectively
    //Return String array of Blocked usernames that A user has
    public ArrayList<String> blockedCompList(String username1) {
        ArrayList<String> blockedCompList = new ArrayList<>();
        try { //Check to see if friendship exists already
            BufferedReader reader1 = new BufferedReader(new FileReader("blockedUserList.txt")); //

            String checkUser = username1; //finding all blocked contacts for this user
            String checkLine;
            //blockedlist format: BID_userID1_userID2_date
            while ((checkLine = reader1.readLine()) != null) {
                String[] selectedLine = checkLine.split("_");
                if (selectedLine[1].equals(checkUser)) {
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
                that.getUser2ID()) && Objects.equals(getBlockedID(), that.getBlockedID()) && Objects.equals(getStatus(),
                that.getStatus()) && Objects.equals(getDate(), that.getDate());
    }

    //SETTERS - To be determined which are required
    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }
    public void setUser2ID(String user2ID) {
        this.user2ID = user2ID;
    }
    public void setBlockedID(String blockedID) {
        this.blockedID = blockedID;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setSince(String since) {
        this.since = since;
    }
    public void setUserName1(String username1) {
        this.username1 = username1;
    }
    public void setUserName2(String username2) {
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
    public String getBlockedID() {
        return blockedID;
    }
    public String getStatus() {
        return status;
    }
    public String getSince() {
        return since;
    }
    public String getUserName1() {
        return username1;
    }
    public String getUserName2() {
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

//Find and set userIDs given usernames
//    public BlockedList(String username1, String username2, Date date) {
//        this.username1 = username1;
//        this.username2 = username2;
//        this.date = date;
//        boolean foundUser1 = false;
//        boolean foundUser2 = false;
//
//        //Search through userprofile.txt to find two accounts
//        try {
//            //FileReader freader1 = new FileReader("userprofile.txt");
//            BufferedReader reader1 = new BufferedReader(new FileReader("userProfileList.txt"));
//
//            String checkLine;
//            //Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,
//            // hobby1,hobby2,hobby3,hobby4,
//            // homeLocation,usersRegion,collegeName;
//            while (((checkLine = reader1.readLine()) != null) && !foundUser1 && !foundUser2) {
//                //Run through file to find username1
//                String[] selectedLine = checkLine.split(","); //Splits line up by comma to search
//                if (selectedLine[1].equals(username1) && !foundUser1) {
//                    user1ID = selectedLine[0];
//                    foundUser1 = true;
//                } else if (selectedLine[1].equals(username2) && !foundUser2) {
//                    user2ID = selectedLine[0];
//                    foundUser2 = true;
//                }
//            }
//            //Create BlockedID
//            if (foundUser1 && foundUser2) {
//                blockedID = String.format("BID_%s_%s_%s", user1ID, user2ID, date); //Temporary BlockedID
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    public String toString() {
//        return (String.format("BID_%s_%s_%s", user1ID,user2ID, date));
//    }
//
//    public String toString(String user1ID, String user2ID, String date) {
//        return (String.format("BID_%s_%s_%s", user1ID,user2ID, date));
//    }
