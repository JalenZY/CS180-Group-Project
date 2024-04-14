import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {
    private static ArrayList<String> loginUserNameList = new ArrayList<>();
    private static ArrayList<Socket> loginUserSocketList = new ArrayList<>();


    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is waiting for client connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleMessageUser(String userName, Socket socket)
    {
        System.out.println("received command for new user login : "+userName);
        if(loginUserNameList.contains(userName)==false)
        {
            loginUserNameList.add(userName);
            loginUserSocketList.add(socket);
        }
        System.out.print("Current login all user list: ");
        for(int i = 0; i< loginUserNameList.size(); i++)
        {
            System.out.print(loginUserNameList.get(i)+" ");
        }
        System.out.println("");
    }

    public static void handleMessageSendMessage(String fromUserName, String toUserName, String message, Socket socket)
    {
        try {
            System.out.println("received command for send message : from " + fromUserName + " to " + toUserName);
            if (loginUserNameList.contains(fromUserName) == true &&
                    loginUserNameList.contains(toUserName) == true) {
                // find toUserName socket
                for (int i = 0; i < loginUserNameList.size(); i++) {
                    String userName = loginUserNameList.get(i);
                    Socket socketToUser = loginUserSocketList.get(i);
                    if (userName.equals(toUserName)) {
                        PrintWriter out = new PrintWriter(socketToUser.getOutputStream(), true);
                        out.println(message);
                        break;
                    }
                }
            } else {
                System.out.println("from or to user name is invalid");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void handleMessage(String message, Socket socket)
    {
        String[] strs = message.split("###");
        System.out.print("handle message: ");
        for(int i=0;i<strs.length;i++)
        {
            System.out.print(strs[i] + " ");
        }
        System.out.println("");
        if(strs.length>=2)
        {
            String cmd = strs[0];
            if(cmd.equals("USERNAME"))
            {
                String userName = strs[1];
                handleMessageUser(userName,socket);
            }
            else if(cmd.equals("SENDMESSAGE"))
            {
                String fromUserName = strs[1];
                String toUserName = strs[2];
                String msg = strs[3];
                handleMessageSendMessage(fromUserName, toUserName, msg, socket);
            }
        }
    }


    private static void handleClient(Socket socket) {
        String clientIp = socket.getInetAddress().getHostAddress();
        System.out.println("Client connected, IP = " + clientIp);

        try (socket;
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

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
}
