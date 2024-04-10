import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MyClient {
    public static void main(String[] a)
    {
        try {
            String ip = "127.0.0.1";
            int port = 1234;
            Socket socket = new Socket(ip, port);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("hello");
            while(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
