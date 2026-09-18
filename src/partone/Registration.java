package partone;

public class Registration {

    public boolean checkUserName(String username) {

        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkPasswordComplexity(String password) {

        if (password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[^a-zA-Z0-9].*")) {

            return true;

        } else {
            return false;
        }

    }

    public boolean checkCellPhoneNumber(String cellPhone) {

        String regex = "^\\+27[0-9]{9}$";

        if (cellPhone.matches(regex)) {
            return true;
        } else {
            return false;
        }

    }

    public String registerUser(String username, String password, String cellPhone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Registration successful.";
    }

    public boolean loginUser(String username, String password,
            String registeredUsername, String registeredPassword) {

        return username.equals(registeredUsername)
                && password.equals(registeredPassword);
    }
}