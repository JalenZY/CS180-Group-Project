import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyServer {
    private static final int PORT = 1234;
    private static ArrayList<String> loginUserNameList = new ArrayList<>();
    private static ArrayList<Socket> loginUserSocketList = new ArrayList<>();
    private static PeopleDatabase peopleDb = new PeopleDatabase("userProfileList.txt", "friendshipList.txt", "blockedUserList.txt");

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
            case "ACCEPTFRIEND":
            case "DENYFRIEND":
            case "REMOVEFRIEND":
                handleManageFriendship(parts, out);
                break;
            default:
                out.println("Invalid command");
                break;
        }
    }

    private static void handleAddUser(String[] parts, PrintWriter out) {
        if (parts.length < 14) {
            out.println("Error: Insufficient data provided.");
            return;
        }
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

        UserProfile newUser = new UserProfile(username, firstName, lastName, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName);
        boolean result = peopleDb.addUser(newUser);

        if (result) {
            out.println("User successfully added.");
        } else {
            out.println("Error adding user.");
        }
    }

    private static void handleRemoveUser(String[] parts, PrintWriter out) {
        if (parts.length < 2) {
            out.println("Error: Username not provided.");
            return;
        }
        String username = parts[1];
        boolean result = peopleDb.removeUser(username);

        if (result) {
            out.println("User successfully removed.");
        } else {
            out.println("Error removing user.");
        }
    }

    private static void handleUpdateUserProfile(String[] parts, PrintWriter out) {
        if (parts.length < 5) {
            out.println("Error: Insufficient data for update.");
            return;
        }
        String username = parts[1];
        String fieldToUpdate = parts[2];
        String newValue = parts[3];

        boolean result = peopleDb.updateUserProfile(username, fieldToUpdate, newValue);

        if (result) {
            out.println("User profile successfully updated.");
        } else {
            out.println("Error updating user profile.");
        }
    }

    private static void handleBlockUnblockUser(String[] parts, PrintWriter out, boolean block) {
        if (parts.length < 3) {
            out.println("Error: Insufficient data for blocking/unblocking.");
            return;
        }
        String blockerUsername = parts[1];
        String blockedUsername = parts[2];

        boolean result;
        if (block) {
            result = peopleDb.blockUser(blockerUsername, blockedUsername, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            result = peopleDb.unblockUser(blockerUsername, blockedUsername);
        }

        if (result) {
            out.println(block ? "User successfully blocked." : "User successfully unblocked.");
        } else {
            out.println(block ? "Error blocking user." : "Error unblocking user.");
        }
    }

    private static void handleManageFriendship(String[] parts, PrintWriter out) {
        // Implementation for managing friendships
    }
}
