import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Conversations.java
 *
 * This class contains all the methods required to format and add messages into a conversation
 * chain that can allow for users to view past messages per conversation. These conversation chains
 * will be stored in the conversations.txt file.
 * Format: "conversationID///participantsID///messages
 * messages format: "messageID//senderID//recipientID//timestamp//isRead//content"
 *
 * TO DO // FIX // TO ASK ABOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * - Format Date like: "MM-dd-yyyy HH:mm:ss" -- Military Time
 * - Might need to "encrypt" commas in given messages as they could result in false splitting
 * - sanity Check ReadFormat method
 *
 * @author Thomas Ralston, L105
 *
 * @version March 31, 2024
 *
 */
public class Conversations {

    //Fields
    private String conversationID;
    private ArrayList<String> messages; //ArrayList of all messages contained in a conversation
    private boolean archived;
    private String senderID;
    private String recipientID;

    //Constructor
//    public Conversations(String conversationID, List<String> participantIDs) {
//        this.conversationID = conversationID;
//        this.participantIDs = participantIDs;
//        this.messages = new ArrayList<>();
//    }

    public Conversations(String conversationID, String senderID, String recipientID) {
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.messages = new ArrayList<>();
    }
//    conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello.--. how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine.--. thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?
//    conv2///sender2///recipient2///conv2//msg3//sender2//recipient2//2024-04-13//Hello.--. how are you?,conv2//msg4//sender2//recipient2//2024-04-14//I'm fine
//    conv3///sender3///recipient3///conv3//msg6//sender3//recipient3//2024-04-15//What are you doing?

    public Conversations(String conversationID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("conversations.txt"));
            String line;
            messages = new ArrayList<>();
            boolean done = false;
            while (((line = reader.readLine()) != null) && !done) { //((line = reader.readLine()) != null) &&
                String[] parts = line.split("///"); //Conversation section of string
                //Set Class variables equal to those contained in the conversation section of the string
                if (parts.length == 4 && parts[0].equals(conversationID)) { // Check if the conversationID matches
                    this.conversationID = parts[0];
                    this.senderID = parts[1];
                    this.recipientID = parts[2];
                    String[] messagesData = parts[3].split(","); //Takes the multiple messages and
                    for (String messageData : messagesData) {
                        String[] messageParts = messageData.split("//");
                        if (messageParts.length == 6) { // Check if the correct number of parts
                            conversationID = messageParts[0];
                            String messageID = messageParts[1];
                            String senderID = messageParts[2];
                            String recipientID = messageParts[3];
                            String date = messageParts[4];
                            String content = messageParts[5];
                            //Create Messaging object with parsed data
                            //Allows for messages to be added to Classes message ArrayList
                            Messaging message = new Messaging(messageID, conversationID, senderID, recipientID,
                                    date, content);
                            addMessage(message);
                        }
                    }
                    done = true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }

    //conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?
    //conv2///sender2///recipient2///conv2//msg3//sender2//recipient2//2024-04-13//Hello, how are you?,conv2//msg4//sender2//recipient2//2024-04-14//I'm fine
    //conv3///sender3///recipient3///conv3//msg6//sender3//recipient3//2024-04-15//What are you doing?conv1///sender1///recipient1///conv1//msg1//sender1//recipient1//2024-04-13//Hello, how are you?,conv1//msg2//sender1//recipient1//2024-04-14//I'm fine, thank you.,conv1//msg3//sender1//recipient1//2024-04-15//What are you doing?

        //Method to add message to conversation
    public void addMessage(Messaging message) {
        if (messages.isEmpty()) {
            messages = new ArrayList<>();
        }
        messages.add(message.toString()); //Adds the message plus extra info about it in proper format
        writeToFile(); //Write new change to file!!!
    }

    //Method to add message to conversation
    public void addMessage(String message) {
        if (messages.isEmpty()) {
            messages = new ArrayList<>();
        }
        messages.add(message); //Adds the message plus extra info about it in proper format
        writeToFile(); //Write new change to file!!!
    }

    //Method to get last message in conversation - not sure if will use
    public String getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    //Method to break apart and assign variables from an imported conversation file String
    //Format: conversationID1///senderID1///recipientID1///message1,message2,message3
    //message Format: "messageID//conversationID//senderID//recipientID//timestamp//isRead//content"
    public void ReadFormat(String conversationInfo) {
        String[] parts = conversationInfo.split("///");
        this.conversationID = parts[0]; //Will not vary between inputs, as same conversation
        String senderID = parts[1];  //These can vary between inputs
        String recipientID = parts[2];

        //Conversations conversation2 = new Conversations(conversationID, senderID, recipientID);

        if (parts.length > 2) {
            String[] messages2 = parts[2].split(","); //This might interfere with commas in original messages
            for (String messageContent : messages2) { //messages2 in format of messages toString
                //Format: "messageID//conversationID//senderID//recipientID//date//content"
                List<String> messageDetails = Arrays.asList(messageContent.split("//"));
                String messageID = messageDetails.get(1);
                String date = messageDetails.get(4); //date is at index 4
                String content = messageDetails.get(5);

                //String newMessageContent = Messaging.getConvertedContent(messageContent);
                Messaging message = new Messaging(messageID, conversationID, senderID, recipientID,
                        date, content); //Message Class un-encrypts information
                addMessage(message); //Creates new conversation set that will return message info
            }
        }
        // don't get printed when called???
    }

    public void writeToFile() {
    //Format: "conversationID//messageID//senderID//recipientID//timestamp//isRead//content"
    // 1.Read TextFile to ArrayList
    // 2.Remove old Obj's messages
    // 3.Rewrite to text File
    // 4.Write Obj's New messages to file
    try {
        BufferedReader reader = new BufferedReader(new FileReader("messagesStorage.txt"));
        //First Read Text File into a list of strings
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        //Remove all lines containing both participating userIDs
        //Create a new list to store updated lines
        List<String> updatedLines = new ArrayList<>();
        //Iterate through the lines
        for (String line2 : lines) {
                //Check if the line contains both userIDs
                if (line2.contains(senderID)) {
                    if (!line2.contains(recipientID)) {
                        //If it does not contain both userIDs, add it to the updated list
                        updatedLines.add(line2);
                    } else {
                        //If it does contain both userIDs, add it to the class messages list
                        messages.add(line2);
                    }
                } else if (line2.contains(recipientID)) {
                    if (!line2.contains(senderID)) {
                        //If it does not contain both userIDs, add it to the updated list
                        updatedLines.add(line2);
                    }
                } else if (!line2.contains(recipientID)) {
                    if (!line2.contains(senderID)) {
                        //If it does not contain both userIDs, add it to the updated list
                        updatedLines.add(line2);
                    }
                }
            }
        if (updatedLines.isEmpty()) {
            return; //Stop the program, don't clear the text file
        }
        //Write updated list without this objects messages to text file
        BufferedWriter writer = new BufferedWriter(new FileWriter("messagesStorage.txt"));
        for (int i = 0; i < updatedLines.size(); i++) {
            // Check if it's the first line
            if (i != 0) {
                writer.newLine(); // Add a newline character if it's not the first line
            }
            writer.write(updatedLines.get(i)); // Write the line to the file
        }

        //Write objects new messages array to text file
        //Iterate over the elements of the ArrayList
        for (String message : messages) {
            writer.newLine(); // Add a newline character
            writer.write(message); // Write each element to the file
        }
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    //Setters
    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void archive() {
        this.archived = true;
    }

    //Getters
    public String getConversationID() {
        return conversationID;
    }

    public ArrayList<String> getMessages() {
        return messages;
        //return new ArrayList<>(messages);
    }
    public boolean isArchived() {
        return archived;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public String toString() {
        //Format: "conversationID///participantsID///messages
        //messages format: "messageID//senderID//recipientID//date//content"
        String totalMessages = messages.toString(); //Commas should be placed between messages content
        return (String.format("%s///%s///%s///%s", conversationID, senderID, recipientID, totalMessages));
    }

} //End Class
