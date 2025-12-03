package com.ga.cmdbank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class UserCreateTest {

    UserCreate user;
    UserCreate userWithParameters;
    byte[] salt;

    @BeforeEach
    void setUp() {
        user = new UserCreate();
        salt = new byte[]{1, 2, 3};
        userWithParameters = new UserCreate("11111111", "John", "Doe", "banker", "hashedPassword", salt);
    }

    @Test
    void getCprInput() {
        assertEquals("11111111", userWithParameters.getCprInput());
    }

    @Test
    void setCprInput() {
        user.setCprInput("66666666");
        assertEquals("66666666", user.getCprInput());
    }

    @Test
    void getCpr() {
        assertEquals(11111111, userWithParameters.getCpr());
    }

    @Test
    void setCpr() {
        user.setCpr(66666666);
        assertEquals(66666666, user.getCpr());
    }

    @Test
    void getFirstName() {
        assertEquals("john", userWithParameters.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("Jane");
        assertEquals("jane", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("doe", userWithParameters.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("Doe");
        assertEquals("doe", user.getLastName());
    }

    @Test
    void getUserRole() {
        assertEquals("banker", userWithParameters.getUserRole());
    }

    @Test
    void setUserRole() {
        user.setUserRole("customer");
        assertEquals("customer", user.getUserRole());
    }

    @Test
    void getHashedPassword() {
        assertEquals("hashedPassword", userWithParameters.getHashedPassword());
    }

    @Test
    void setHashedPassword() {
        user.setHashedPassword("hashedPassword");
        assertEquals("hashedPassword", user.getHashedPassword());
    }

    @Test
    void getPasswordSalt() {
        assertArrayEquals(salt, userWithParameters.getPasswordSalt());
    }

    @Test
    void setPasswordSalt() {
        user.setPasswordSalt(salt);
        assertArrayEquals(salt, user.getPasswordSalt());
    }

    @Test
    void convertCPRInput() {
        assertEquals(11111111, user.convertCPRInput("11111111")); // valid input

        // Test for invalid input
        assertThrows(RuntimeException.class, () -> user.convertCPRInput("123"), "Exception should be thrown for wrong input length");
        assertThrows(RuntimeException.class, () -> user.convertCPRInput("DSFG@#12"), "Exception should be thrown for non-numeric characters in input");
    }

    @Test
    void exists() throws IOException {
        assertTrue(user.exists(11111111), "User should exist");
        assertFalse(user.exists(10000000), "User should not exist");
    }

    @Test
    void read() throws IOException {
        assertNotNull(user.read(11111111), "Read return should be array of user data");
        assertNull(user.read(10000000), "Read return should be null");
    }

    @Test
    void generateSalt() {
        byte[] newSalt = IPassword.generateSalt();
        assertEquals(16, newSalt.length, "Generated salt byte array should contain 16 elements");
    }

    @Test
    void base64Salt() {
        String base64Salt = IPassword.base64Salt(salt);
        assertDoesNotThrow(() -> Base64.getDecoder().decode(base64Salt), "The result should be a base64 encode.");
    }

    @Test
    void decodeBase64Salt() {
        assertDoesNotThrow(() -> Base64.getEncoder().encode(salt), "The result should be a decoded byte[] array");
    }

    @Test
    void hashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Check hashing is always same result for same password and salt
        assertEquals(IPassword.hashPassword("password", salt), IPassword.hashPassword("password", salt));
        // Check hashing is different result for same password different salt
        assertNotEquals(IPassword.hashPassword("password", salt), IPassword.hashPassword("password", IPassword.generateSalt()));
    }
}