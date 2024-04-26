import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
 * MessageWindow.java
 *
 * GUI Class that Allows for a user to send a message to another user
 *
 * @author ThomasRalston - Juan RodriguezL105
 * @version April 26, 2024
 */
public class MessageWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextField recipientField;
    private JTextArea messageArea;
    public String fromUser;

    public MessageWindow(PrintWriter out, BufferedReader in, String fromUser) {
        this.out = out;
        this.in = in;
        this.fromUser = fromUser;
        setTitle("Message Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        recipientField = new JTextField();
        add(recipientField, BorderLayout.NORTH);

        messageArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String recipient = recipientField.getText();
                String message = messageArea.getText();
                String messageStatus = "Error";
                try {
                    messageStatus = MyClient.manageSendMessage(out, in, fromUser, recipient, message);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(MessageWindow.this, messageStatus);
                clearMessageArea();
            }
        });
        add(sendButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                clearMessageArea();
            }
        });

        setVisible(true);
    }

    // Method to clear the message area
    private void clearMessageArea() {
        messageArea.setText("");
    }
}

