import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
            e.printStackTrace();
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

            String userInput;
            System.out.println("Type your username:");
            userInput = stdIn.readLine();
            String fromUserName = userInput;
            userInput = "USERNAME###" + userInput;
            out.println(userInput);

            while (!userInput.equalsIgnoreCase("bye")) {
                System.out.println("Choose an action:\n1. Send message\n2. Add friend\n3. Block user\n4. Unblock user\n5. Update profile\n6. Remove user\nType the number of the action:");
                String actionType = stdIn.readLine();
                String message;
                switch (actionType) {
                    case "1":
                        System.out.println("Send message to:");
                        String toUserName = stdIn.readLine();
                        System.out.println("Type a message (type 'bye' to quit):");
                        message = stdIn.readLine();
                        out.println("SENDMESSAGE" + "###" + fromUserName + "###" + toUserName + "###" + message);
                        userInput = message;
                        break;
                    case "2":
                        System.out.println("Enter the username of the friend you want to add:");
                        String friendUserName = stdIn.readLine();
                        out.println("ADDFRIEND" + "###" + fromUserName + "###" + friendUserName);
                        userInput = "ADDFRIEND";
                        break;
                    case "3":
                        System.out.println("Enter the username of the user you want to block:");
                        String blockUserName = stdIn.readLine();
                        out.println("BLOCKUSER" + "###" + fromUserName + "###" + blockUserName);
                        userInput = "BLOCKUSER";
                        break;
                    case "4":
                        System.out.println("Enter the username of the user you want to unblock:");
                        String unblockUserName = stdIn.readLine();
                        out.println("UNBLOCKUSER" + "###" + fromUserName + "###" + unblockUserName);
                        userInput = "UNBLOCKUSER";
                        break;
                    case "5":
                        System.out.println("Enter the field you want to update (e.g., userName, userFirstName, userLastName):");
                        String fieldToUpdate = stdIn.readLine();
                        System.out.println("Enter the new value:");
                        String newValue = stdIn.readLine();
                        out.println("UPDATEPROFILE" + "###" + fromUserName + "###" + fieldToUpdate + "###" + newValue);
                        userInput = "UPDATEPROFILE";
                        break;
                    case "6":
                        System.out.println("Are you sure you want to remove your account? (Type 'yes' to confirm)");
                        String confirmation = stdIn.readLine();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            out.println("REMOVEUSER" + "###" + fromUserName);
                            userInput = "REMOVEUSER";
                        } else {
                            userInput = "";
                        }
                        break;
                    default:
                        System.out.println("Invalid action. Please choose a valid action.");
                        userInput = "";
                        break;
                }
            }
            rcvThread.interrupt();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
