import java.util.Objects;
/**
 * UserProfile.java
 *
 * Contains all methods for database to call to create, update, and replace all details of a
 * user's profile page **Does not write any information to file**
 *
 *
 *
 * @author Thomas Ralston, L105
 *
 * //@version April 3, 2024
 *
 */
public class UserProfile { //EXTENDS WHAT????
    private String userID; //Users unique ID code
    private String username; //Users selected username - name that associated with ID
    private String userFirstname; //Users first name
    private String userLastname; //Users Last name
    private String email; //Users selected email
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

    //If information is skipped then a "\" will be inserted in info's place
    //Certain information is required, while others are options
    //Required: username, userFirstname, user Lastname, email, password
    //Optional: birthday, gender, hobbies 1-4, homeLocation, usersRegion, collegeName
    //**This class will not enact these required classes, this will be done in the database
    //however this class will create methods for adding them**
    //**This Class will not write to the text file, that is left to the database**

    //Method to Write collected information to userprofile.txt file
    public UserProfile(String userID, String username, String userFirstname, String userLastname, String email,
                       String password, String birthday, String gender, String hobby1, String hobby2, String hobby3,
                       String hobby4, String homeLocation, String usersRegion, String collegeName) {
        //Initialize and set all variables
        this.userID = userID;
        this.username = username;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.hobby1 = hobby1;
        this.hobby2 = hobby2;
        this.hobby3 = hobby3;
        this.hobby4 = hobby4;
        this.homeLocation = homeLocation;
        this.usersRegion = usersRegion;
        this.collegeName = collegeName;

    }

    public String toString() { //Writes string in format for database
        //Format:username,userFirstname,userLastname,email,password,birthday,gender,hobby1,hobby2,hobby3,hobby4,
        // homeLocation,usersRegion,collegeName;
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        return userID == that.userID && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getUserFirstname(), that.getUserFirstname()) && Objects.equals(getUserLastname(), that.getUserLastname()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getBirthday(), that.getBirthday()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getHobby1(), that.getHobby1()) && Objects.equals(getHobby2(), that.getHobby2()) && Objects.equals(getHobby3(), that.getHobby3()) && Objects.equals(getHobby4(), that.getHobby4()) && Objects.equals(getHomeLocation(), that.getHomeLocation()) && Objects.equals(getUsersRegion(), that.getUsersRegion()) && Objects.equals(getCollegeName(), that.getCollegeName());
    }

    //Method allows for username to be updated and changed
    //String will be replaced in file using database
    public String UpdateFirstname(String newfirstname) {
        this.userFirstname = newfirstname;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateLastname(String newlastname) {
        this.userLastname = newlastname;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateEmail(String newemail) {
        this.email = newemail;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdatePassword(String newpassword) {
        this.password = newpassword;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateBirthday(String newbirthday) {
        this.birthday = newbirthday;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateGender(String newgender) {
        this.gender = newgender;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateHobby1(String newhobby1) {
        this.hobby1 = newhobby1;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateHobby2(String newhobby2) {
        this.hobby2 = newhobby2;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateHobby3(String newhobby3) {
        this.hobby3 = newhobby3;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateHobby4(String newhobby4) {
        this.hobby4 = newhobby4;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateHomeLocation(String newhomeLocation) {
        this.homeLocation = newhomeLocation;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateUsersRegion(String newusersRegion) {
        this.usersRegion = newusersRegion;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public String UpdateCollegeName(String newcollegeName) {
        this.collegeName = newcollegeName;
        //String string = toString(); //Calls old String
        return (String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname, userLastname, email,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    //Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }
    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setHobby1(String hobby1) {
        this.hobby1 = hobby1;
    }
    public void setHobby2(String hobby2) {
        this.hobby2 = hobby2;
    }
    public void setHobby3(String hobby3) {
        this.hobby3 = hobby3;
    }
    public void setHobby4(String hobby4) {
        this.hobby4 = hobby4;
    }
    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }
    public void setUsersRegion(String usersRegion) {
        this.usersRegion = usersRegion;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    //Getters
    public String getUserID() {
        return userID;
    }
    public String getUsername() {
        return username;
    }
    public String getUserFirstname() {
        return userFirstname;
    }
    public String getUserLastname() {
        return userLastname;
    }
    public String getEmail() {
        return email;
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
