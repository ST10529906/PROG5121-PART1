
package partone;

import org.junit.Test;
import static org.junit.Assert.*;

// JUnit tests for the QuickChat message functionality.
public class MessageTest {

    @Test
    public void testMessageIDIsValid() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us tonight?",
                "1234567890"
        );

        assertTrue(message.checkMessageID());
    }

    @Test
    public void testMessageIDIsInvalid() {
        Message message = new Message(
                "+27718693002",
                "Hello Mike",
                "12345678901"
        );

        assertFalse(message.checkMessageID());
    }

    @Test
    public void testValidRecipientCell() {
        Message message = new Message(
                "+27718693002",
                "Hello Mike"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipientCell() {
        Message message = new Message(
                "08575975889",
                "Hello Mike"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testMessageWithin250Characters() {
        Message message = new Message(
                "+27718693002",
                "Hello Mike"
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageExceeds250Characters() {
        String longMessage = "A".repeat(251);

        Message message = new Message(
                "+27718693002",
                longMessage
        );

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "+27718693002",
                "Hi tonight",
                "0012345678"
        );

        String hash = message.createMessageHash();

        assertTrue(hash.startsWith("00:"));
        assertTrue(hash.matches("00:[0-9]+:HITONIGHT"));
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                "+27718693002",
                "Hello Mike"
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage("1")
        );

        assertEquals("Sent", message.getStatus());
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                "+27718693002",
                "Hello Mike"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage("2")
        );

        assertEquals("Disregarded", message.getStatus());
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
                "+27718693002",
                "Hello Mike"
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage("3")
        );

        assertEquals("Stored", message.getStatus());
    }
}
