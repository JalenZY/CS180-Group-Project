import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
/**
 * SearchWindow.java
 *
 * GUI Class used to search for users - displays all users initially and then Limits 
 * the displayed names as further information is entered into the search box and the 
 * search button pressed
 *
 * @author ThomasRalston - Juan RodriguezL105
 * @version April 26, 2024
 */
public class SearchWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextField searchField;
    private JTextArea searchResultsArea;
    private JList<String> nameList;
    public  DefaultListModel<String> listModel;
    public String[] results;
    public String fromUser;

    public SearchWindow(PrintWriter out, BufferedReader in, String fromUser) {
        this.out = out;
        this.in = in;
        this.fromUser = fromUser;
        setTitle("Search Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        searchField = new JTextField();
        add(searchField, BorderLayout.NORTH);

        searchResultsArea = new JTextArea();
        searchResultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize listModel
        listModel = new DefaultListModel<>();
        results = new String[0];

        //Create the JList with the list model
        nameList = new JList<>(listModel);

        scrollPane = new JScrollPane(nameList);
        add(scrollPane, BorderLayout.CENTER);

        //Initialize search window full of all users
        initializeSearchWithAllUsers();

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
                        userProfileWindow = new UserProfileWindow(in, out, selectedName, "SearchClass", fromUser);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    userProfileWindow.setVisible(true);
                }
            }
        });

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                if (query.isEmpty()) {
                    query = "ALLUSERS";
                }
                try {
                    String searchList = MyClient.searchForUser(out, in, query);
                    results = searchList.split("----");
                    System.out.println(Arrays.toString(results));
                    //Clear and Populate List
                    listModel.clear();
                    for (String item : results) {
                        listModel.addElement(item);
                    }

                    //Create the JList with the list model
                    //nameList = new JList<>(listModel);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //listModel.clear(); // Clear previous results
            }
        });

        // Add the JList to a JScrollPane
        add(searchButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeSearchWithAllUsers() {
        try {
            String searchList = MyClient.searchForUser(out, in, "ALLUSERS");
            results = searchList.split("----");
            System.out.println(Arrays.toString(results));
            // Clear and Populate List
            listModel.clear();
            for (String item : results) {
                listModel.addElement(item);
            }

            //Create the JList with the list model
            //nameList = new JList<>(listModel);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void displayResults(String results) {
        searchResultsArea.setText(results);
    }
}
