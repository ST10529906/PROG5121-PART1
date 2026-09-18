package partone;

import org.junit.Test;
import static org.junit.Assert.*;

// JUnit tests for registration and login functionality.
public class RegistrationTest {

    @Test
    public void testValidUserName() {
        Registration instance = new Registration();
        boolean result = instance.checkUserName("kyl_1");
        assertTrue(result);
    }

    @Test
    public void testInvalidUserName() {
        Registration instance = new Registration();
        boolean result = instance.checkUserName("kyle!!!!!!!");
        assertFalse(result);
    }

    @Test
    public void testValidPassword() {
        Registration instance = new Registration();
        boolean result = instance.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result);
    }

    @Test
    public void testInvalidPassword() {
        Registration instance = new Registration();
        boolean result = instance.checkPasswordComplexity("password");
        assertFalse(result);
    }

    @Test
    public void testValidCellPhoneNumber() {
        Registration instance = new Registration();
        boolean result = instance.checkCellPhoneNumber("+27838968976");
        assertTrue(result);
    }

    @Test
    public void testInvalidCellPhoneNumber() {
        Registration instance = new Registration();
        boolean result = instance.checkCellPhoneNumber("08966553");
        assertFalse(result);
    }

    @Test
    public void testSuccessfulLogin() {
        Registration instance = new Registration();
        boolean result = instance.loginUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "kyl_1",
                "Ch&&sec@ke99!"
        );
        assertTrue(result);
    }

    @Test
    public void testFailedLogin() {
        Registration instance = new Registration();
        boolean result = instance.loginUser(
                "kyl_1",
                "wrongpassword",
                "kyl_1",
                "Ch&&sec@ke99!"
        );
        assertFalse(result);
    }
}