package partone;

import java.util.Scanner;

public class PartOne {

    public static void main(String[] args) {

        Registration register = new Registration();
        Scanner scan = new Scanner(System.in);

        // First name and last name
        System.out.println("Enter your first name:");
        String firstName = scan.nextLine();

        System.out.println("Enter your last name:");
        String lastName = scan.nextLine();

        // Username input and check
        System.out.println("Please enter the username:");
        String name = scan.nextLine();

        if (register.checkUserName(name)) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        // Password input and check
        System.out.println("Enter password:");
        String password = scan.nextLine();

        if (register.checkPasswordComplexity(password)) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        // Cell phone input and check
        System.out.println("Enter cell phone number:");
        String cellPhone = scan.nextLine();

        if (register.checkCellPhoneNumber(cellPhone)) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        }

        // Registration
        String registrationStatus = register.registerUser(name, password, cellPhone);
        System.out.println(registrationStatus);

        // Login
        System.out.println("Login");
        System.out.println("Enter your username:");
        String loginUsername = scan.nextLine();

        System.out.println("Enter your password:");
        String loginPassword = scan.nextLine();

        if (register.loginUser(loginUsername, loginPassword, name, password)) {

            System.out.println("Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.");

        } else {

            System.out.println("Username or password incorrect, please try again.");
        }
    }
}