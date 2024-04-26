import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * FriendsWindow.java
 *
 * GUI Class used to display all users that Main User is Friends with. When a Username is 
 * clicked, then UserProfileWindow.java will open and Display the userProfile Page 
 * for the respective user. 
 *
 * @author ThomasRalston - Juan Rodriguez L105
 * @version April 26, 2024
 */
public class FriendsWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea friendsArea;
    private JList<String> nameList;
    private DefaultListModel<String> listModel;
    public String fromUser;

    public FriendsWindow(PrintWriter out, BufferedReader in, String fromUser) {
        this.out = out;
        this.in = in;
        this.fromUser = fromUser;
        setTitle("Friends Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Get Name List from Client Class
        String friendsString;
        String[] friends;
        try {
            friendsString = MyClient.viewFriends(in, out, fromUser);
            friends = friendsString.split("----");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Arrays.toString(friends));

        //Populate ListModel
        listModel = new DefaultListModel<>();
        for (String item : friends) {
            listModel.addElement(item);
        }

        //Create the JList with the list model
        nameList = new JList<>(listModel);

        // Add a ListSelectionListener to the JList to handle item selection
        nameList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    //Get the selected name
                    String selectedName = nameList.getSelectedValue();

                    //Temporary - Open UserProfile Page
                    UserProfileWindow userProfileWindow = null;
                    try {
                        userProfileWindow = new UserProfileWindow(in, out, selectedName, "FriendsClass", fromUser);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    userProfileWindow.setVisible(true);
                }
            }
        });

        // Add the JList to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(nameList);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);


    }

    public void displayFriends(String friends) {
        friendsArea.setText(friends);
    }
}
