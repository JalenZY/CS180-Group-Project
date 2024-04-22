import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ChatWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea chatArea;

    public ChatWindow(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
        setTitle("Chat Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

        // Start a thread to continuously listen for incoming messages
        new Thread(() -> {
            try {
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    displayMessage(serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }
}
