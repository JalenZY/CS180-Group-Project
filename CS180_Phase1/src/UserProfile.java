import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
/**
 * UserProfile.java
 *
 * Contains all methods for database to call to create, update, and replace all details of a
 * user's profile page. This will write to the file storing all userprofiles
 *
 *
 * @author Thomas Ralston, L105
 *
 * //@version March 31, 2024
 *
 */
public class UserProfile extends Object {
    private String userID; //Users unique ID code
    private String userName; //Users selected username - name that associated with ID
    private String userFirstName; //Users first name
    private String userLastName; //Users Last name
    // private String email; //Users selected email
    private String password; //Users selected password
    private String birthday; //Users birthday dd/mm/yr
    private String gender; //Users gender - three options
    private String hobby1; //Users 1st selected hobby
    private String hobby2; //Users 2nd selected hobby
    private String hobby3; //Users 3rd selected hobby
    private String hobby4; //Users 4th selected hobby
    private String homeLocation; //Where user lives
    private String usersRegion; //Where users is from
    private String collegeName; //College user attends
    private String toString2;

    //If information is skipped then a "--" will be inserted in info's place - handeled in Database
    //Certain information is required, while others are options
    //Required: username, userFirstname, user Lastname email, password
    //Optional: birthday, gender, hobbies 1-4, homeLocation, usersRegion, collegeName
    //**This class will not enact these required classes, this will be done in the database
    //however this class will create methods for adding them**
    //**This Class will not write to the text file, that is left to the database**

    //Method to Write collected information to userprofile.txt file
    public UserProfile (String userID) {
        try {
            //Read through text file to collect information for userID
            BufferedReader reader = new BufferedReader(new FileReader("UserProfileList.txt"));
            ArrayList<String> lines = new ArrayList<>();
            String line1;
            boolean next = true;
            while (((line1 = reader.readLine()) != null) && next) {
                //Skip empty lines or lines with only whitespace characters
                if (line1.contains(userID)) {
                    if (!line1.trim().isEmpty()) {
                        //Write line containing given userID to the arraylist
                        lines.add(line1);
                        next = false;
                    }
                }
            }
            //Parse through and separate arrayList into respective categories
            if (!next) {
                String[] parts = lines.get(0).split(","); //Get first line, should only be one line
                this.userID = parts[0];
                this.userName = parts[1];
                this.userFirstName = parts[2];
                this.userLastName = parts[3];
                this.password = parts[4];
                this.birthday = parts[5];
                this.gender = parts[6];
                this.hobby1 = parts[7];
                this.hobby2 = parts[8];
                this.hobby3 = parts[9];
                this.hobby4 = parts[10];
                this.homeLocation = parts[11];
                this.usersRegion = parts[12];
                this.collegeName = parts[13];
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public UserProfile(String userID1, String username1, String userFirstname1, String userLastname1,
                       String password1, String birthday1, String gender1, String hobby01, String hobby02,
                       String hobby03, String hobby04, String homeLocation1, String usersRegion1, String collegeName1) {
        //Initialize and set all variables
        this.userID = userID1;
        this.userName = username1;
        this.userFirstName = userFirstname1;
        this.userLastName = userLastname1;
        this.password = password1;
        this.birthday = birthday1;
        this.gender = gender1;
        this.hobby1 = hobby01;
        this.hobby2 = hobby02;
        this.hobby3 = hobby03;
        this.hobby4 = hobby04;
        this.homeLocation = homeLocation1;
        this.usersRegion = usersRegion1;
        this.collegeName = collegeName1;

//        try (BufferedWriter writer = new BufferedWriter()) {
//            // Write user profile information to the file
//            writer.write(toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public String toString() { //Writes string in format for database
        //Format:username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,hobby4,
        // homeLocation,usersRegion,collegeName;
        toString2 = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", userID, userName, userFirstName, userLastName,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName);
        return (toString2);
    }

    public boolean equals(Object o) { //UPDATE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        return Objects.equals(userID, that.userID) && Objects.equals(getUserName(), that.getUserName()) &&
                Objects.equals(getUserFirstName(), that.getUserFirstName()) && Objects.equals(getUserLastName(),
                that.getUserLastName()) && Objects.equals(getPassword(),
                that.getPassword()) && Objects.equals(getBirthday(), that.getBirthday()) && Objects.equals(getGender(),
                that.getGender()) && Objects.equals(getHobby1(), that.getHobby1()) && Objects.equals(getHobby2(),
                that.getHobby2()) && Objects.equals(getHobby3(), that.getHobby3()) && Objects.equals(getHobby4(),
                that.getHobby4()) && Objects.equals(getHomeLocation(), that.getHomeLocation()) &&
                Objects.equals(getUsersRegion(), that.getUsersRegion()) && Objects.equals(getCollegeName(),
                that.getCollegeName());
    }

    //Setters
    public void setUserID(String userID) {
        userID = userID;
    }
    public void setUserName(String username) {
        this.userName = username;
    }
    public void setUserFirstName(String userFirstname) {
        this.userFirstName = userFirstname;
    }
    public void setUserLastName(String userLastname) {
        this.userLastName = userLastname;
    }
    public void setPassword(String password1) {
        this.password = password1;
    }
    public void setBirthday(String birthday1) {
        this.birthday = birthday1;
    }
    public void setGender(String gender1) {
        this.gender = gender1;
    }
    public void setHobby1(String hobby01) {
        this.hobby1 = hobby01;
    }
    public void setHobby2(String hobby02) {
        this.hobby2 = hobby02;
    }
    public void setHobby3(String hobby03) {
        this.hobby3 = hobby03;
    }
    public void setHobby4(String hobby04) {
        this.hobby4 = hobby04;
    }
    public void setHomeLocation(String homeLocation1) {
        this.homeLocation = homeLocation1;
    }
    public void setUsersRegion(String usersRegion1) {
        this.usersRegion = usersRegion1;
    }
    public void setCollegeName(String collegeName1) {
        this.collegeName = collegeName1;
    }

    //Getters
    public String getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserFirstName() {
        return userFirstName;
    }
    public String getUserLastName() {
        return userLastName;
    }
    public String getPassword() {
        return password;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getGender() {
        return gender;
    }
    public String getHobby1() {
        return hobby1;
    }
    public String getHobby2() {
        return hobby2;
    }
    public String getHobby3() {
        return hobby3;
    }
    public String getHobby4() {
        return hobby4;
    }
    public String getHomeLocation() {
        return homeLocation;
    }
    public String getUsersRegion() {
        return usersRegion;
    }
    public String getCollegeName() {
        return collegeName;
    }
} //End Class
