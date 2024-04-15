import java.io.*;
import java.util.*;
/**
 * PeopleDatabase.java
 *
 * The Operating Database that contains and connects all user interactions......
 *
 * @author Zhengyi Jiang/Thomas Ralston, L105
 * @version April 15, 2024
 */
public class PeopleDatabase {
    private ArrayList<String> users; // Key: userID, Value: UserProfile
    private ArrayList<String> friendships; // Key: friendshipID, Value: status
    private ArrayList<String> blockedUsers; // Key: blockedID, Value: status
    private final String userProfileList; //FileName of BlockedUserList File
    private final String friendshipList; //FileName of FriendshipList File
    private final String blockedUserList; //File name of userProfile File

    //Constructor
    public PeopleDatabase(String userProfileList, String friendshipList, String blockedUserList) {
        this.userProfileList = userProfileList; //FileName of userProfile File
        this.friendshipList = friendshipList; //FileName of FriendshipList File
        this.blockedUserList = blockedUserList; //FileName of BlockedUserList File
    }

    //Helper Method that Writes Text Files as an ArrayList
    public ArrayList<String> readFileToArray(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //Skip empty lines or lines with only whitespace characters
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
            return lines;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }

    //Method to rewrite text Files
    public void writeChangesToFile(String filename, ArrayList<String> updatedInfo) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            //Write each element of the updatedProfile ArrayList to a new line in the file
            // Check if the line is not null before writing
            for (String line : updatedInfo) {
                if (line != null) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    // Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,
    // hobby4,homeLocation,usersRegion,collegeName;

    // Add a new user to the database
    public boolean addUser(UserProfile user) { //Client clicks on add button and server sends User2 profile to addUser
        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
        boolean contains = false;

        if (user.toString() != null) { //Stop Duplicates
            String line2;
            for (int i = 0; i < users.size() && !contains; i++) {
                line2 = users.get(i);
                contains = line2.contains(user.getUserID()); //Check if line contains userID
            }
        }

        if (!contains) { //If UserID is not in users, then add users
            users.add(user.toString()); //Update ArrayList
            writeChangesToFile(userProfileList, users); //Write new changes to Text File
            return true;
        }
        return false;
    }

    // Remove a user from the database
    public boolean removeUser(String userID) { //Remove UserID from system
        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
        //Iterate through arrayList scanning each line for userID
        for (int i = 0; i < users.size(); i++) {
            String userString = users.get(i);

            //Check if the userString contains the userID
            if (userString.contains(userID)) {
                //If userID is found, remove entire row from the ArrayList
                users.remove(i);
                writeChangesToFile(userProfileList, users); //Write new changes to Text File
                return true;
            }
        }
        return false;
    }

    // Update a user's profile
    // A userProfile needs to be passed in
    public boolean updateUserProfile(String userID, String toBeUpdated, String updatedPart) {
        //UserID: ID of User Updating Profile
        //toBeUpdated: Component of Profile to be Updated (Password, Name .....) -- Import UserProfile Field Names
        //updatedParts: Replacement String
        UserProfile userprofile = new UserProfile(userID); //This UserID MUST EXIST!!!!!!!!!!!!!
        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
        String line2;
        boolean contains = false;
        for (int i = 0; i < users.size() && !contains; i++) {
            line2 = users.get(i);
            contains = line2.contains(userID); //Check if line contains userID
        }
        //boolean contains = users.contains(userID);
        if (contains) { //Check UserID exists in List
            String updatedList = "";
            boolean updated = false;
            switch (toBeUpdated) {
                case "userName" -> {
                    userprofile.setUserName(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "userFirstName" -> {
                    userprofile.setUserFirstName(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "userLastName" -> {
                    userprofile.setUserLastName(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "Password" -> {
                    userprofile.setPassword(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "birthday" -> {
                    userprofile.setBirthday(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "gender" -> {
                    userprofile.setGender(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "hobby1" -> {
                    userprofile.setHobby1(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "hobby2" -> {
                    userprofile.setHobby2(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "hobby3" -> {
                    userprofile.setHobby3(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "hobby4" -> {
                    userprofile.setHobby4(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "homeLocation" -> {
                    userprofile.setHomeLocation(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "usersRegion" -> {
                    userprofile.setUsersRegion(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                case "collegeName" -> {
                    userprofile.setCollegeName(updatedPart);
                    updatedList = (userprofile.toString());
                    updated = true;
                }
                default -> {
                    return false;
                    //System.out.println("Error Update Not Completed"); //Throw error if updated not completed
                }
            }
            if (updated) { //Check if update really happened
                return (removeUser(userID) && addUser(userprofile)); //Remove old and Add new userProfile String
                //Return False, if either method has an issue but True if both check out
            }
        }
        return false;
    } //End Method

    // Block a user
    public boolean blockUser(String blockerID, String blockedID, String date) {
        //Date Created when User Blocks - Server Creates THIS!!!!!!!!!!!!!!!!!!!!!!!
        //blockerID: user1ID
        //blockedID: user2ID
        blockedUsers = readFileToArray(blockedUserList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        //BlockerID Format: "BID_user1ID_user2ID_date"
        //String blockRecord = block.addBlock(); // blockRecord = "BID_user1ID_user2ID_date"

        //boolean alreadyBlocked = false;
        //Iterate through arrayList scanning each line for userID
        for (int i = 0; i < blockedUsers.size(); i++) {
            String userString = blockedUsers.get(i);
            //Check if the userString contains the blockIDs
            if (userString.contains(block.getUser1ID()) && userString.contains(block.getUser2ID())) {
                //If blockID is found, then don't add to list by setting boolean to true
                //alreadyBlocked = true;
                return false; //False for user already blocked/blocking error
            }
        }
        //if (!alreadyBlocked) { //if not already on blocked list, then add
        blockedUsers.add(block.getBlockedID());
        writeChangesToFile(blockedUserList, blockedUsers); //Write new changes to Text File
        return true; //True for block created
        //}
    }


    // Unblock a user
    public boolean unblockUser(String blockerID, String blockedID, String date) {
        //Date Created when User Blocks - Server Creates THIS!!!!!!!!!!!!!!!!!!!!!!!
        //blockerID: user1ID
        //blockedID: user2ID
        //BlockerID Format: "BID_user1ID_user2ID_date"

        blockedUsers = readFileToArray(blockedUserList); //Takes UserProfile.txt and makes it into an array List
        //boolean found = false;
        //Iterate through arrayList scanning each line for userID
        for (int i = 0; i < blockedUsers.size(); i++) {
            String userString = blockedUsers.get(i);
            //Check if the userString contains the userID
            if (userString.contains(blockerID) && userString.contains(blockedID)) {
                //If userID is found, remove entire row from the ArrayList
                blockedUsers.remove(i);
                //found = true;
                writeChangesToFile(blockedUserList, blockedUsers); //Write new changes to Text File
                return true; //True for user been removed from blocked list
            }
        }
        return false; //False for user not being removed from blocked list
    }

    // Add a friend
    // Method used for when User1 initiates a Friend Request to User2
    public boolean addFriend(String user1ID, String user2ID, String date) {
        //STATUS!!!! - This will be set as Pending automatically, and moved to Accepted or Denied after user2 acts
        //Need an accept friend request method
        //FriendshipList format: FID_username1_username2_status_date

        friendships = readFileToArray(friendshipList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        FriendsList friends = new FriendsList(user1ID, user2ID, "Pending", date); //Pending because this is first initiation
        boolean alreadyFriends = false;
        //Iterate through arrayList scanning each line for userID
        for (int i = 0; i < friendships.size() && !alreadyFriends; i++) {
            String userString = friendships.get(i);
            //Check if the userString contains the blockIDs
            if (userString.contains(friends.getUser1ID()) && userString.contains(friends.getUser2ID())) {
                //If FriendshipID is found, then don't add to list by setting boolean to true
                alreadyFriends = true;
                return false; //False for user already existing
            }
        }
        if (!alreadyFriends) { //if not already on Friends list, then add
            friendships.add(friends.getFriendshipID());
            writeChangesToFile(friendshipList, friendships); //Write new changes to Text File
            return true; //True for user being added to friends list
        }
        return false;
    }

    //Accept a Friend Request
    public boolean acceptFriend (String user1ID, String user2ID, String date) {
        //FriendshipList format: FID_username1_username2_status_date
        //Set Status to Active:
        FriendsList accepted = new FriendsList(user1ID, user2ID, "Active", date); //Status set to Active

        //Remove old friendshipID from textFile
        removeFriend(user1ID, user2ID, date);

        //Add new FriendshipID to textFile
        friendships = readFileToArray(friendshipList); //ArrayList containing current text file of all friendships
        //boolean alreadyFriends = false;
        //Iterate through arrayList scanning each line for userID
        for (int i = 0; i < friendships.size(); i++) {
            String userString = friendships.get(i);
            //Check if the userString contains the blockIDs
            if (userString.contains(accepted.getUser1ID()) && userString.contains(accepted.getUser2ID())) {
                //If FriendshipID is found, then don't add to list by setting boolean to true
                //alreadyFriends = true;
                return false; //False for friendship already exist/Error
            }
        }
//        if (!alreadyFriends) { //if not already on Friends list, then add
        friendships.add(accepted.getFriendshipID());
        writeChangesToFile(friendshipList, friendships); //Write new changes to Text File
        return true; //True for friendship being successfully accepted
    }

    public boolean deniedFriend (String user1ID, String user2ID, String date) {
        //Remove friendship from text file
        return (removeFriend(user1ID, user2ID, date));
    }

    // Remove a friend
    public boolean removeFriend(String user1ID, String user2ID, String date) {
        //FriendshipList format: FID_username1_username2_status
        friendships = readFileToArray(friendshipList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        //FriendsList friendship = new FriendsList(user1ID, user2ID, "Inactive", date);
        //Do we want to remove friendship from text file or set it as Inactive?

        //Currently removing entire FriendshipID from text file
        boolean found = false;
        //Iterate through arrayList scanning each line for userID
        for (int i = 0; i < friendships.size() && !found; i++) {
            String userString = friendships.get(i);
            //Check if the userString contains the userID
            if (userString.contains(user1ID) && userString.contains(user2ID)) {
                //If userID is found, remove entire row from the ArrayList
                friendships.remove(i);
                found = true;
                writeChangesToFile(friendshipList, friendships); //Write new changes to Text File
                return true; //True for friendslist successfully being edited
            }
        }
        return false;
    }






    //ADD FRIENDS COMPILATION LIST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    //ADD BLOCKED COMPILATION LIST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


} //EndClass
