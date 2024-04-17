import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;

public class ClientServerTestCase {
    private void testInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void testAddUser() {
        testInput("1\n" +
                  "johndoe\n" +
                  "John\n" +
                  "Doe\n" +
                  "password123\n" +
                  "1999-06-01\n" +
                  "Male\n" +
                  "Soccer\n" +
                  "Cooking\n" +
                  "Hiking\n" +
                  "Gaming\n" +
                  "New York City\n" +
                  "Northeast\n" +
                  "University of 123\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MyClient.main(null);

        assertEquals("User successfully added.\n", outContent.toString());
    }

    @Test
    public void testRemoveUser() {
        testInput("2\nyes\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MyClient.main(null);

        assertEquals("User successfully removed.\n", outContent.toString());
    }

    @Test
    public void testUpdateUserProfile() {
        testInput("3\nHobby 3\nSwimming\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MyClient.main(null);

        assertEquals("User profile successfully updated.\n", outContent.toString());
    }

    @Test
    public void testBlockUser() {
        testInput("4\ntestuser2\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MyClient.main(null);

        assertEquals("User successfully blocked.\n", outContent.toString());
    }

    @Test
    public void testUnblockUser() {
        testInput("5\ntestuser3\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MyClient.main(null);

        assertEquals("User successfully unblocked.\n", outContent.toString());
    }

    @Test
    public void testInvalidAction() {
        testInput("10\n");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MyClient.main(null);

        assertEquals("Invalid action. Please enter a valid number.\n", outContent.toString());
    }
}
