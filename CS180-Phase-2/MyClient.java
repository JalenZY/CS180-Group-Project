import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//class ReceiveDataThread extends Thread {
//    public final Socket socket;
//    public String serverResponse;
//
//    public ReceiveDataThread(Socket socket) {
//        super();
//        this.socket = socket;
//    }
//
//    public void run() {
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            //String serverResponse;
//            while ((serverResponse = in.readLine()) != null) {
//                System.out.println("Server reply: " + serverResponse);
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error in receiving data from server: " + e.getMessage());
//        }
//    }
//}

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

//            ReceiveDataThread rcvThread = new ReceiveDataThread(socket);
//            rcvThread.start();

            boolean repeat = true;
            boolean mainMenu = true; //Boolean to initiate the main menu list
            String fromUserName = "";
            while (repeat) {
                //Provide Option to create new account
                System.out.println("Enter your username or Enter 1 to create a new Account!");
                fromUserName = stdIn.readLine();
                if (fromUserName.equals("1")) {
                    addUser(out, stdIn, in);
                    mainMenu = false; //Don't display the main menu
                    String serverResponse = in.readLine();
                    if (serverResponse.equals("User successfully added.")) {
                        System.out.println("Account Successfully Created, please Login!!");
                    } else {
                        System.out.println("Error, Please Try Again!");
                    }
                } else {
                    out.println("USERNAME###" + fromUserName);
                    String serverResponse = in.readLine();
                    if (serverResponse.equals("Welcome User!")) {
                        System.out.println("Welcome User!");
                        repeat = false;
                    } else if (serverResponse.equals("Please Try Again")) {
                        System.out.println("Please Try Again");
                    }
                }
            }

            String actionType;
            boolean stop = false;
            while ((!stop) || (mainMenu)) {
                System.out.println("\nChoose an action:");
                System.out.println("1. Add User");
                System.out.println("2. Remove User");
                System.out.println("3. Update User Profile");
                System.out.println("4. Block User");
                System.out.println("5. Unblock User");
                System.out.println("6. Add Friend");
                System.out.println("7. Accept Friendship");
                System.out.println("8. Decline Friendship");
                System.out.println("9. Remove Friend");
                System.out.println("0. Exit");
                System.out.println("Enter the number of your choice:");

                actionType = stdIn.readLine();
                switch (actionType) {
                    case "1":
                        addUser(out, stdIn, in);
                        break;
//                    case "2":
//                        removeUser(out, fromUserName);
//                        break;
                    case "3":
                        updateUserProfile(out, fromUserName, stdIn, in);
                        break;
                    case "4":
                        blockUnblockUser(out, fromUserName, stdIn, in, true);
                        break;
                    case "5":
                        blockUnblockUser(out, fromUserName, stdIn, in, false);
                        break;
                    case "6":
                        manageFriendship(out, fromUserName, stdIn, in, "ADDFRIEND");
                        break;
                    case "7":
                        manageFriendship(out, fromUserName, stdIn, in,"ACCEPTFRIEND");
                        break;
                    case "8":
                        manageFriendship(out, fromUserName, stdIn, in,"DENYFRIEND");
                        break;
                    case "9":
                        manageFriendship(out, fromUserName, stdIn, in,"REMOVEFRIEND");
                        break;
                    case "0":
                        System.out.println("Exiting program.");
                        stop = true;
                        break;
                    default:
                        System.out.println("Invalid action. Please enter a valid number.");
                        break;
                }
                try { //Wait for server verification to pass through
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            rcvThread.interrupt();

        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    private static void addUser(PrintWriter out, BufferedReader stdIn, BufferedReader in) throws IOException {
        System.out.println("Enter details for new user:");
        System.out.println("Username: ");
        String username = stdIn.readLine();
        boolean unique = false;
        while (!unique) { //Check for Unique Username
            out.println("USERNAME###" + username);
            String serverResponse = in.readLine();
            if (serverResponse.equals("Welcome User!")) {
                System.out.println("UserName already Exists, Please enter a New UserName!");
                username = stdIn.readLine();
            } else {
                unique = true;
            }
        }
        System.out.println("First Name: ");
        String firstName = stdIn.readLine();
        while (!isNotInteger(firstName)) { //Check that given input is not an integer
            System.out.println("Please Enter a Non-Numeric String");
            System.out.println("First Name: ");
            firstName = stdIn.readLine();
        }
        System.out.println("Last Name: ");
        String lastName = stdIn.readLine();
        while (!isNotInteger(lastName)) { //Check that given input is not an integer
            System.out.println("Please Enter a Non-Numeric String");
            System.out.println("Last Name: ");
            lastName = stdIn.readLine();
        }
        System.out.println("Password: ");
        String password = stdIn.readLine();
        System.out.println("Birthday (YYYY-MM-DD): ");
        String birthday = stdIn.readLine();
        System.out.println("Gender: ");
        String gender = stdIn.readLine();
        System.out.println("Hobby 1: ");
        String hobby1 = stdIn.readLine();
        System.out.println("Hobby 2: ");
        String hobby2 = stdIn.readLine();
        System.out.println("Hobby 3: ");
        String hobby3 = stdIn.readLine();
        System.out.println("Hobby 4: ");
        String hobby4 = stdIn.readLine();
        System.out.println("Home Location: ");
        String homeLocation = stdIn.readLine();
        while (!(isNotInteger(homeLocation))) { //Check that given input is not an integer
            System.out.println("Please Enter a Non-Numeric String");
            System.out.println("Home Location: ");
            homeLocation = stdIn.readLine();
        }
        System.out.println("User's Home Region: ");
        String usersHomeRegion = stdIn.readLine();
        while (!(isNotInteger(usersHomeRegion))) { //Check that given input is not an integer
            System.out.println("Please Enter a Non-Numeric String");
            System.out.println("User's Home Region: ");
            usersHomeRegion = stdIn.readLine();
        }
        System.out.println("College Name: ");
        String collegeName = stdIn.readLine();
        while (!(isNotInteger(collegeName))) { //Check that given input is not an integer
            System.out.println("Please Enter a Non-Numeric String");
            System.out.println("College Name: ");
            collegeName = stdIn.readLine();
        }

        out.println("ADDUSER###" + username + "###" + firstName + "###" + lastName + "###" + password + "###" +
                birthday + "###" + gender + "###" + hobby1 + "###" + hobby2 + "###" + hobby3 + "###" + hobby4 +
                "###" + homeLocation + "###" + usersHomeRegion + "###" + collegeName);
    }

    public static boolean isNotInteger(String input) {
        try {
            Integer.parseInt(input);
            return false; //Input is an integer
        } catch (NumberFormatException e) {
            return true; //Input is not an integer
        }
    }

//    private static void removeUser(PrintWriter out, String username) {
//        System.out.println("Are you sure you want to remove your account? (Type 'yes' to confirm)");
//        String confirmation = stdIn.readLine();
//        if (confirmation.equalsIgnoreCase("yes")) {
//            out.println("REMOVEUSER###" + username);
//        }
//    }

    private static void updateUserProfile(PrintWriter out, String username, BufferedReader stdIn, BufferedReader in) throws IOException {
        System.out.println("Enter the field you want to update:");
        String fieldToUpdate = stdIn.readLine();
        if (fieldToUpdate.equals("0")) {
            return;
        }
        System.out.println("Enter the new value for " + fieldToUpdate + ":");
        String newValue = stdIn.readLine();
        out.println("UPDATEPROFILE###" + username + "###" + fieldToUpdate + "###" + newValue);
        String serverResponse = in.readLine();
        if (serverResponse.equals("User profile successfully updated.")) {
            System.out.println("Account Successfully Updated");
        } else {
            if (serverResponse.equals("Error updating user profile.")) {
                System.out.println("Error - Please Try Again");
            }
        }
    }

    private static void blockUnblockUser(PrintWriter out, String username, BufferedReader stdIn, BufferedReader in, boolean block) throws IOException {
        String action = block ? "BLOCKUSER" : "UNBLOCKUSER"; //if block == true, block user -- if block == false, unblock user
        System.out.println("Enter the username of the user you want to " + (block ? "block:" : "unblock:"));
        String targetUsername = stdIn.readLine();
        if (targetUsername.equals(username) || targetUsername.equals("0")) {
            if (targetUsername.equals(username)) {
                System.out.println("Error-User Can not Block Themselves");
            } else {
                return;
            }
        } else {
            out.println(action + "###" + username + "###" + targetUsername);
            String serverResponse = in.readLine();
            if (serverResponse.equals("User successfully blocked.")) {
                System.out.println("User successfully blocked.");
            } else if (serverResponse.equals("User successfully unblocked.")) {
                System.out.println("User successfully unblocked.");
            } else if (serverResponse.equals("Error blocking user.")) {
                System.out.println("Error blocking user, Please Try Again");
            } else if (serverResponse.equals("Error unblocking user.")) {
                System.out.println("Error unblocking user, Please Try Again");
            } else if (serverResponse.equals("Error: Insufficient data for blocking/unblocking.")) {
                System.out.println("Error: Insufficient data for blocking/unblocking, Please Try Again");
            }
        }
    }

    private static void manageFriendship(PrintWriter out, String username, BufferedReader stdIn, BufferedReader in, String action) throws IOException {
        System.out.println("Enter the username of the user:");
        String targetUsername = stdIn.readLine();
        out.println(action + "###" + username + "###" + targetUsername);
    }
}
