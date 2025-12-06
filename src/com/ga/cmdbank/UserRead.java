package com.ga.cmdbank;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Read user data, including login.
 */
public class UserRead extends User implements IPassword {

    public UserRead() {}

    /**
     * Read existing user constructor.
     * @param cprInput String user's CPR number. Unique user identifier and login ID.
     * @param firstName String user's first name.
     * @param lastName String user's last name.
     * @param userRole String user's role, 1 of 2 options: [banker, customer].
     */
    public UserRead(String cprInput, String firstName, String lastName, String userRole) {
        super(cprInput, firstName, lastName, userRole);
    }

    /**
     * Read existing constructor.
     * @param cprInput String user's CPR number. Unique user identifier and login ID.
     * @param firstName String user's first name.
     * @param lastName String user's last name.
     * @param userRole String user's role, 1 of 2 options: [banker, customer].
     * @param hashedPassword String User's hashed password
     * @param passwordSalt String User's password's unique salt value.
     */
    public UserRead(String cprInput, String firstName, String lastName, String userRole, String hashedPassword, byte[] passwordSalt) {
        super(cprInput, firstName, lastName, userRole, hashedPassword, passwordSalt);
    }

    /**
     * Login an existing user to the system.
     * @param username int User's username, default is their CPR number.
     * @param password  String  User's supplied password, must be verified first before allowing login.
     * @return String[] User's stored data in users.txt file. Format: {cpr,firstName,lastName,accountRole,hashedPassword,passwordSalt}
     */
    UserRead login(int username, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (!exists(username)) throw new IOException("A user with username " + username + " does not exist.");

        String[] userData = read(username);
        String userStoredHash = userData[4];
        byte[] userStoredSalt = IPassword.decodeBase64Salt(userData[5]);

        if (!IPassword.verifyPassword(password, userStoredHash, userStoredSalt)) throw new IOException("Password does not match.");

        return new UserRead(userData[0], userData[1], userData[2], userData[3]);
    }

    /**
     * Display user login prompt.
     */
    UserRead display() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Welcome to CMD-BANK");
            System.out.println("Please Login to your user account:");
            System.out.print("CPR Number: ");
            String cprInput = scanner.nextLine();
            System.out.print("Password: ");
            String passwordInput = scanner.nextLine();

            return login(convertCPRInput(cprInput), passwordInput);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(" ");
            display();
        }

        return null;
    }

    /**
     * Display banker's main menu options.
     * @param userRead Object
     */
    void displayMainMenuBanker(UserRead userRead, Scanner inputScanner) {
        if (!Objects.equals(userRead.getUserRole(), "banker")) throw new RuntimeException("This menu may only display for a banker.");

        System.out.println("Welcome, " + userRead.getFirstName() + " " + userRead.getLastName());
        System.out.println("What would you like to do today?");
        System.out.println("(C) Create new customer");
        System.out.println("(B) Create new customer bank account");
        System.out.println("(V) View customer's account data");
        System.out.println("(A) Access my own bank accounts");
        System.out.println("(E) Exit System");
        System.out.print("Choice (Type the letter associated with the option): ");
        String choice = inputScanner.nextLine().strip();

        switch (choice.toLowerCase()) {
            case "c":
                System.out.println("create new customer account");
                break;

            case "b":
                BankAccount bankAccount = new BankAccount();
                bankAccount.displayCreateAccount(inputScanner);
                break;

            case "v":
                System.out.println("View customer's account details");
                break;

            case "a":
                System.out.println("Access own bank accounts details");
                break;

            case "e":
                System.out.println("Thank you for coming today! Goodbye.");
                break;

            default:
                System.out.println("Please type in the letter corresponding to 1 of the choices only.");
                displayMainMenuBanker(userRead, inputScanner);
                break;
        }
    }

    /**
     * Display customer's main menu options.
     * @param userRead Object
     */
    void displayMainMenuCustomer(UserRead userRead, Scanner inputScanner) {
        System.out.println("Welcome, " + userRead.getFirstName() + " " + userRead.getLastName());
        System.out.println("What would you like to do today?");
        System.out.println("(V) View Bank Account Details");
        System.out.println("(W) Withdraw Money");
        System.out.println("(D) Deposit Money");
        System.out.println("(T) Transfer Money");
        System.out.println("(E) Exit System");
        System.out.print("Choice (Type the letter associated with the option): ");
        String choice = inputScanner.nextLine();

        switch (choice.toLowerCase()) {
            case "v":
                System.out.println("View account");
                break;

            case "w":
                System.out.println("withdraw");
                break;

            case "d":
                System.out.println("deposit");
                break;

            case "t":
                System.out.println("transfer");
                break;

            case "e":
                System.out.println("Thank you for coming today! Goodbye.");
                break;

            default:
                System.out.println("Please type in the letter corresponding to 1 of the choices only.");
                displayMainMenuCustomer(userRead, inputScanner);
                break;
        }
    }
}
