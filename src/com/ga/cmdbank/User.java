package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class User {

    String getCprInput() {
        return cprInput;
    }

    void setCprInput(String cprInput) {
        this.cprInput = cprInput.trim();
    }

    Integer getCpr() {
        return cpr;
    }

    void setCpr(Integer cpr) {
        this.cpr = cpr;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName.trim().toLowerCase();
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName.trim().toLowerCase();
    }

    String getUserRole() {
        return userRole;
    }

    void setUserRole(String userRole) {
        this.userRole = userRole.trim().toLowerCase();
    }

    String getHashedPassword() {
        return hashedPassword;
    }

    void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    byte[] getPasswordSalt() {
        return passwordSalt;
    }

    void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    String cprInput;
    Integer cpr;
    String firstName;
    String lastName;
    String userRole;
    String hashedPassword;
    byte[] passwordSalt;
    Path filePath = Paths.get("data/users.txt");

    /**
     * Default constructor.
     */
    public User() {

    }

    /**
     * Constructor for a new user account.
     * @param cprInput String user's CPR number. Unique user identifier and login ID.
     * @param firstName String user's first name.
     * @param lastName String user's last name.
     * @param userRole String user's role, 1 of 2 options: [banker, customer].
     * @param hashedPassword String User's hashed password
     * @param passwordSalt String User's password's unique salt value.
     */
    public User(String cprInput, String firstName, String lastName, String userRole, String hashedPassword, byte[] passwordSalt) {
        if (cprInput.isEmpty()) throw new RuntimeException("CPR cannot be empty.");
        this.setCprInput(cprInput);
        this.setCpr(convertCPRInput(cprInput));

        if (firstName.isEmpty()) throw new RuntimeException("First name cannot be empty.");
        this.setFirstName(firstName);

        if (lastName.isEmpty()) throw new RuntimeException("Last name cannot be empty.");
        this.setLastName(lastName);

        if (userRole.isEmpty()) throw new RuntimeException("User role cannot be empty.");
        this.setUserRole(userRole);

        this.setHashedPassword(hashedPassword);
        this.setPasswordSalt(passwordSalt);
    }

    /**
     * Check the validity of the entered CPR and convert it into a valid number.
     * @param cprInput String input of CPR number. Should be exactly 8 positive int digits only.
     * @return int
     */
    int convertCPRInput(String cprInput) {
        cprInput = cprInput.strip();
        int requiredCPRLength = 8;

        if (cprInput.length() != requiredCPRLength) throw new RuntimeException("Input must be exactly 8 digits only.");

        cprInput = cprInput.replaceAll("[^0-9]", ""); // remove all non-numeric characters from the string

        // Placed in Try/Catch to rethrow NumberFormatException error into a custom RuntimeException.
        try {
            int cpr = Integer.parseInt(cprInput);

            if (cprInput.length() != requiredCPRLength) throw new RuntimeException("Input must contain numbers only.");

            return cpr;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Input must contain numbers only.");
        }
    }

    /**
     * Check if a user exists based on CPR number. Returns true if it exists, otherwise false.
     * @param CPR int User CPR number
     * @return boolean
     * @throws IOException IOException
     */
    boolean exists(int CPR) throws IOException {
        String[] userData = read(CPR);

        return userData != null;
    }

    /**
     * Retrieve user's account data in users.txt file based on user CPR. Returns null if no matching user is found.
     * @param CPR User's cpr number and username.
     * @return String[] User's data array, in format: [cpr,firstname,lastname,accountRole,hashedPassword,passwordSalt]
     * @throws IOException Input reading error.
     */
    String[] read(int CPR) throws IOException {
        List<String> usersData = Files.readAllLines(filePath);

        for (String user : usersData) {
            String[] userData = user.split(";");
            int userCPR = convertCPRInput(userData[0]);

            if (userCPR == CPR) return userData;
        }

        return null;
    }
}