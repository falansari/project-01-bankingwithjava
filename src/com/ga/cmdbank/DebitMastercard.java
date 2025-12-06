package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DebitMastercard implements IDebitcard {
    int cardId = 0;
    final double depositLimitDaily = 200_000.0;
    final double withdrawLimitDaily = 5_000.0;
    final double transferLimitOwnAccountDaily = 20_000.0;
    final double transferLimitOtherAccountDaily = 10_000.0;
    final int cardIdPrefix = 51;
    final Path filepath = Paths.get("data/system.txt"); // Where system data is stored
    final String systemDataRowPrefix = "debit_mastercard_lastGenerated";

    /**
     * Generate a new card ID with a set length and prefix number code based on the card. Increment from last saved card ID.
     *
     * @param length int How many digits long the card number is.
     * @param cardIdPrefix int First 2 digits in the card number.
     * @return int Generated card ID
     */
    @Override
    public int generateCardId(int length, int cardIdPrefix) {


        return 0;
    }

    /**
     * Save the last generated card ID to its associated data file.
     *
     * @param cardId   int Card ID
     * @param filePath Path File path
     * @return int Saved Last Generated Card ID
     */
    @Override
    public int saveLastGeneratedCardId(int cardId, Path filePath) throws IOException {
        List<String> systemData = Files.readAllLines(filePath);
        String newRowData = systemDataRowPrefix + ":" + cardId;

        for (int _i = 0; _i < systemData.size(); _i++ ) {
            String row = systemData.get(_i);

            if (row.startsWith(systemDataRowPrefix)) {
                // New row contents
                systemData.set(_i, newRowData);

                return cardId;
            }
        }

        // If a matching row was not found, create one.
        Files.writeString(filePath, newRowData + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return cardId;
    }

    /**
     * Get the last generated card id from stored data file.
     *
     * @param filepath Data file.
     * @return int Card ID, or 0 if none found.
     */
    @Override
    public int getLastGeneratedCardId(Path filepath) throws IOException {
        List<String> systemData = Files.readAllLines(filePath);

        for (String row : systemData) {
            if (row.startsWith(systemDataRowPrefix)) {
                int separatorIndex = row.indexOf(":");

                return Integer.parseInt(row.substring(separatorIndex));
            }
        }

        return 0;
    }
}
