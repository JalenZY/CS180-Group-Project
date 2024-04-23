import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyServer {
    private static final int PORT = 4242;
    private static ArrayList<String> loginUserNameList = new ArrayList<>();
    private static ArrayList<Socket> loginUserSocketList = new ArrayList<>();
    private static PeopleDatabase peopleDb = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");
    private static DirectMessagingDatabase messagingDb = new DirectMessagingDatabase();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is waiting for client connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        System.out.println("Client connected, IP = " + socket.getInetAddress().getHostAddress());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client says: " + inputLine);
                handleMessage(inputLine, out);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void handleMessage(String message, PrintWriter out) {
        String[] parts = message.split("###");
        if (parts.length < 2) return;

        String cmd = parts[0];
        switch (cmd) {
            case "USERNAME":
                handleCheckUser(parts, out);
                break;
            case "USERLOGIN":
                handleUserLogin(parts, out);
                break;
            case "ADDUSER":
                handleAddUser(parts, out);
                break;
            case "REMOVEUSER":
                handleRemoveUser(parts, out);
                break;
            case "UPDATEPROFILE":
                handleUpdateUserProfile(parts, out);
                break;
            case "BLOCKUSER":
                handleBlockUnblockUser(parts, out, true);
                break;
            case "UNBLOCKUSER":
                handleBlockUnblockUser(parts, out, false);
                break;
            case "ADDFRIEND":
                handleManageFriendship(parts, out, 1);
                break;
            case "ACCEPTFRIEND":
                handleManageFriendship(parts, out, 2);
                break;
            case "DENYFRIEND":
                handleManageFriendship(parts, out, 3);
                break;
            case "REMOVEFRIEND":
                handleManageFriendship(parts, out, 4);
                break;
            case "SENDMESSAGE":
                handleSendMessage(parts, out);
                break;
            case "CONVERSATIONPRINT":
                handlePrintConversation(parts, out);
                break;
                case "FRIENDSPRINT":
                    handlePrintFriends(parts, out);
            default:
                out.println("Invalid command");
                break;
        }
    }

    private static void handlePrintFriends(String[] parts, PrintWriter out) {
        String username= parts[1];
        String user1ID = peopleDb.userNameToUserID(username);
        ArrayList<String> friendsList = peopleDb.printFriends(user1ID);

        StringBuilder friendsString = new StringBuilder(); //Convert ArrayList to String to Print
        for (String item : friendsList) {
            friendsString.append(item).append("\n");
        }
        out.println(friendsString);
    }


    private static void handlePrintConversation(String[] parts, PrintWriter out) {
        String username= parts[1];
        String targetUserName = parts[2];
        String user1ID = peopleDb.userNameToUserID(username);
        String user2ID = peopleDb.userNameToUserID(targetUserName);

        String conversationID;
        if (messagingDb.findConvID(user1ID, user2ID).equals("Error")) {
            out.write("Error: Conversation Corrupted");
            out.println();
            out.flush(); //Ensure data is sent to the client.
            return;
        } else if ((messagingDb.findConvID(user1ID, user2ID)).equals("No ID")) {
            //Check if a conversation does not exist between users
            conversationID = messagingDb.generateUniqueConversationID(user1ID, user2ID);
        } else {
            conversationID = messagingDb.findConvID(user1ID, user2ID);
        }
        ArrayList<String> conversation = messagingDb.printMessages(conversationID, user1ID);
        StringBuilder conversationString = new StringBuilder(); //Convert ArrayList to String to Print
        for (String item : conversation) {
            conversationString.append(item).append("\n");
        }
        out.println(conversationString);

    }

    private static void handleSendMessage(String[] parts, PrintWriter out) {
        if (parts.length < 3) {
            out.println("Error: Insufficient data provided.");
            return;
        }
        String username = parts[1];
        String targetUserName = parts[2];
        String message = parts[3];
        String user1ID = peopleDb.userNameToUserID(username);
        String user2ID = peopleDb.userNameToUserID(targetUserName);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        String conversationID = "";


        if (!peopleDb.checkUser(targetUserName)) { //Check that recipient exists
            out.write("Error: Recipient UserName does Not Exist");
            out.println();
            out.flush(); //Ensure data is sent to the client.
            return;
        } else if (peopleDb.checkBlock(user1ID, user2ID)) { //Check if Block exists
            out.write("Error: User Block Exists");
            out.println();
            out.flush(); //Ensure data is sent to the client.
            return;
        } else if (!peopleDb.checkFriend(user1ID, user2ID)) { //Check if users are friends
            out.write("Error: Friendship Does Not Exist");
            out.println();
            out.flush(); //Ensure data is sent to the client.
            return;
        } else if (messagingDb.findConvID(user1ID, user2ID).equals("Error")) {
            out.write("Error: Conversation Corrupted");
            out.println();
            out.flush(); //Ensure data is sent to the client.
            return;
        } else if ((messagingDb.findConvID(user1ID, user2ID)).equals("No ID")) {
            //Check if a conversation exists between users
            conversationID = messagingDb.generateUniqueConversationID(user1ID, user2ID);
        } else {
            conversationID = messagingDb.findConvID(user1ID, user2ID);
        }
        boolean result = messagingDb.sendMessage(conversationID, user1ID, user2ID, formattedDate, message);

        if (result) {
            out.write("Message Sent!");
            out.println();
            out.flush(); //Ensure data is sent to the client.
        } else {
            out.write("Error: Message Unable to Be Sent");
            out.println();
            out.flush(); //Ensure data is sent to the client.
        }
    }

    private static void handleCheckUser(String[] parts, PrintWriter out) {
        if (parts.length < 1) {
            out.println("Error: Insufficient data provided.");
            return;
        }
        String username = parts[1];
        boolean exist = peopleDb.checkUser(username); //Check that username and password are valid and applicable to eachother
        if (exist) {
            out.write("Welcome User!");
            out.println();
            out.flush(); //Ensure data is sent to the client.
        }
        else {
            out.write("Please Try Again");
            out.println();
            out.flush(); //Ensure data is sent to the client.
        }
    }

    //Checks that User and Password are related
    private static void handleUserLogin(String[] parts, PrintWriter out) {
        if (parts.length < 1) {
            out.println("Error: Insufficient data provided.");
            return;
        }
        String username = parts[1];
        String password = parts[2];
        boolean exist = peopleDb.userLogin(username, password); //Check that username and password are valid and applicable to eachother
        if (exist) {
            out.write("Welcome User!");
            out.println();
            out.flush(); //Ensure data is sent to the client.
        }
        else {
            out.write("Please Try Again");
            out.println();
            out.flush(); //Ensure data is sent to the client.
        }
    }

    private static void handleAddUser(String[] parts, PrintWriter out) {
        if (parts.length < 14) {
            out.println("Error: Insufficient data provided.");
            return;
        }
        String userID = "-";
        String username = parts[1];
        String firstName = parts[2];
        String lastName = parts[3];
        String password = parts[4];
        String birthday = parts[5];
        String gender = parts[6];
        String hobby1 = parts[7];
        String hobby2 = parts[8];
        String hobby3 = parts[9];
        String hobby4 = parts[10];
        String homeLocation = parts[11];
        String usersRegion = parts[12];
        String collegeName = parts[13];

        UserProfile newUser = new UserProfile(userID, username, firstName, lastName, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName);
        boolean result = peopleDb.addUser(newUser);

        if (result) {
            String serverResponse = "User successfully added.";
            out.write(serverResponse);
            out.println();
            out.flush(); //Ensure data is sent to the client.
            //out.println("User successfully added.");
        } else {
            String serverResponse = "Error adding user.";
            out.write(serverResponse);
            out.println();
            out.flush(); //Ensure data is sent to the client.
            //out.println("Error adding user.");
        }
    }

    private static void handleRemoveUser(String[] parts, PrintWriter out) {
        if (parts.length < 2) {
            out.println("Error: Username not provided.");
            return;
        }
        String userID = peopleDb.userNameToUserID(parts[1]); //Methods require UserID not Username
        boolean result = peopleDb.removeUser(userID);

        if (result) {
            out.println("User successfully removed.");
        } else {
            out.println("Error removing user.");
        }
    }

    private static void handleUpdateUserProfile(String[] parts, PrintWriter out) {
        if (parts.length < 2) {
            out.println("Error: Insufficient data for update.");
            return;
        }
        String userID = peopleDb.userNameToUserID(parts[1]);
        String fieldToUpdate = parts[2];
        String newValue = parts[3];

        boolean result = peopleDb.updateUserProfile(userID, fieldToUpdate, newValue);

        if (result) {
            out.println("User profile successfully updated.");
        } else {
            out.println("Error updating user profile.");
        }
    }

    private static void handleBlockUnblockUser(String[] parts, PrintWriter out, boolean block) {
        if (parts.length < 1) {
            out.println("Error: Insufficient data for blocking/unblocking.");
            return;
        }
        String blockerUserID = peopleDb.userNameToUserID(parts[1]);
        String blockedUserID = peopleDb.userNameToUserID(parts[2]);

        boolean result; //Need to convert username to UserID
        if (block) {
            result = peopleDb.blockUser(blockerUserID, blockedUserID, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            result = peopleDb.unblockUser(blockerUserID, blockedUserID);
        }

        if (result) {
            out.println(block ? "User successfully blocked." : "User successfully unblocked.");
        } else {
            out.println(block ? "Error blocking user." : "Error unblocking user.");
        }
    }

    private static void handleManageFriendship(String[] parts, PrintWriter out, int action) {
        if (parts.length < 3) {
            out.println("Error: Insufficient data for blocking/unblocking.");
            return;
        }
        String userID1 = peopleDb.userNameToUserID(parts[1]);
        String userID2 = peopleDb.userNameToUserID(parts[2]);

        boolean result = false; //Need to convert username to UserID
        if (action == 1) { //AddFriend
            if (!peopleDb.checkFriend(userID1, userID2)) { //Check Friendship doesn't already exist
                if (!peopleDb.checkBlock(userID1, userID2)) { //Check that Block does not exist
                    result = peopleDb.addFriend(userID1, userID2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
            }
        } if (action == 2) { //Accept Friend
            if (peopleDb.checkFriend(userID1, userID2)) { //Check that Friendship is registered in database -- will be
                //saved as "Pending" -- Might be Redundant
                result = peopleDb.acceptFriend(userID1, userID2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        } if (action == 3) { //Decline Friend
            if (peopleDb.checkFriend(userID1, userID2)) { //Check that Friendship has been registered and is pending
                result = peopleDb.deniedFriend(userID1, userID2);
            }
        } if (action == 4) { //Remove Friend
            if (peopleDb.checkFriend(userID1, userID2)) { //Check that Friendship exists
                result = peopleDb.deniedFriend(userID1, userID2);
            }
        }

        if (result) {
            if (action == 1) {
                out.println("Friend Added!");
            } if (action == 2) {
                out.println("Friendship Accepted!");
            } if (action == 3) {
                out.println("Friendship Declined");
            } if (action == 4) {
                out.println("Friend Removed");
            }
        } else {
            if (action == 1) {
                out.println("Error Adding Friend");
            } if (action == 2) {
                out.println("Error Accepting Friendship");
            } if (action == 3) {
                out.println("Error Declining Friendship");
            } if (action == 4) {
                out.println("Error Removing Friend");
            }
        }
    }


}
