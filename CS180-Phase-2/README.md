
# CS180-Group-Project

- PeopleDatabaseTestCase.java
 To properly test the peopleDatabase class, a couple checks on the supporting text files need to be made.
1. userProfileList.txt
  - Ensure "UserID2,user4,Sarah,Smith,pass456,1995-05-15,female,Painting,Hiking,Photography,Yoga,Los Angeles,USA,Community College" is added
  - ensure "UserID1,testUser,John,Doe,password123,1990-01-01,male,Reading,Swimming,Traveling,Cooking,New York,USA,University of XYZ" is added

2. friendshipList.txt
   - Ensure "FID_user3_user4_Active_2024-04-12 13:38:44" is added
   - Ensure that "FID_user3_user4_Pending_2024-04-12 13:38:44" is not on the text file
  
3. blockedUsersList.txt
- Ensure "BID_user9_user10_2024-04-11 15:40:10" is not in the text file
- Ensure "BID_user1_user2_2024-04-11 09:45:20" is on the file

With these changes the test case should properly run and show all passes. However, a test case might show a "fail" if one of the text files is not updated, this is due to the fact that the peopleDatabase.java class methods return true for the proper addition or removal of a string false if the string already exists or if there was some other error. 


- DirectMessagingDatabase.java
  The test cases for this method are a bit wonky and will properly run the program, however the strings that they use to check against the text files are not always correctly grabbing the proper string segment and therefore show many test cases as failing. A way to visible check that the database is able to properly add and update the respective text files is to inspect the "conversations.txt" file and "messagesStorage.txt" files. If this is done, one will notice that the conversations.txt file has an updated conversation string added, and that the messagesStorage text file has more strings added that have the conversation ID "conv1"

- PeopleDatabase.java
  This is the main method that handles all the calling of supporting classes and writing to the text files. The text files that will be mentioned act as the memory of our program and are important to the operation of our program. The helping classes are: userProfile.java, blockedList.java, and frienshipList.java. The userProfile class actively handles all the organization and formatting of a users "personal" profile information and is called on by the database to store the infromation into the userProfileList.txt text file. 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
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

