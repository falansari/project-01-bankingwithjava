package com.ga.cmdbank;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * General purpose components
 */
public class UtilityComponent {

    /**
     * Convert local datetime string to local date time
     * @param datetimeString LocalDateTime string
     * @return LocalDateTime
     */
    public LocalDateTime convertStringToDateTime(String datetimeString) {
        try {
            return LocalDateTime.parse(datetimeString);

        } catch (RuntimeException e) {
            throw new DateTimeException("String is not a valid local date time format");        }
    }

    /**
     * Get local date from local date time string
     * @param datetimeString LocalDateTime string
     * @return LocalDate
     */
    public LocalDate getDateFromDatetime(String datetimeString) {
        try {
            LocalDateTime localDateTime = convertStringToDateTime(datetimeString);

            return getDateFromDatetime(localDateTime);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error parsing datetime string to local date. Make sure the string is a valid local datetime format.");
        }
    }

    /**
     * Get local date from local datetime value
     * @param dateTime Local date time
     * @return LocalDate
     */
    public LocalDate getDateFromDatetime(LocalDateTime dateTime) {
        try {
            return dateTime.toLocalDate();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error parsing local datetime to local date");
        }
    }

    /**
     * Get today local date.
     * @return LocalDate now()
     */
    public LocalDate getTodayDate() {
        return LocalDateTime.now().toLocalDate();
    }

    /**
     * Pad and center the string to enforce a specific length.
     * @param input String
     * @param length int    New string length
     * @return String Padded string
     */
    public String padString(String input, int length) {
        if (input.length() >= length) return input;

        int totalPadding = length - input.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return " ".repeat(leftPadding) + input +" ".repeat(Math.max(0, rightPadding));
    }
}
