# CS180-Group-Project
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
  Main class that uses other classes and methods to check, using Hashmaps and ArrayLists
  
- UserProfile.java
   Contains all methods for database to call to create, update, and replace all details of a user's profile page. This will write to the file storing all userprofiles

- BlockedListInterface.java
  Lists all the methods and their paramaters used by BlockedList.java

  - FriendsListInterface.java
  Lists all the methods and their paramaters used by FriendsList.java

- MessagingInterface.java
  Lists all the methods and their paramaters used by Messaging.java

  - ConversationsInterface.java
  Lists all the methods and their paramaters used by Conversations.java

- UserProfileInterface.java
  Lists all methods and their parameters used by UserProfile.java
- BadDataException.java
  checks for exception

  Submitted on Vocareum by Thomas Ralston


