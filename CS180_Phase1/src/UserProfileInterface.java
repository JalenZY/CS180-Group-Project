/**
 * UserprofileInterface.java
 *
 * Lists all the methods and their paramaters used by UserProfile.java
 *
 * @author ThomasRalton, L105
 * @version April 1, 2024
 */
public interface UserProfileInterface {
    // Getters
    String getUserID();
    String getusername();
    String getuserFirstname();
    String getuserLastname();
    String getpassword();
    String getbirthday();
    String getgender();
    String gethobby1();
    String gethobby2();
    String gethobby3();
    String gethobby4();
    String gethomeLocation();
    String getusersRegion();
    String getcollegeName();

    // Setters
    void setuserID(String userID);
    void setusername(String username);
    void setuserFirstname(String userFirstname);
    void setuserLastname(String userLastname);
    void setpassword(String password);
    void setbirthday(String birthday);
    void setgender(String gender);
    void sethobby1(String hobby1);
    void sethobby2(String hobby2);
    void sethobby3(String hobby3);
    void sethobby4(String hobby4);
    void sethomeLocation(String homeLocation);
    void setusersRegion(String usersRegion);
    void setcollegeName(String collegeName);

    // Other methods
    String toString();
    boolean equals(Object o);
    String updateFirstname(String newfirstname);
    String updateLastname(String newlastname);
    String updatePassword(String newpassword);
    String updateBirthday(String newbirthday);
    String updateGender(String newgender);
    String updateHobby1(String newhobby1);
    String updateHobby2(String newhobby2);
    String updateHobby3(String newhobby3);
    String updateHobby4(String newhobby4);
    String updateHomeLocation(String newhomeLocation);
    String updateUsersRegion(String newusersRegion);
    String updateCollegeName(String newcollegeName);
}
