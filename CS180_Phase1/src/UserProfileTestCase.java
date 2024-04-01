import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserProfileTestCase {

    private UserProfile userProfile;

    @Before
    public void setUp() {
        userProfile = new UserProfile("123", "jj_rodriguez", "JJ", "Rodriguez", "jj.rodriguez@example.com", "password",
                "2005-06-29", "Male", "Soccer", "Hiking", "Running", "Workingout", "New York", "USA", "University");
    }
    @Test
    public void testUpdateFirstname() {
        String newFirstname = "JJ";
        String updatedProfile = userProfile.UpdateFirstname(newFirstname);
        assertTrue(updatedProfile.contains(newFirstname));
    }

    @Test
    public void testUpdateLastname() {
        String newLastname = "Rodriugez";
        String updatedProfile = userProfile.UpdateLastname(newLastname);
        assertTrue(updatedProfile.contains(newLastname));
    }

    @Test
    public void testUpdateEmail() {
        String newEmail = "jj.rodriguez@example.com";
        String updatedProfile = userProfile.UpdateEmail(newEmail);
        assertTrue(updatedProfile.contains(newEmail));
    }

    @Test
    public void testUpdatePassword() {
        String newPassword = "newpassword";
        String updatedProfile = userProfile.UpdatePassword(newPassword);
        assertTrue(updatedProfile.contains(newPassword));
    }
}
