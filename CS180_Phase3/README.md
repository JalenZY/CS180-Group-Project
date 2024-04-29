CS180-Group-Project
With the project now complete, the entire system can be ran by opening two classes, MyServer.java and MainMenu.java. The first class to be ran will be MyServer.java and then running MainMenu.java, the GUI login panel will be opened allowing for the entire application to be accessed. 
This project was created in three main chuncks, with the databases being created first, then the server/client code, and finally the GUI. Due to this order of creation, there are two main sets of test cases. The first set of test cases PeopleDatabaseTestCase.java and DirectMessagingDatabase.java were created to test the databases ability to add new user information and messages to their respective text files. These test cases were used to iron out all and any kinks in the databases before integrating the server/client code. The other set of test case set is ClientServerTestCase.java, which was used to iron out the functioning and integration of the server/client code to that of the database. However, due to how the GUI has since implemented with the server/client code and the original architecture of the server/client code, this test case is no longer a true representation of the methods involved with the server/client database interaction. Finally, the GUI has no test cases, however it if fully opperational and can be tested as be. Provided below is a user account and password that can be used to test the system. This username is the same one that was used in the live demonstration and that video can be referenced to see how this user can interact with others, along with seeing how the database text files update as user actions are processed. 
user: Tralston25 
Password: Space25

However, a quick walk through as to how one can tell that the system is truly updating all the required data is shown below. 
Messaging: 
- To show that the user sent messages are being saved and stored properly, the two text files MessagesStorage.txt and conversations.txt can be analyzed to show the live update of this information as it is sent. (If there is an issue with the data not showing up immediately, then click off the text file tab and then click back to it)
- In the MessagesStorage.txt text file, one will see the new messages get added to the text file, and in the conversations.txt text file, one will see the conversation string be expanded to add in the new conversation. 
- Reference the Live Demo for a view at this in action! 
UserProfile Information:
- The text file UserProfileList.txt is responsible for the storage of all users and their respective information. A way to view that new user information is correctly saved to the text file, can be seen by editting the main user profile. After clicking on the user profile button on the main menu screen of the GUI, and then clicking the update button next to the editted information, then the new changes can be seen in the UserProfileList.txt text file. This is also demonstrated live in the demonstration video.
User Relationships
- A users relationship to another can be set as either friend, blocked, or nonassociated. A user can add a friend by clicking on any username and clicking the add friend button, and similarly a user can block another user, only if they were friends beforehand, by clicking the block user button on the userprofile page. Once a user has acted on sending a friend invite to another user, then the other user will have the option to accept or decline that friendship. All friendships are stored in the friendsList.txt text file, while all blocks are stored in the blockedUserList.txt text file. These actions are also demonstrated in the live demonstration video.

GUI Classes: 
- LoginPanel.java
    - This class handles prompting the user to login to their account by calling on the respective client methods. It also provides the user with the option to create a new account and will then respectively call on AddUserPanel.java, if that button is pressed. Once the user has successfully logged in, then the main menu window will appear.
- MainMenu.java
    - This class acts as the distribution center for the entire platform. Providing the options for the user to send messages, view conversations, search for new users, see friends, edit their personal user profile, and finally logout. When one of these options is pressed, then the class will call on the respective class required to open the correct GUI panel.
- MessageWindow.java
    - This window allows for the user to send messages to other users. This panel is also where the user is notified about their message status, as to if it has been sent or not. If it has not been sent, then a message is displayed with the possible reason why. This panel opperates by having the user type in a specific username, a following message and then hitting send.
- ChatWindow.java
    - This window allows for the user to view their conversations with other users. The window first displays a list of all the user's friends, and then displays their conversation once the user selects a specific friend.
- SearchWindow.java
    - This window allows for the user to search through the current database of all users, and view thier user profile pages. At first the window will display all users, however once text is inserted into the top search bar and the search button pressed, then the list will be limitted to only those usernames that contain part of that text, or whose user's name contains part of that text. Once a username from the list is selected, then that user's specific userprofile page will be displayed, giving the main user the option to add them as a friend.
- FriendsWindow.java
    - This window allows for the user to view and edit their current friendships. This window initially displays all the user's friends, and then when a friend's username is selected, then their respective user profile page will be displayed. This profile page, however, will have the option to either accept/deny/remove a friend or block/unblock the friend, depending on their current relationship status. The friends window is also where the main user will be able to view who has requested their friendship, and then act accordingly. 
- UserProfileWindow.java
    - This window is the user profile page that has been repeadetly referenced above. This window handles the displaying of all profile pages of current users. This window also displays the relationship action buttons between users, however it has logic written to only display certain buttons at a time, depending on a multitude of factors.
- MainUserProfileWindow.java
    - Not to be confused with the last window, this window allows for the main user to view and edit their own user profile page. This is done by providing a mixture of text boxes and drop down lists that allow for the user to edit their profile page. To be noted however, is the fact that the user is Not able to change their username. The reason for this is due to the intertwined relationship between the database methods and a user's userID and username.
- LoginListener.java
    - This class is simple helper class that assists the login window class with sending information to the main menu panel, to know when the main menu panel is allowed to be displayed. 


DataBases and Client/Server Code Details:
PeopleDatabaseTestCase.java To properly test the peopleDatabase class, a couple checks on the supporting text files need to be made.
userProfileList.txt
Ensure "UserID2,user4,Sarah,Smith,pass456,1995-05-15,female,Painting,Hiking,Photography,Yoga,Los Angeles,USA,Community College" is added
ensure "UserID1,testUser,John,Doe,password123,1990-01-01,male,Reading,Swimming,Traveling,Cooking,New York,USA,University of XYZ" is added
friendshipList.txt

Ensure "FID_user3_user4_Active_2024-04-12 13:38:44" is added
Ensure that "FID_user3_user4_Pending_2024-04-12 13:38:44" is not on the text file
blockedUsersList.txt

Ensure "BID_user9_user10_2024-04-11 15:40:10" is not in the text file
Ensure "BID_user1_user2_2024-04-11 09:45:20" is on the file
With these changes the test case should properly run and show all passes. However, a test case might show a "fail" if one of the text files is not updated, this is due to the fact that the peopleDatabase.java class methods return true for the proper addition or removal of a string false if the string already exists or if there was some other error.

DirectMessagingDatabaseTestCase.java The test cases for this method are a bit wonky and will properly run the program, however the strings that they use to check against the text files are not always correctly grabbing the proper string segment and therefore show many test cases as failing. A way to visible check that the database is able to properly add and update the respective text files is to inspect the "conversations.txt" file and "messagesStorage.txt" files. If this is done, one will notice that the conversations.txt file has an updated conversation string added, and that the messagesStorage text file has more strings added that have the conversation ID "conv1"

PeopleDatabase.java This is the main method that handles all the calling of supporting classes and writing to the text files. The text files that will be mentioned act as the memory of our program and are important to the operation of our program. The helping classes are: userProfile.java, blockedList.java, and frienshipList.java. The userProfile class actively handles all the organization and formatting of a users "personal" profile information and is called on by the database to store the infromation into the userProfileList.txt text file. The blockedList class handles all the formatting and manipulation of information between users that block eachother. This formatting is directly inserted into the blockedUserList.txt text file through the database methods. Finally, the friendshipList.java class handles all the proper formatting and manipulation of test for the text file blockUserList.txt. All the searching, reading, and manipulation of information in the text files is handled by the datbase class itself. By properly keeping the text files up to date and organized, the relationship and information of users can be easily accessed by any method in the program

DirectMessagingDatabase.java This is the main method that handles all the calling of supporting classes and the writing to the two supporting text files. The supporting classes are: "Messaging.java" and "Conversations.java", along with the supporting text files: "conversations.txt" and "messagesSupport.txt". The Messaging class handles the proper formatting and manipulation of information that is directly related to an individual message. This formatting is stored in the messagesSupports text file, this text file stores all the messages sent by all users and is key to the proper organization and formation of the conversations created in the conversations class. The conversations class chains together all the messages sent between two users and ties them under a single conversationID, this allows for quick ease of access to a users conversations history. This information is stored in the conversations.txt text file and is key to the functioning of the database. The database calls on internal and external methods to properly handle the given inputs from the the user passed down through the client server classes.

MyClient / MyServer These two classes work by the client acting as a structure for phase 3's GUI to directly interact with, to send requests and display outputs. The GUI will interact with the client, enacting a certain method that will then prompt the user to input required information, which is then passed to the server class which takes that information and uses it to call on the appropriate database methods, and then pass the method outputs back to the client. The information that the client prompts from the user is the parameters for a method in the database. Most calculations and data organization is handled in the database, while the server handles most of the logic, and the client simply gathers the required information.

BlockedList.java This class provides all the methods necessary for the database to create, add, remove, edit, and compare users to the Blocked List - This class also writes and removes information to the blockedlist.txt

Conversations.java This class contains all the methods required to format and add messages into a conversation chain that can allow for users to view past messages per conversation. These conversation chains will be stored in the conversations.txt file. This class uses the format: "conversationID///participantsID///messages", and the messages format: "messageID//senderID//recipientID//timestamp//isRead//content"

DataBaseInterfaces.java creates the interfaces used by the database

FriendsList.java This class provides all the methods necessary for the database to create, add, remove, edit, and compare users to the Friends List - All Writing and removing from the database will be handled by the database

Messaging.java This class contains all the methods required to format and store all Direct Messaging messages to the messaging.txt file. This class also writes to the messages.txt file when a new message is created. This class also "encrypts" the messages by replacing all commas with ".--.", this is done to make sure that there are no errors when using split(",") later on in the classes.

PeopleDatabase.java Main class that uses other classes and methods to check, using Hashmaps and ArrayLists

UserProfile.java Contains all methods for database to call to create, update, and replace all details of a user's profile page. This will write to the file storing all userprofiles

BlockedListInterface.java Lists all the methods and their paramaters used by BlockedList.java

FriendsListInterface.java Lists all the methods and their paramaters used by FriendsList.java
MessagingInterface.java Lists all the methods and their paramaters used by Messaging.java

ConversationsInterface.java Lists all the methods and their paramaters used by Conversations.java
UserProfileInterface.java Lists all methods and their parameters used by UserProfile.java

BadDataException.java checks for exception

Submitted on Vocareum by Thomas Ralston
