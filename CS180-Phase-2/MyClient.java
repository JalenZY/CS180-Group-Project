import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ReceiveDataThread extends Thread {
    private final Socket socket;

    public ReceiveDataThread(Socket socket) {
        super();
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server reply: " + serverResponse);
            }
        } catch (Exception e) {
            System.out.println("Error in receiving data from server: " + e.getMessage());
        }
    }
}

public class MyClient {
    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int portNumber = 1234;

        System.out.println("Connecting to server on port " + portNumber);

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            ReceiveDataThread rcvThread = new ReceiveDataThread(socket);
            rcvThread.start();

            System.out.println("Enter your username:");
            String fromUserName = stdIn.readLine();
            out.println("USERNAME###" + fromUserName);

            String actionType;
            do {
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
                        addUser(out, stdIn);
                        break;
                    case "2":
                        removeUser(out, fromUserName);
                        break;
                    case "3":
                        updateUserProfile(out, fromUserName, stdIn);
                        break;
                    case "4":
                        blockUnblockUser(out, fromUserName, stdIn, true);
                        break;
                    case "5":
                        blockUnblockUser(out, fromUserName, stdIn, false);
                        break;
                    case "6":
                        manageFriendship(out, fromUserName, stdIn, "ADDFRIEND");
                        break;
                    case "7":
                        manageFriendship(out, fromUserName, stdIn, "ACCEPTFRIEND");
                        break;
                    case "8":
                        manageFriendship(out, fromUserName, stdIn, "DENYFRIEND");
                        break;
                    case "9":
                        manageFriendship(out, fromUserName, stdIn, "REMOVEFRIEND");
                        break;
                    case "0":
                        System.out.println("Exiting program.");
                        break;
                    default:
                        System.out.println("Invalid action. Please enter a valid number.");
                        break;
                }
            } while (!actionType.equals("0"));
            
            rcvThread.interrupt();

        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    private static void addUser(PrintWriter out, BufferedReader stdIn) throws IOException {
        System.out.println("Enter details for new user:");
        System.out.print("Username: ");
        String username = stdIn.readLine();
        System.out.print("First Name: ");
        String firstName = stdIn.readLine();
        System.out.print("Last Name: ");
        String lastName = stdIn.readLine();
        System.out.print("Password: ");
        String password = stdIn.readLine();
        System.out.print("Birthday (YYYY-MM-DD): ");
        String birthday = stdIn.readLine();
        System.out.print("Gender: ");
        String gender = stdIn.readLine();
        System.out.print("Hobby 1: ");
        String hobby1 = stdIn.readLine();
        System.out.print("Hobby 2: ");
        String hobby2 = stdIn.readLine();
        System.out.print("Hobby 3: ");
        String hobby3 = stdIn.readLine();
        System.out.print("Hobby 4: ");
        String hobby4 = stdIn.readLine();
        System.out.print("Home Location: ");
        String homeLocation = stdIn.readLine();
        System.out.print("User's Home Region: ");
        String usersHomeRegion = stdIn.readLine();
        System.out.print("College Name: ");
        String collegeName = stdIn.readLine();

        out.println("ADDUSER###" + username + "###" + firstName + "###" + lastName + "###" + password + "###" +
                    birthday + "###" + gender + "###" + hobby1 + "###" + hobby2 + "###" + hobby3 + "###" + hobby4 +
                    "###" + homeLocation + "###" + usersHomeRegion + "###" + collegeName);
    }

    private static void removeUser(PrintWriter out, String username) {
        System.out.println("Are you sure you want to remove your account? (Type 'yes' to confirm)");
        String confirmation = stdIn.readLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            out.println("REMOVEUSER###" + username);
        }
    }

    private static void updateUserProfile(PrintWriter out, String username, BufferedReader stdIn) throws IOException {
        System.out.println("Enter the field you want to update:");
        String fieldToUpdate = stdIn.readLine();
        System.out.println("Enter the new value for " + fieldToUpdate + ":");
        String newValue = stdIn.readLine();
        out.println("UPDATEPROFILE###" + username + "###" + fieldToUpdate + "###" + newValue);
    }

    private static void blockUnblockUser(PrintWriter out, String username, BufferedReader stdIn, boolean block) throws IOException {
        String action = block ? "BLOCKUSER" : "UNBLOCKUSER";
        System.out.println("Enter the username of the user you want to " + (block ? "block:" : "unblock:"));
        String targetUsername = stdIn.readLine();
        out.println(action + "###" + username + "###" + targetUsername);
    }

    private static void manageFriendship(PrintWriter out, String username, BufferedReader stdIn, String action) throws IOException {
        System.out.println("Enter the username of the user:");
        String targetUsername = stdIn.readLine();
        out.println(action + "###" + username + "###" + targetUsername);
    }
}
