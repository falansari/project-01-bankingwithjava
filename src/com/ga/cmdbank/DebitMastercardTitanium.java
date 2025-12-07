package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DebitMastercardTitanium implements IDebitcard {
    int cardId = 0;
    final double depositLimitDaily = 200_000.0;
    final double withdrawLimitDaily = 10_000.0;
    final double transferLimitOwnAccountDaily = 40_000.0;
    final double transferLimitOtherAccountDaily = 20_000.0;
    final int cardIdPrefix = 530000000;
    final Path filepath = Paths.get("data/system.txt"); // Where system data is stored
    final String systemDataRowPrefix = "debit_mastercardTitanium_lastGenerated";

    public DebitMastercardTitanium() {}

    public DebitMastercardTitanium(int cardId) {
        this.cardId = cardId;
    }

    /**
     * Generate a new card ID with a set length and prefix number code based on the card.
     * Increment from last saved card ID.
     * Updates system.txt's last generated card field.
     *
     * @return int Generated card ID
     */
    @Override
    public int generateCardId() throws IOException {
        int lastGeneratedCardId = getLastGeneratedCardId(filepath);

        if (lastGeneratedCardId == 0) {
            cardId = cardIdPrefix + 1;
        } else {
            cardId = lastGeneratedCardId + 1;
        }

        saveLastGeneratedCardId(cardId, filepath);

        return cardId;
    }

    /**
     * Save the last generated card ID to its associated data file.
     *
     * @param cardId   String Card ID
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
                Files.write(filePath, systemData);

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

                return Integer.parseInt(row.substring(separatorIndex + 1));
            }
        }

        return 0;
    }
}