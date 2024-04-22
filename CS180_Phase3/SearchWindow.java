import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SearchWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextField searchField;
    private JTextArea searchResultsArea;

    public SearchWindow(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
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

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        add(searchButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void search() {
        String query = searchField.getText();
        // Send the search query to the server
        out.println("SEARCH###" + query);
    }

    public void displayResults(String results) {
        searchResultsArea.setText(results);
    }
}
