import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * ChatWindow.java
 *
 * GUI Class used to display all users that main user has conversations with
 * When A user on this list is clicked, then DisplayChatWindow.java will open 
 * and Display the conversation between the users
 *
 * @author ThomasRalston - Juan Rodriguez L105
 * @version April 26, 2024
 */
public class ChatWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea chatArea;
    private JList<String> nameList;
    private DefaultListModel<String> listModel;
    public String fromUser;

    public ChatWindow(PrintWriter out, BufferedReader in, String fromUser) {
        this.out = out;
        this.in = in;
        this.fromUser = fromUser;
        setTitle("Chat Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Get Name List from Client Class
        String friendsString;
        String[] friends;
        try {
            friendsString = MyClient.viewFriends(in, out, fromUser); //Should be the same, as can only message friends
            friends = friendsString.split("----");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(friends);

        //Populate ListModel
        listModel = new DefaultListModel<>();
        for (String item : friends) {
            listModel.addElement(item);
        }

        //Create the JList with the list model
        nameList = new JList<>(listModel);

        nameList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    //Get the selected name
                    String selectedName = nameList.getSelectedValue();
                    DisplayChatWindow displayChatWindow = new DisplayChatWindow(out, in, fromUser, selectedName);
                    displayChatWindow.setVisible(true);
                }
            }
        });

        //Add the JList to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(nameList);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }
}


