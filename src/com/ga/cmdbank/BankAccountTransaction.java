package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class BankAccountTransaction extends BankAccount {
    /** DEPOSIT
     * Customer Can:
     * TODO: Choose account to deposit into
     * TODO: See amount currently in the account as they choose how much to deposit
     * TODO: Choose preset deposit amount or enter custom amount
     * TODO: Deposit amount that does not exceed their daily deposit limit based on their attached card.
     * TODO: See new balance after deposit. Transaction ends.
     * Banker Can:
     * TODO: Deposit for a customer or their own accounts
     */

    /**
     * Deposit money amount into specified bank account. Returns true if successful, otherwise false.
     * @param bankAccount Object bank Account to deposit into
     * @param amount double Amount of money in USD to deposit
     * @return boolean
     * @throws IOException Exception handling
     */
    boolean deposit(BankAccount bankAccount, double amount) throws IOException {
        List<String> accountsData = Files.readAllLines(filepath);

        for (int _i = 0; _i < accountsData.size(); _i++) {
            String row = accountsData.get(_i);

            if (row.startsWith(String.valueOf(bankAccount.bankAccountID))) { // Update account record balance
                String[] rowData = row.split(";");
                double sum = Double.parseDouble(rowData[5]) + amount;
                String newRowData = rowData[0] + ";"
                        + rowData[1] + ";"
                        + rowData[2] + ";"
                        + rowData[3] + ";"
                        + rowData[4] + ";"
                        + sum;

                accountsData.set(_i, newRowData);
                Files.write(filepath, accountsData);

                // TODO: Add a transaction in a transaction history file
                return true;
            }
        }

        return false;
    }

    /**
     * Display deposit into account balance menu
     * @param inputScanner Scanner
     * @param user Object
     * @throws IOException Input exception handling
     */
    void displayDeposit(Scanner inputScanner, UserRead user) throws IOException {
        System.out.println("DEPOSIT INTO ACCOUNT:");
        System.out.print("Account ID: ");
        int accountId = Integer.parseInt(inputScanner.nextLine().strip());
        BankAccount account = getAccount(accountId);

        // Check user has authorization to deposit into this account
        if (String.valueOf(user.userRole).equals("customer")) {
            if (account.userCPR != user.cpr) {
                System.out.println("You are not authorized to deposit into another person's account.");
                displayDeposit(inputScanner, user);
            }
        }

        System.out.println(" ");

        System.out.print("Deposit Amount (USD): ");
        double amount = Double.parseDouble(inputScanner.nextLine().strip());

        try {
            // Check deposit amount does not exceed their card's limit
            switch (account.cardType) {
                case "DebitMastercard":
                    DebitMastercard debitMastercard = new DebitMastercard(account.debitCardId);
                    if (amount > debitMastercard.depositLimitDaily)
                        throw new IOException("You cannot deposit more than your card's daily limit of $" + debitMastercard.depositLimitDaily);
                    break;

                case "DebitMastercardTitanium":
                    DebitMastercardTitanium debitMastercardTitanium = new DebitMastercardTitanium(account.debitCardId);
                    if (amount > debitMastercardTitanium.depositLimitDaily)
                        throw new IOException("You cannot deposit more than your card's daily limit of $" + debitMastercardTitanium.depositLimitDaily);
                    break;

                case "DebitMastercardPlatinum":
                    DebitMastercardPlatinum debitMastercardPlatinum = new DebitMastercardPlatinum(account.debitCardId);
                    if (amount > debitMastercardPlatinum.depositLimitDaily)
                        throw new IOException("You cannot deposit more than your card's daily limit of $" + debitMastercardPlatinum.depositLimitDaily);
                    break;
            }

            // TODO: Add transaction in transaction history
            // TODO: Deposit amount limit should add up all transactions done on the same day for this account

            if (deposit(account, amount)) {
                System.out.println("Amount of $" + amount + " successfully deposited.");
                System.out.println(" ");
                System.out.println("New Account Balance: $" + (account.balance + amount));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            displayDeposit(inputScanner, user);
        }
    }
}
