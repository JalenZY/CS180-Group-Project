import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class MainMenu extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public MainMenu(Socket socket, PrintWriter out, BufferedReader in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        JButton messageButton = new JButton("Message");
        messageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMessageWindow();
            }
        });
        add(messageButton);

        JButton chatButton = new JButton("Chat");
        chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openChatWindow();
            }
        });
        add(chatButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSearchWindow();
            }
        });
        add(searchButton);

        JButton friendsButton = new JButton("Friends");
        friendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFriendsWindow();
            }
        });
        add(friendsButton);

        setVisible(true);
    }

    private void openMessageWindow() {
        MessageWindow messageWindow = new MessageWindow(out, in);
    }

    private void openChatWindow() {
        ChatWindow chatWindow = new ChatWindow(out, in);
    }

    private void openSearchWindow() {
        SearchWindow searchWindow = new SearchWindow(out, in);
    }

    private void openFriendsWindow() {
        FriendsWindow friendsWindow = new FriendsWindow(out, in);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 4242);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    new MainMenu(socket, out, in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
