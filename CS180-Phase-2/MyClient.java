import java.io.*;
import java.net.Socket;

public class MyClient {

    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int portNumber = 4242;
//
        System.out.println("Connecting to server on port " + portNumber);

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //writes to Server
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) { //Reads from User
        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
    
    public static boolean manageLogIn(BufferedReader in, PrintWriter out, String fromUserName, String password) throws IOException {
        out.println("USERLOGIN###" + fromUserName + "###" + password);
        String serverResponse = in.readLine();
        if (serverResponse.equals("Welcome User!")) {
            System.out.println("Welcome User!");
            return true;
            //repeat = false;
        } else if (serverResponse.equals("Please Try Again")) {
            System.out.println("Please Try Again");
            return false;
        }
        return false;
    }

    public static String searchForUser(PrintWriter out, BufferedReader in, String textComponent) throws IOException {
        out.println("SEARCH###" + textComponent);
        String serverResponse = in.readLine();
        return (serverResponse);
    }

    public static boolean checkUser(PrintWriter out, BufferedReader in, String userName) throws IOException {
        out.println("USERNAME###" + userName);
        String serverResponse = in.readLine();
        if (serverResponse.equals("Welcome User!")) {
            return true;
        }
        return false;
    }

    public static void addUser(PrintWriter out, BufferedReader stdIn, BufferedReader in, String userName,
                               String firstName, String lastName, String password, String birthday, String homeLocation,
                               String usersHomeRegion, String collegeName, String hobby1, String hobby2, String hobby3,
                               String hobby4, String gender) throws IOException {

        out.println("ADDUSER###" + userName + "###" + firstName + "###" + lastName + "###" + password + "###" +
                birthday + "###" + gender + "###" + hobby1 + "###" + hobby2 + "###" + hobby3 + "###" + hobby4 +
                "###" + homeLocation + "###" + usersHomeRegion + "###" + collegeName);
    }

    public static String viewUserProfile(PrintWriter out, BufferedReader in, String userName) throws IOException {
        out.println("PRINTUSERPROFILE###" + userName);
        String serverResponse = in.readLine(); //Collect UserprofileData
        return serverResponse;

    }

    public static boolean isNotInteger(String input) {
        try {
            Integer.parseInt(input);
            return false; //Input is an integer
        } catch (NumberFormatException e) {
            return true; //Input is not an integer
        }
    }

    private static void removeUser(PrintWriter out, BufferedReader stdIn, String username) throws IOException {
        System.out.println("Are you sure you want to remove your account? (Type 'yes' to confirm)");
        String confirmation = stdIn.readLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            out.println("REMOVEUSER###" + username);
        }
    }

    public static Boolean updateUserProfile(PrintWriter out, String username, String fieldToUpdate, String newValue, BufferedReader in) throws IOException {
        out.println("UPDATEPROFILE###" + username + "###" + fieldToUpdate + "###" + newValue);
        String serverResponse = in.readLine();
        if (serverResponse.equals("User profile successfully updated.")) {
            return (true); //("Account Successfully Updated");
        }
        return false;
    }

    public static String checkUserAbleUnBlock(PrintWriter out, BufferedReader in, String fromUser, String userName) throws IOException {
        out.println("CHECKBLOCK###" + fromUser + "###" + userName);
        String blocked = in.readLine(); //Returns true if users blocked
        if (blocked.equals("true")) {
            out.println("CHECKSPECIFICBLOCK###" + fromUser + "###" + userName);
            String canUnblock = in.readLine();
            if (canUnblock.equals("true")) {
                return ("UnblockButton"); //Display UserUnblock Button
            }
            return ("NoButton");
        }
        return ("BlockButton");
    }

    public static String checkUserFriendActions(PrintWriter out, BufferedReader in, String fromUser, String userName) throws IOException {
        String existingFriendship = "false";
        String initiated = "false";
        out.println("CHECKFRIEND###" + fromUser + "###" + userName);
        String friendship = in.readLine(); //Returns true if users have Friendship
        if (friendship.equals("true")) {
            out.println("CHECKACTIVEFRIEND###" + fromUser + "###" + userName);
            existingFriendship = in.readLine(); //Returns true if users have Active Friendship
            if (existingFriendship.equals("false")) { //Friendship is Pending
                out.println("CHECKSPECIFICFRIEND###" + fromUser + "###" + userName);
                initiated = in.readLine(); //Returns true if users initiated Friendship
                if (initiated.equals("false")) {
                    return ("BothOptions");
                } else {
                    return ("RemoveFriend");
                }
            } else {
                return ("RemoveFriend");
            }
        } else {
            return ("RemoveFriend");
        }
    }

    public static String blockUnblockUser(PrintWriter out, String username, String targetUsername, BufferedReader in, boolean block) throws IOException {
        String action = block ? "BLOCKUSER" : "UNBLOCKUSER"; //if block == true, block user -- if block == false, unblock user
        out.println(action + "###" + username + "###" + targetUsername);
        String serverResponse = in.readLine();
        if (serverResponse.equals("User successfully blocked.")) {
            return ("User successfully blocked.");
        } else if (serverResponse.equals("User successfully unblocked.")) {
            return ("User successfully unblocked.");
        } else if (serverResponse.equals("Error blocking user.")) {
            return ("Error blocking user, Please Try Again");
        } else if (serverResponse.equals("Error unblocking user.")) {
            return ("Error unblocking user, Please Try Again");
        } else if (serverResponse.equals("Error: Insufficient data for blocking/unblocking.")) {
            return ("Error: Insufficient data for blocking/unblocking, Please Try Again");
        }
        return ("System Error - Unable to Complete Action");
    }

    public static String viewFriends(BufferedReader in, PrintWriter out, String userName) throws IOException {
        out.println("FRIENDSPRINT###" + userName);
        String friendsString = "";
        friendsString = in.readLine();
        return (friendsString);
    }

    public static String manageFriendship(PrintWriter out, String username, BufferedReader in, String action, String targetUsername) throws IOException {
        out.println(action + "###" + username + "###" + targetUsername);
        String status = in.readLine();
        return (status);
    }

    public static String managePrintConversation(PrintWriter out, BufferedReader in, String userName, String targetUsername) throws IOException {
        out.println("CONVERSATIONPRINT###" + userName + "###" + targetUsername);
        String[] totalMessages = null;
        String conversationString = in.readLine();
        return (conversationString);
    }

    public static String manageSendMessage(PrintWriter out, BufferedReader in, String username, String targetUsername, String message) throws IOException {
        boolean validUser = false;
        while (!validUser) { //Check that Username entered is valid
            out.println("USERNAME###" + targetUsername);
            String serverResponse = in.readLine();
            if (serverResponse.equals("Please Try Again")) {
                return ("Please Enter the Valid UserName of the User You Want to Message:");
            } else if (serverResponse.equals("Welcome User!"))  {
                validUser = true;
            }
        }
        out.println("SENDMESSAGE###" + username + "###" + targetUsername + "###" + message); //Send message information to Server
        String serverResponse = in.readLine();
        if (serverResponse.equals("Error: Recipient UserName does Not Exist")) {
            return ("Error: Recipient UserName does Not Exist");
        } else if (serverResponse.equals("Error: User Block Exists")) {
            return ("Error, Block Exists Between Users");
        } else if (serverResponse.equals("Error: Friendship Does Not Exist")) {
            return ("Error, User is not a Friend");
        } else if (serverResponse.equals("Error: Conversation Corrupted")) {
            return ("Error, Conversation Might Be Corrupted, please Try again");
        } else if (serverResponse.equals("Error: Insufficient data provided.")) {
            return ("Error: Insufficient data, Please Try Again");
        }
        return ("Message Sent!");
    }
}
