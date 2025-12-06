package com.ga.cmdbank;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Currency support: US Dollars.
 */
public interface IDebitcard {
    int cardId = 0; // auto generated unique identifier
    double depositLimitOwnAccountDaily = 0.0;
    double withdrawLimitDaily = 0.0;
    double transferLimitOwnAccountDaily = 0.0;
    double transferLimitOtherAccountDaily = 0.0;
    int cardIdPrefix = 0;
    Path filePath = Paths.get("data/system.txt");

    /**
     * Generate a new card ID with a set length and prefix number code based on the card. Increment from last saved card ID.
     * @param length int How many digits long the card number is.
     * @param cardIdPrefix int First 2 digits in the card number.
     * @return int Generated card ID
     */
    int generateCardId(int length, int cardIdPrefix);

    /**
     * Save the last generated card ID to its associated data file.
     * @param cardId int Card ID
     * @param filePath Path File path
     * @return int Last generated saved card ID
     */
    int saveLastGeneratedCardId(int cardId, Path filePath) throws IOException;

    /**
     * Get the last generated card id from stored data file.
     * @param filepath Data file.
     * @return int Card ID
     */
    int getLastGeneratedCardId(Path filepath) throws IOException;
}
