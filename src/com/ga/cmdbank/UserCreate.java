package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

/**
 * Create new users. Privilege allowed to banker accounts only.
 */
public class UserCreate extends User implements IPassword {
    /**
     * Default constructor to access class methods.
     */
    public UserCreate() {}

    /**
     * New user constructor.
     * @param cprInput String user's CPR number. Unique user identifier and login ID.
     * @param firstName String user's first name.
     * @param lastName String user's last name.
     * @param userRole String user's role, 1 of 2 options: [banker, customer].
     * @param hashedPassword String User's hashed password
     * @param passwordSalt String User's password's unique salt value.
     */
    public UserCreate(String cprInput, String firstName, String lastName, String userRole, String hashedPassword, byte[] passwordSalt) {
        super(cprInput, firstName, lastName, userRole, hashedPassword, passwordSalt);
    }

    /**
     * Display the Create New User Command Line prompt.
     */
    void display(Scanner inputScanner) {
        try {

            System.out.println("CREATE NEW USER ACCOUNT");
            System.out.println(" ");
            System.out.print("CPR Number: ");
            String cpr = inputScanner.nextLine();

            System.out.println(" ");
            System.out.print("First Name: ");
            String firstName = inputScanner.nextLine();
            System.out.println(" ");

            System.out.print("Last Name: ");
            String lastName = inputScanner.nextLine();
            System.out.println(" ");

            System.out.print("User Type ([B] banker/ [C] customer): ");
            userRole = inputScanner.nextLine();
            switch (userRole.toUpperCase()) {
                case "B":
                    userRole = "banker";
                    break;
                case "C":
                default:
                    userRole = "customer";
                    break;
            }
            System.out.println(" ");

            System.out.println("Creating new user account...");
            if (save(cpr, firstName, lastName, userRole)) {
                System.out.println("New user account successfully created.");
            } else {
                System.out.println("New user account creation failed. Please try again.");
                display(inputScanner);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(" ");
            display(inputScanner);
        }
    }

    /**
     * Create a new user account and save it to users.txt data file.
     * @param cpr String user's CPR number. Unique user identifier and login ID.
     * @param firstName String user's first name.
     * @param lastName String user's last name.
     * @param userRole String user's role, 1 of 2 options: [banker, customer].
     * @return boolean
     */
    boolean save(String cpr, String firstName, String lastName, String userRole) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] passwordSalt = IPassword.generateSalt();
        String hashedPassword = IPassword.hashPassword(cpr, passwordSalt);
        User user = new UserCreate(cpr, firstName, lastName, userRole, hashedPassword, passwordSalt);
        String valueBreak = ";";
        String userString = user.cpr + valueBreak + user.firstName + valueBreak + user.lastName + valueBreak + user.userRole + valueBreak + user.hashedPassword + valueBreak + IPassword.base64Salt(user.passwordSalt);

        try {
            int cprNumber = convertCPRInput(cpr);
            if (exists(cprNumber)) {
                throw new IOException("User with CPR " + cpr + " already exists.");
            }
        } catch (NumberFormatException e) {
            throw new IOException("Invalid CPR format: " + cpr);
        }

        try {
            Files.writeString(filePath, userString + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());

            return false;
        }
    }
}