package partone;

import java.util.Scanner;

public class PartOne {

    public static void main(String[] args) {

        Registration register = new Registration();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your first name:");
        String firstName = scan.nextLine();

        System.out.println("Enter your last name:");
        String lastName = scan.nextLine();

        String username;
        do {
            System.out.println("Please enter a username:");
            username = scan.nextLine();

            if (!register.checkUserName(username)) {
                System.out.println("Username is not correctly formatted; "
                        + "please ensure that your username contains an "
                        + "underscore and is no more than five characters "
                        + "in length.");
            }
        } while (!register.checkUserName(username));

        System.out.println("Username successfully captured.");

        String password;
        do {
            System.out.println("Enter password:");
            password = scan.nextLine();

            if (!register.checkPasswordComplexity(password)) {
                System.out.println("Password is not correctly formatted; "
                        + "please ensure that your password contains at "
                        + "least eight characters, a capital letter, "
                        + "a number, and a special character.");
            }
        } while (!register.checkPasswordComplexity(password));

        System.out.println("Password successfully captured.");

        String cellPhone;
        do {
            System.out.println("Enter cell phone number:");
            cellPhone = scan.nextLine();

            if (!register.checkCellPhoneNumber(cellPhone)) {
                System.out.println("Cell phone number incorrectly "
                        + "formatted or does not contain international code.");
            }
        } while (!register.checkCellPhoneNumber(cellPhone));

        System.out.println("Cell phone number successfully added.");
        System.out.println(register.registerUser(username, password, cellPhone));

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("Login");
            System.out.println("Enter your username:");
            String loginUsername = scan.nextLine();

            System.out.println("Enter your password:");
            String loginPassword = scan.nextLine();

            loggedIn = register.loginUser(
                    loginUsername, loginPassword, username, password);

            if (!loggedIn) {
                System.out.println(
                        "Username or password incorrect, please try again.");
            }
        }

        System.out.println("Welcome " + firstName + ", " + lastName
                + " it is great to see you again.");

        System.out.println("Welcome to QuickChat.");

        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("Choose an option:");

            String option = scan.nextLine();

            switch (option) {
                case "1":
                    int numberOfMessages = 0;

                    while (numberOfMessages < 1) {
                        System.out.println(
                                "How many messages would you like to send?");
                        try {
                            numberOfMessages =
                                    Integer.parseInt(scan.nextLine());

                            if (numberOfMessages < 1) {
                                System.out.println(
                                        "Please enter at least 1 message.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(
                                    "Please enter a valid whole number.");
                        }
                    }

                    for (int i = 0; i < numberOfMessages; i++) {
                        System.out.println("\nMessage " + (i + 1));

                        System.out.println("Enter recipient cell number:");
                        String recipient = scan.nextLine();

                        System.out.println("Enter your message:");
                        String messageText = scan.nextLine();

                        Message message = new Message(recipient, messageText);

                        System.out.println(message.checkRecipientCell());
                        System.out.println(message.checkMessageLength());

                        if (!message.checkRecipientCell().equals(
                                "Cell phone number successfully captured.")
                                || messageText.length() > 250) {
                            System.out.println(
                                    "Message was not sent. Please correct the errors.");
                            continue;
                        }

                        System.out.println("Message Hash: "
                                + message.getMessageHash());

                        System.out.println(message.SentMessage());

                        if (message.getStatus().equals("Sent")) {
                            System.out.println(message);
                        }
                    }

                    System.out.println("Total messages sent: "
                            + new Message("+27718693002", "Counter")
                                    .returnTotalMessagess());
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select 1, 2, or 3.");
            }
        }
    }
}
