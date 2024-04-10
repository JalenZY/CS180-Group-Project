import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] a)
    {
        try {
            int port = 1234;
            ServerSocket ss = new ServerSocket(port);
            System.out.println("wait client connection");
            // wait client
            Socket socket = ss.accept();
            String clientIp = socket.getInetAddress().getHostAddress();
            System.out.println("client connected, ip="+clientIp);
            // read client data
            OutputStream os = socket.getOutputStream();
            boolean fg = true;
            PrintWriter pw = new PrintWriter(os,fg);
            InputStream is = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String msg;
            while((msg = br.readLine())!=null)
            {
                System.out.println("client send msg to server, msg="+msg);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
