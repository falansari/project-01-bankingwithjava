package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Data file data row structure:
 * accountId;userCPR;accountType;cardId;cardType
 */
public class BankAccount {
    int bankAccountID;
    /**
     * CPR of the owner of the account customer/banker
     */
    int userCPR = 0;
    /**
     * Type of bank account
     */
    String accountType = null;
    /**
     * Types of bank accounts available.
     */
    String[] accountTypes = {"checking", "savings"};
    /**
     * Types of debit cards available.
     */
    String[] cardTypes = {"DebitMastercard", "DebitMastercardTitanium", "DebitMastercardPlatinum"};
    /**
     * Account's associated card's type: mastercard, mastercard titanium, or mastercard platinum.
     */
    String cardType = "";
    /**
     * ID of associated debit card.
     */
    int debitCardId = 0;
    /**
     * Data save file path for bank accounts.
     */
    final Path filepath = Paths.get("data/accounts.txt");
    final int accountIdPrefix = 100000;

    public BankAccount() {}

    public BankAccount(int accountId, int userCPR, String accountType, int cardId, String cardType) {
        this.bankAccountID = accountId;
        this.userCPR = userCPR;
        this.accountType = accountType;
        this.debitCardId = cardId;
        this.cardType = cardType;
    }

    /**
     * Create a new bank account for an existing user with a new associated debit card.
     * @param userCPR String The CPR number of the user account
     * @param accountType String The bank account type
     * @param cardType String The type of debit card the customer wants.
     * @return boolean True if account successfully created, false otherwise.
     */
    boolean createBankAccount(int userCPR, String accountType, String cardType) throws IOException {
        // Make sure user CPR exists before proceeding
        UserRead user = new UserRead();
        if(!user.exists(userCPR)) throw new RuntimeException("User with CPR " + userCPR + " does not exist.");

        // Check user is inputting a valid account type
        this.accountType = accountType.strip();
        // TODO: replace with List helper for cleaner code
        if (!this.accountType.equals(accountTypes[0]) && !this.accountType.equals(accountTypes[1]))
            throw new RuntimeException("Account type must be either " + accountTypes[0] + " or " + accountTypes[1]);

        // Check user is inputting a valid card type
        this.cardType = cardType.strip();
        // TODO: replace with List helper for cleaner code
        if(!this.cardType.equals(cardTypes[0]) && !this.cardType.equals(cardTypes[1]) && !this.cardType.equals(cardTypes[2]))
            throw new RuntimeException("Account type must be either " + cardTypes[0] + " or " + cardTypes[1] + " or " + cardTypes[2]);

        // TODO: Check user doesn't already have an account of the same type

        // TODO: Create debit card based on card type
        switch (cardType) {
            case "DebitMastercard":
                DebitMastercard card = new DebitMastercard();
                card.cardId = card.generateCardId();
                debitCardId = card.cardId;
                break;

            case "DebitMastercardTitanium":
                // TODO: Create titanium card
                break;

            case "DebitMastercardPlatinum":
                // TODO: Create platinum card
                break;

            default:
                throw new RuntimeException("Debit card must be either a regular mastercard, a titanium mastercard, or a platinum mastercard.");
        }

        this.bankAccountID = generateBankAccountId();
        this.userCPR = userCPR;
        this.accountType = accountType;
        this.cardType = cardType;
        System.out.println("account id: " + " cpr: " + " type: " + " card type: ");
        BankAccount bankAccount = new BankAccount(generateBankAccountId(), userCPR, accountType, debitCardId, cardType);
        String valueBreak = ";";
        String accountString = bankAccount.bankAccountID +
                valueBreak + bankAccount.userCPR +
                valueBreak + bankAccount.accountType +
                valueBreak + bankAccount.debitCardId +
                valueBreak + bankAccount.cardType;
        System.out.println("bank account: " + accountString);

        try {
            Files.writeString(filepath, accountString + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generate an account ID for a new bank account. Incremental ID generation.
     * @return int Generated bank account ID
     * @throws IOException Exception handling.
     */
    int generateBankAccountId() throws IOException {
        int lastGeneratedId = getLastGeneratedCardId();

        if (lastGeneratedId == 0) {
            bankAccountID = accountIdPrefix + 1;
        } else {
            bankAccountID = lastGeneratedId + 1;
        }

        return bankAccountID;
    }

    /**
     * Get the last generated card id from stored data file.
     *
     * @return int Card ID, or 0 if none found.
     */
    int getLastGeneratedCardId() throws IOException {
        List<String> data = getAccountsData();

        if (data.isEmpty()) return 0;

        String lastRow = data.get(data.size() - 1);
        String[] lastRowData = lastRow.split(";");

        return Integer.parseInt(lastRowData[0]);
    }

    /**
     * Get the data of all the bank accounts saved in accounts.txt.
     * @return List Accounts data, each row a different account.
     * @throws IOException IOException handling.
     */
    List<String> getAccountsData() throws IOException {

        return Files.readAllLines(filepath);
    }

    /**
     * Display the Create New User Command Line prompt.
     */
    void displayCreateAccount(Scanner inputScanner) {
        try {
            System.out.println("CREATE NEW BANK ACCOUNT");
            System.out.println(" ");

            System.out.print("Customer CPR Number: ");
            String cpr = inputScanner.nextLine().strip();
            UserRead user = new UserRead();
            userCPR = user.convertCPRInput(cpr);
            System.out.println(" ");

            System.out.print("Account Type ([C] Checking Account / [S] Savings Account): ");
            char choice = inputScanner.next().strip().toUpperCase().charAt(0);
            switch (choice) {
                case 'C':
                    accountType = accountTypes[0];
                    break;
                case 'S':
                    accountType = accountTypes[1];
                    break;
                default:
                    throw new RuntimeException("Please type in C for a checking account or S for a savings account");
            }
            System.out.println(" ");

            System.out.print("Card Type ([M] Debit Mastercard [T] Debit Mastercard Titanium [P] Debit Mastercard Platinum): ");
            choice = inputScanner.next().strip().toUpperCase().charAt(0);
            switch (choice) {
                case 'M':
                    cardType = cardTypes[0];
                    break;
                case 'T':
                    cardType = cardTypes[1];
                    break;
                case 'P':
                    cardType = cardTypes[2];
                    break;
                default:
                    throw new RuntimeException("Please type in M / T / P for choice of card type");
            }
            System.out.println(" ");

            System.out.println("Creating new bank account...");
            if (createBankAccount(userCPR, accountType, cardType)) {
                System.out.println("New bank account successfully created.");
            } else {
                System.out.println("New bank account creation failed, please try again.");
                displayCreateAccount(inputScanner);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + " SOURCE: " + Arrays.toString(e.getStackTrace()));
            System.out.println(" ");
            displayCreateAccount(inputScanner);
        }
    }
}