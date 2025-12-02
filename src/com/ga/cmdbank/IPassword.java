package com.ga.cmdbank;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Password hashing, salting, and verification.
 */
public interface IPassword {
    /**
     * Generate a randomized password salt with a default length 16.
     * @return byte[] randomly generated salt
     */
    static byte[] generateSalt() {
        return generateSalt(16);
    }

    /**
     * Generate a randomized password salt.
     * @param length Length of salt values to generate.
     * @return byte[] randomly generated salt
     */
    static byte[] generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);

        return salt;
    }

    /**
     * Generate and return a hashed version of the password using PBKDF2 algorithm.
     * @param plainPassword String  User's unencrypted password.
     * @param salt byte[]   Generated salt value.
     * @return String   User's encrypted password.
     * @throws NoSuchAlgorithmException Exception   Algorithm not found exception.
     * @throws InvalidKeySpecException Exception    Invalid specification key exception.
     */
    static String hashPassword(String plainPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt, 100_000, 256); // Hardcoding the iterations and key length to not cause problems in a test app. Not good practice for real life though.
        SecretKeyFactory hashingAlgorithm = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = hashingAlgorithm.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verify the supplied password matches the (hashed) password stored in database.
     * @param plainPassword String User's password input.
     * @param storedHash String Database stored hashed password.
     * @param storedSalt byte[] Database stored salt.
     * @return boolean
     * @throws NoSuchAlgorithmException Exception   Algorithm not found exception.
     * @throws InvalidKeySpecException Exception    Invalid specification key exception.
     */
    static boolean verifyPassword(String plainPassword, String storedHash, byte[] storedSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String newHash = hashPassword(plainPassword, storedSalt);

        return newHash.equals(storedHash);
    }
}
