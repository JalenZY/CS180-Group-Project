import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int portNumber = 1234;

        System.out.println("Connecting to server on port " + portNumber);

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            System.out.println("Type a message (type 'bye' to quit):");
            while ((userInput = stdIn.readLine()) != null && !userInput.equalsIgnoreCase("bye")) {
                out.println(userInput); // Sending to the server
                System.out.println("Server echo: " + in.readLine()); // Reading the echo from the server
                System.out.println("Type a message (type 'bye' to quit):");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
