package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/**
 * Record, read or search through transaction history.
 */
public class TransactionHistory {
    int userId;
    int accountId;
    LocalDateTime dateTime;
    String transactionType;
    double transactionAmount;
    int receivedFromAccountId; // Optional field: only for transfer from other account transactions
    final Path filePath = Paths.get("data/transaction_history.txt");

    public TransactionHistory(int userId, int accountId, String transactionType, double transactionAmount) {
        this.userId = userId;
        this.accountId = accountId;
        this.dateTime = LocalDateTime.now();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public TransactionHistory(int userId, int accountId, String transactionType, double transactionAmount, int receivedFromAccountId) {
        this.userId = userId;
        this.accountId = accountId;
        this.dateTime = LocalDateTime.now();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.receivedFromAccountId = receivedFromAccountId;
    }

    /**
     * Save a transaction into transaction_history.txt file.
     */
    void recordTransaction() {
        String elementBreak = ";";
        String record = userId + elementBreak
                + accountId + elementBreak
                + dateTime + elementBreak
                + transactionType + elementBreak
                + transactionAmount + elementBreak
                + receivedFromAccountId;

        try {
            Files.writeString(filePath, record + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());

        }
    }
}
