import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FriendsWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea friendsArea;

    public FriendsWindow(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
        setTitle("Friends Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        friendsArea = new JTextArea();
        friendsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(friendsArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

        // Start a thread to continuously listen for updated friends list
        new Thread(() -> {
            try {
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    displayFriends(serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void displayFriends(String friends) {
        friendsArea.setText(friends);
    }
}
