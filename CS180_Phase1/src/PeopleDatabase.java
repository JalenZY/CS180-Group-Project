import java.io.*;
import java.util.*;

public class PeopleDatabase extends Object {
    private ArrayList<String> users; // Key: userID, Value: UserProfile
    private ArrayList<String> friendships; // Key: friendshipID, Value: status
    private ArrayList<String> blockedUsers; // Key: blockedID, Value: status
    private String userProfileList;
    private String friendshipList;
    private String blockedUserList;

    //Constructor
    public PeopleDatabase(String userProfileList, String friendshipList, String blockedUserList) {
//        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
//        friendships = readFileToArray(friendshipList); //Takes FriendshipList.txt and makes into array List
//        blockedUsers = readFileToArray(blockedUserList); //Takes blockedUser.txt and makes into an array List

        this.userProfileList = userProfileList;
        this.friendshipList = friendshipList;
        this.blockedUserList = blockedUserList;
    }

    public ArrayList<String> readFileToArray(String filename) { //Write Text Files as an ArrayList
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Method to rewrite text Files
    public void writeChangesToFile(String filename, ArrayList<String> updatedProfile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            // Write the updated user profile information to the file -- "false" makes sure that ir rewrites the file
            writer.write(String.valueOf(updatedProfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Format:userID,username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,
    // hobby4,homeLocation,usersRegion,collegeName;

    // Add a new user to the database
    public void addUser(UserProfile user) { //Client clicks on add button and server sends User2 profile to addUser
        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
        if (!users.contains(user.getUserName())) { //If Username is not in users, then add users
            users.add(user.getUserName()); //Update ArrayList
        } else { //Display message stating already exist
            System.out.println("UserID already exists."); //??????
        }
        writeChangesToFile(userProfileList, users); //Write new changes to Text File

    }

    // Remove a user from the database
    public void removeUser(String userID) { //Remove UserID from system
        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
        if (users.contains(userID)) {
            users.remove(getRowNumber(users,userID)); //Delete entire User Row from Array List
        } else {
            System.out.println("User does not exist.");
        }
        writeChangesToFile(userProfileList, users); //Write new changes to Text File
    }

    public int getRowNumber(ArrayList<String> list, String value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(value)) {
                // Return the index of the first occurrence of the value
                return i;
            }
        }
        // If the value is not found, return -1 or throw an exception
        return -1; // or throw new IllegalArgumentException("Value not found");
    }

    // Update a user's profile
    public void updateUserProfile(String userID, String toBeUpdated, String updatedPart) {
        //UserID: ID of User Updating Profile
        //toBeUpdated: Component of Profile to be Updated (Password, Name .....) -- Import UserProfile Field Names
        //updatedParts: Replacement String
        UserProfile userprofile = new UserProfile(userID);
        users = readFileToArray(userProfileList); //Takes UserProfile.txt and makes it into an array List
        if (users.contains(userID)) { //Check UserID exists in List
            String updatedList = "";
            boolean updated = false;
            switch (toBeUpdated) {
                case "userName" -> {
                    userprofile.setUserName(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "userFirstName" -> {
                    userprofile.setUserFirstName(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "userLastName" -> {
                    userprofile.setUserLastName(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "Password" -> {
                    userprofile.setPassword(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "birthday" -> {
                    userprofile.setBirthday(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "gender" -> {
                    userprofile.setGender(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "hobby1" -> {
                    userprofile.setHobby1(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "hobby2" -> {
                    userprofile.setHobby2(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "hobby3" -> {
                    userprofile.setHobby3(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "hobby4" -> {
                    userprofile.setHobby4(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "homeLocation" -> {
                    userprofile.setHomeLocation(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "usersRegion" -> {
                    userprofile.setUsersRegion(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                case "collegeName" -> {
                    userprofile.setCollegeName(updatedPart);
                    updatedList = String.valueOf(userprofile.toString());
                    updated = true;
                }
                default -> {
                    System.out.println("Error Update Not Completed"); //Throw error if updated not completed
                }
            }
            if (updated) { //Check if update really happened
                users.remove(getRowNumber(users, userID)); //Remove old String
                users.add(updatedList); //Add new Users List
                writeChangesToFile(userProfileList, users); //Write new changes to Text File
            }
        }
        else {
            System.out.println("User does not exist.");
        }
    } //End Method

    // Block a user
    public void blockUser(String blockerID, String blockedID, Date date) {
        //Date Created when User Blocks - Server Creates THIS!!!!!!!!!!!!!!!!!!!!!!!
        //blockerID: user1ID
        //blockedID: user2ID
        blockedUsers = readFileToArray(blockedUserList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        //BlockerID Format: "BID_user1ID_user2ID_date"
        //String blockRecord = block.addBlock(); // blockRecord = "BID_user1ID_user2ID_date"
        if (!(blockedUsers.contains(block.getBlockedID()))) {
            blockedUsers.add(block.getBlockedID()); //Add new Block to Array in proper format
            System.out.println("User Blocked Successfully.");
            writeChangesToFile(blockedUserList, blockedUsers); //Write new changes to Text File
        } else {
            System.out.println("Block Operation Failed.");
        }
    }

    // Unblock a user
    public void unblockUser(String blockerID, String blockedID, Date date) {
        //Date Created when User Blocks - Server Creates THIS!!!!!!!!!!!!!!!!!!!!!!!
        //blockerID: user1ID
        //blockedID: user2ID
        //BlockerID Format: "BID_user1ID_user2ID_date"
        blockedUsers = readFileToArray(blockedUserList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        BlockedList block = new BlockedList(blockerID, blockedID, date);
        if (blockedUsers.contains(block.getBlockedID())) { //Make sure Block is in Records
            //blockedUsers.remove(block.getBlockedID());
            blockedUsers.remove(getRowNumber(blockedUsers,block.getBlockedID())); //Remove Row From Array
            System.out.println("User Unblocked Successfully.");
            writeChangesToFile(blockedUserList, blockedUsers); //Write new changes to Text File
        } else {
            System.out.println("Unblock Operation Failed.");
        }
    }

    // Add a friend
    public void addFriend(String user1ID, String user2ID, Date date) {
        //FriendshipList format: FID_username1_username2_status
        friendships = readFileToArray(friendshipList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        FriendsList friendship = new FriendsList(user1ID, user2ID, "Active", "Active", date);
        if (!(friendships.contains(friendship.getFriendshipID()))) {
            friendships.add(friendship.getFriendshipID());
            System.out.println("Friend added successfully.");
            writeChangesToFile(friendshipList, friendships); //Write new changes to Text File
        } else {
            System.out.println("Add friend operation failed.");
        }
    }

    // Remove a friend
    public void removeFriend(String user1ID, String user2ID, Date date) {
        //FriendshipList format: FID_username1_username2_status
        friendships = readFileToArray(friendshipList); //Format of blockedUsers: "BID_user1ID_user2ID_date"
        FriendsList friendship = new FriendsList(user1ID, user2ID, "Active", "Active", date);
        if (friendships.contains(friendship.getFriendshipID())) {
            //friendships.remove(friendship.getfriendshipID());
            friendships.remove(getRowNumber(friendships, friendship.getFriendshipID())); //Remove Row From Array
            System.out.println("Friend removed successfully.");
            writeChangesToFile(friendshipList, friendships); //Write new changes to Text File
        } else {
            System.out.println("Remove friend operation failed.");
        }
    }

    //ADD FRIENDS COMPILATION LIST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    //ADD BLOCKED COMPILATION LIST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // Main method for demonstration purposes

} //EndClass
