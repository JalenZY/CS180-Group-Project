import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
/**
 * MainMenu.java
 *
 * GUI Class that acts as the home page for entire project - Allows user to 
 * navigate between different aspects of the program
 *
 * @author ThomasRalston - Juan RodriguezL105
 * @version April 26, 2024
 */
public class MainMenu extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    public String fromUser;

    public MainMenu(Socket socket, PrintWriter out, BufferedReader in, BufferedReader stdIn) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.stdIn = stdIn;

        // Create login panel
        LoginPanel loginPanel = new LoginPanel(stdIn, in, out); //Displays Login Panel

        loginPanel.addLoginListener(new LoginListener() {
            public void onLoginSuccess(String username) {
                // Once login is successful, initialize the main menu
                fromUser = username;
                initializeMainMenu();
            }

            public void onLoginFailure() {
                // Handle login failure if needed
                // For example, display an error message
                //System.out.println("Login failed!");
            }
        });
    }

    private void initializeMainMenu() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        JButton messageButton = new JButton("Message"); //Block to Send Messages
        messageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMessageWindow();
                //return false;
            }
        });
        add(messageButton);

        JButton chatButton = new JButton("Chat"); //Block to Display Chats
        chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openChatWindow();
                //return false;
            }
        });
        add(chatButton);

        JButton searchButton = new JButton("Search"); //Search for Users in Total Users List
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSearchWindow();
                //return false;
            }
        });
        add(searchButton);

        JButton friendsButton = new JButton("Friends");
        friendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFriendsWindow();
                //return false;
            }
        });
        add(friendsButton);

        JButton userProfileButton = new JButton("User Profile Page");
        userProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMainUserProfileWindow();
            }
        });
        add(userProfileButton);

        JButton userLogOutButton = new JButton("Log Out");
        userLogOutButton.setBounds(200, 150, 90, 50);
        userLogOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenu.this, "Thank You! Have A Wonderful Day!");
                dispose(); //Close Main Menu
                //openLoginPanel();
                new MainMenu(socket, out, in, stdIn);
            }
        });
        add(userLogOutButton);

        setVisible(true);
    }

    private void openLoginPanel() {
        LoginPanel loginPanel = new LoginPanel(stdIn, in, out);
        loginPanel.setVisible(true);
    }

    private void openMessageWindow() {
        MessageWindow messageWindow = new MessageWindow(out, in, fromUser);
    }

    private void openChatWindow() {
        ChatWindow chatWindow = new ChatWindow(out, in, fromUser);
    }

    private void openSearchWindow() {
        SearchWindow searchWindow = new SearchWindow(out, in, fromUser);
    }

    private void openFriendsWindow() {
        FriendsWindow friendsWindow = new FriendsWindow(out, in, fromUser);
    }

    private void openMainUserProfileWindow() {
        MainUserProfileWindow mainUserProfileWindow = new MainUserProfileWindow(in, out, fromUser);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 4242);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));  //Reads from User
                    new MainMenu(socket, out, in, stdIn);
                    //MainMenu.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
