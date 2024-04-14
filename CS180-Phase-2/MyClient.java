import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


class ReceiveDataThread extends Thread {
    private Socket socket;
    public ReceiveDataThread(Socket socket) {
        super();
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            while(true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Server reply: " + in.readLine()); // Reading the echo from the server
            }
        }
        catch (Exception e)
        {
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
            System.out.println("Type your username :");
            // optional: register by send username and password
            userInput = stdIn.readLine();
            String fromUserName = userInput;
            userInput = "USERNAME###"+userInput;
            out.println(userInput); // Sending to the server
            while (!userInput.equalsIgnoreCase("bye")) {
                //System.out.println("Server echo: " + in.readLine()); // Reading the echo from the server
                System.out.println("Send message to : ");
                String toUserName = stdIn.readLine();
                System.out.println("Type a message (type 'bye' to quit): ");
                String message = stdIn.readLine();
                out.println("SENDMESSAGE"+"###"+fromUserName+"###"+toUserName+"###"+message);
                userInput = message;
            }
            rcvThread.interrupt();// kill the thread

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
