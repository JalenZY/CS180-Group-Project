import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class MyServer {
    private static final int PORT = 1234;
    private static ArrayList<String> loginUserNameList = new ArrayList<>();
    private static ArrayList<Socket> loginUserSocketList = new ArrayList<>();

    // Assuming these are initialized somewhere in your main method or constructor
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
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        String clientIp = socket.getInetAddress().getHostAddress();
        System.out.println("Client connected, IP = " + clientIp);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client (" + clientIp + "): " + inputLine);
                handleMessage(inputLine, socket);
                out.println("Echo: " + inputLine); // Echoing back the received message
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static void handleMessage(String message, Socket socket) {
        String[] parts = message.split("###");
        System.out.print("Handle message: ");
        for (String part : parts) {
            System.out.print(part + " ");
        }
        System.out.println("");

        if (parts.length >= 2) {
            String cmd = parts[0];
            switch (cmd) {
                case "USERNAME":
                    String userName = parts[1];
                    handleMessageUser(userName, socket);
                    break;
                case "SENDMESSAGE":
                    if (parts.length < 4) return;
                    String fromUserName = parts[1];
                    String toUserName = parts[2];
                    String msg = parts[3];
                    handleMessageSendMessage(fromUserName, toUserName, msg, socket);
                    break;
            }
        }
    }

    public static void handleMessageUser(String userName, Socket socket) {
        System.out.println("Received command for new user login: " + userName);
        if (!loginUserNameList.contains(userName)) {
            if (!peopleDb.userExists(userName)) {
                UserProfile newUser = new UserProfile(userName, "defaultPassword");
                peopleDb.addUser(newUser);
            }
            loginUserNameList.add(userName);
            loginUserSocketList.add(socket);
        }
        System.out.print("Current login all user list: ");
        loginUserNameList.forEach(user -> System.out.print(user + " "));
        System.out.println();
    }

    public static void handleMessageSendMessage(String fromUserName, String toUserName, String message, Socket socket) {
        try {
            System.out.println("Received command for sending message: from " + fromUserName + " to " + toUserName);
            if (loginUserNameList.contains(fromUserName) && loginUserNameList.contains(toUserName)) {
                String conversationID = fromUserName + "_" + toUserName;
                messagingDb.sendMessage(conversationID, fromUserName, toUserName, new Date().toString(), message);

                int index = loginUserNameList.indexOf(toUserName);
                if (index != -1) {
                    PrintWriter out = new PrintWriter(loginUserSocketList.get(index).getOutputStream(), true);
                    out.println("New message from " + fromUserName + ": " + message);
                }
            } else {
                System.out.println("From or to username is invalid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
