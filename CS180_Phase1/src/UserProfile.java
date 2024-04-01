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
 * //@version March 31, 2024
 *
 */
public class UserProfile { //EXTENDS WHAT????
    private String userID; //Users unique ID code
    private String username; //Users selected username - name that associated with ID
    private String userFirstname; //Users first name
    private String userLastname; //Users Last name
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

    //If information is skipped then a "--" will be inserted in info's place - handeled in Database
    //Certain information is required, while others are options
    //Required: username, userFirstname, user Lastname email, password
    //Optional: birthday, gender, hobbies 1-4, homeLocation, usersRegion, collegeName
    //**This class will not enact these required classes, this will be done in the database
    //however this class will create methods for adding them**
    //**This Class will not write to the text file, that is left to the database**

    //Method to Write collected information to userprofile.txt file
    public UserProfile(String userID, String username, String userFirstname, String userLastname,
                       String password, String birthday, String gender, String hobby1, String hobby2, String hobby3,
                       String hobby4, String homeLocation, String usersRegion, String collegeName) {
        //Initialize and set all variables
        this.userID = userID;
        this.username = username;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
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
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", username, userFirstname, userLastname,
                password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation, usersRegion, collegeName));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        return Objects.equals(userID, that.userID) && Objects.equals(getusername(), that.getusername()) &&
                Objects.equals(getuserFirstname(), that.getuserFirstname()) && Objects.equals(getuserLastname(),
                that.getuserLastname()) && Objects.equals(getpassword(),
                that.getpassword()) && Objects.equals(getbirthday(), that.getbirthday()) && Objects.equals(getgender(),
                that.getgender()) && Objects.equals(gethobby1(), that.gethobby1()) && Objects.equals(gethobby2(),
                that.gethobby2()) && Objects.equals(gethobby3(), that.gethobby3()) && Objects.equals(gethobby4(),
                that.gethobby4()) && Objects.equals(gethomeLocation(), that.gethomeLocation()) &&
                Objects.equals(getusersRegion(), that.getusersRegion()) && Objects.equals(getcollegeName(),
                that.getcollegeName());
    }

    //Method allows for username to be updated and changed
    //String will be replaced in file using database
    public String updateFirstname(String newfirstname) {
        this.userFirstname = newfirstname;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateLastname(String newlastname) {
        this.userLastname = newlastname;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updatePassword(String newpassword) {
        this.password = newpassword;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateBirthday(String newbirthday) {
        this.birthday = newbirthday;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateGender(String newgender) {
        this.gender = newgender;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateHobby1(String newhobby1) {
        this.hobby1 = newhobby1;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateHobby2(String newhobby2) {
        this.hobby2 = newhobby2;
        //String string = toString(); //Calls old String

        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateHobby3(String newhobby3) {
        this.hobby3 = newhobby3;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateHobby4(String newhobby4) {
        this.hobby4 = newhobby4;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateHomeLocation(String newhomeLocation) {
        this.homeLocation = newhomeLocation;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateUsersRegion(String newusersRegion) {
        this.usersRegion = newusersRegion;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }
    public String updateCollegeName(String newcollegeName) {
        this.collegeName = newcollegeName;
        //String string = toString(); //Calls old String
        return (String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", userID, username, userFirstname,
                userLastname, password, birthday, gender, hobby1, hobby2, hobby3, hobby4, homeLocation,
                usersRegion, collegeName));
    }

    //Setters
    public void setuserID(String userID) {
        this.userID = userID;
    }
    public void setusername(String username) {
        this.username = username;
    }
    public void setuserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }
    public void setuserLastname(String userLastname) {
        this.userLastname = userLastname;
    }
//    public void setemail(String email) {
//        this.email = email;
//    }
    public void setpassword(String password) {
        this.password = password;
    }
    public void setbirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setgender(String gender) {
        this.gender = gender;
    }
    public void sethobby1(String hobby1) {
        this.hobby1 = hobby1;
    }
    public void sethobby2(String hobby2) {
        this.hobby2 = hobby2;
    }
    public void sethobby3(String hobby3) {
        this.hobby3 = hobby3;
    }
    public void sethobby4(String hobby4) {
        this.hobby4 = hobby4;
    }
    public void sethomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }
    public void setusersRegion(String usersRegion) {
        this.usersRegion = usersRegion;
    }
    public void setcollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    //Getters
    public String getUserID() {
        return userID;
    }
    public String getusername() {
        return username;
    }
    public String getuserFirstname() {
        return userFirstname;
    }
    public String getuserLastname() {
        return userLastname;
    }
//    public String getemail() {
//        return email;
//    }
    public String getpassword() {
        return password;
    }
    public String getbirthday() {
        return birthday;
    }
    public String getgender() {
        return gender;
    }
    public String gethobby1() {
        return hobby1;
    }
    public String gethobby2() {
        return hobby2;
    }
    public String gethobby3() {
        return hobby3;
    }
    public String gethobby4() {
        return hobby4;
    }
    public String gethomeLocation() {
        return homeLocation;
    }
    public String getusersRegion() {
        return usersRegion;
    }
    public String getcollegeName() {
        return collegeName;
    }
} //End Class
