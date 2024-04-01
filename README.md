# CS180-Group-Project
need this
 - BlockedList.java
  This class provides all the methods necessary for the database to create, add, remove, edit, and compare users to the Blocked List - This class also writes and removes information to the blockedlist.txt
- Conversations.java
  This class contains all the methods required to format and add messages into a conversation chain that can allow for users to view past messages per conversation. These conversation chains will be stored in the conversations.txt file. This class uses the format: "conversationID///participantsID///messages", and the messages format: "messageID//senderID//recipientID//timestamp//isRead//content"
- DataBaseInterfaces.java
  creates the interfaces used by the database
- FriendsList.java
  This class provides all the methods necessary for the database to create, add, remove, edit, and compare users to the Friends List - All Writing and removing from the database will be handled by the database
- Messaging.java
  This class contains all the methods required to format and store all Direct Messaging messages to the messaging.txt file. This class also writes to the messages.txt file when a new message is created. This class also "encrypts" the messages by replacing all commas with ".--.", this is done to make sure that there are no errors when using split(",") later on in the classes.
- PeopleDatabase.java
          
  2.A list of who submitted which parts of the assignment on Brightspace and Vocareum. 
        For example: Student 1 - Submitted Report on Brightspace. Student 2 - Submitted Vocareum workspace.
  3. A detailed description of each class. This should include the functionality included in the class, the testing done to verify it works properly, and its relationship to other classes in the project. 
+ we need javadocs on all classes
