package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

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
        super(cprInput.trim(), firstName.trim().toLowerCase(), lastName.trim().toLowerCase(), userRole.trim(), hashedPassword, passwordSalt);
    }

    /**
     * Display the Create New User prompt.
     */
    void display() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("CREATE NEW USER ACCOUNT");
            System.out.println(" ");
            System.out.print("CPR Number: ");
            String cpr = scanner.nextLine();

            System.out.println(" ");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.println(" ");

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.println(" ");

            System.out.print("User Type ([B] banker/ [C] customer): ");
            userRole = scanner.nextLine();
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
                display();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(" ");
            display();
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
    boolean save(String cpr, String firstName, String lastName, String userRole) throws IOException {
        User user = new UserCreate(cpr, firstName, lastName, userRole, cpr, IPassword.generateSalt());
        String valueBreak = ";";
        String userString = user.cpr + valueBreak + user.firstName + valueBreak + user.lastName + valueBreak + user.userRole + valueBreak + user.hashedPassword + valueBreak + user.passwordSalt;

        if (exists(Integer.parseInt(cpr))) {
            throw new IOException("User with CPR " + cpr + " already exists.");
        }

        try {
            if (Files.exists(filePath)) {
                Files.writeString(filePath, "\n" + userString, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            } else {
                Files.writeString(filePath, userString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
            System.out.println("Data saved to users.txt");
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}
