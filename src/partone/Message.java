package partone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    private String status = "Pending";

    public Message(String recipient, String messageText) {
        this(recipient, messageText, generateMessageID());
    }

    // Constructor also allows predictable IDs in unit tests.
    public Message(String recipient, String messageText, String messageID) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = messageID;
        this.messageNumber = messageCounter++;
        this.messageHash = createMessageHash();
    }

    private static String generateMessageID() {
        long number = 1_000_000_000L
                + new Random().nextInt(9) * 1_000_000_000L
                + new Random().nextInt(1_000_000_000);

        return String.format("%010d", number);
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("[0-9]{1,10}");
    }

    public String checkRecipientCell() {
        // South African international format: +27 followed by 9 digits.
        if (recipient != null && recipient.matches("\\+27[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText == null || messageText.length() > 250) {
            int length = messageText == null ? 0 : messageText.length();
            return "Message exceeds 250 characters by "
                    + Math.max(0, length - 250)
                    + "; please reduce the size.";
        }

        return "Message ready to send.";
    }

    public String createMessageHash() {
        String firstTwoDigits = messageID != null
                && messageID.length() >= 2
                ? messageID.substring(0, 2) : "00";

        String[] words = messageText == null
                ? new String[0]
                : messageText.trim().split("\\s+");

        String firstWord = words.length > 0
                ? words[0].replaceAll("[^a-zA-Z0-9]", "") : "";

        String lastWord = words.length > 0
                ? words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "") : "";

        return (firstTwoDigits + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        String choice = scanner.nextLine();
        return SentMessage(choice);
    }

    public String SentMessage(String choice) {
        switch (choice) {
            case "1":
                status = "Sent";
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";

            case "2":
                status = "Disregarded";
                return "Press 0 to delete the message.";

            case "3":
                status = "Stored";
                storedMessages.add(this);
                try {
                    storeMessage();
                    return "Message successfully stored.";
                } catch (IOException e) {
                    return "Message could not be stored: " + e.getMessage();
                }

            default:
                return "Invalid option. Please select 1, 2, or 3.";
        }
    }

    public String printMessages() {
        StringBuilder result = new StringBuilder();

        for (Message message : sentMessages) {
            result.append(message.toString()).append("\n");
        }

        return result.toString();
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public void storeMessage() throws IOException {
        Path path = Path.of("messages.json");
        StringBuilder json = new StringBuilder("[\n");

        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);

            json.append("  {\n")
                .append("    \"messageID\": \"").append(escapeJson(m.messageID)).append("\",\n")
                .append("    \"messageHash\": \"").append(escapeJson(m.messageHash)).append("\",\n")
                .append("    \"recipient\": \"").append(escapeJson(m.recipient)).append("\",\n")
                .append("    \"message\": \"").append(escapeJson(m.messageText)).append("\",\n")
                .append("    \"status\": \"").append(escapeJson(m.status)).append("\"\n")
                .append("  }");

            if (i < storedMessages.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("]\n");

        Files.writeString(path, json.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    @Override
    public String toString() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getStatus() {
        return status;
    }
}
