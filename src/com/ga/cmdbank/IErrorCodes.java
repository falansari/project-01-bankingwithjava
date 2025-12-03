package com.ga.cmdbank;

/**
 * List of universal error codes and what they mean.
 * <p>
 *     Error Codes:
 *     404: not found
 * </p>
 */
public interface IErrorCodes {

    /**
     * Throw a runtime exception with a relevant error message based on error code.
     * @param code
     */
    static void throwError(int code) {
        switch (code) {
            case 404:
                throw new RuntimeException("Resource not found");
        }
    }

    /**
     * Get the error message of a specific error code.
     * @param code error code.
     * @return String error message.
     */
    static String getErrorMessage(int code) {
        String message = "";

        switch (code) {
            case 404:
                message = "not found";
        }

        return message;
    }
}
