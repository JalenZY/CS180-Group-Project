import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MessageWindow extends JFrame {
    private PrintWriter out;
    private BufferedReader in;
    private JTextField recipientField;
    private JTextArea messageArea;

    public MessageWindow(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
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
                sendMessage();
            }
        });
        add(sendButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendMessage() {
        String recipient = recipientField.getText();
        String message = messageArea.getText();
        // Send the message to the server
        out.println("SENDMESSAGE###" + recipient + "###" + message);
    }
}

