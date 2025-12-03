package com.ga.cmdbank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * User class unit test
 * Generated with help from Copilot.
 */
class UserTest {

    // Dummy subclass for testing since User is abstract
    static class TestUser extends User {
        public TestUser(String cprInput, String firstName, String lastName,
                        String userRole, String hashedPassword, byte[] passwordSalt) {
            super(cprInput, firstName, lastName, userRole, hashedPassword, passwordSalt);
        }
    }

    // -------------------------
    // Constructor & Getter Tests
    // -------------------------

    @Test
    void testValidConstructorAndGetters() {
        byte[] salt = {1, 2, 3};
        User user = new TestUser("12345678", "Alice", "Smith", "customer", "hashedPass", salt);

        assertEquals("12345678", user.getCprInput());
        assertEquals(12345678, user.getCpr());
        assertEquals("Alice", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("customer", user.getUserRole());
        assertEquals("hashedPass", user.getHashedPassword());
        assertArrayEquals(salt, user.getPasswordSalt());
    }

    @Test
    void testEmptyCprThrowsException() {
        byte[] salt = {1};
        Exception ex = assertThrows(RuntimeException.class,
                () -> new TestUser("", "Alice", "Smith", "customer", "hash", salt));
        assertTrue(ex.getMessage().contains("CPR cannot be empty"));
    }

    @Test
    void testInvalidCprLengthThrowsException() {
        byte[] salt = {1};
        Exception ex = assertThrows(RuntimeException.class,
                () -> new TestUser("1234", "Alice", "Smith", "customer", "hash", salt));
        assertTrue(ex.getMessage().contains("Input must be exactly 8 digits"));
    }

    @Test
    void testNonNumericCprThrowsException() {
        byte[] salt = {1};
        Exception ex = assertThrows(RuntimeException.class,
                () -> new TestUser("ABCDEFGH", "Alice", "Smith", "customer", "hash", salt));
        assertTrue(ex.getMessage().contains("Input must contain numbers only"));
    }

    @Test
    void testEmptyFirstNameThrowsException() {
        byte[] salt = {1};
        Exception ex = assertThrows(RuntimeException.class,
                () -> new TestUser("12345678", "", "Smith", "customer", "hash", salt));
        assertTrue(ex.getMessage().contains("First name cannot be empty"));
    }

    @Test
    void testEmptyLastNameThrowsException() {
        byte[] salt = {1};
        Exception ex = assertThrows(RuntimeException.class,
                () -> new TestUser("12345678", "Alice", "", "customer", "hash", salt));
        assertTrue(ex.getMessage().contains("Last name cannot be empty"));
    }

    @Test
    void testEmptyUserRoleThrowsException() {
        byte[] salt = {1};
        Exception ex = assertThrows(RuntimeException.class,
                () -> new TestUser("12345678", "Alice", "Smith", "", "hash", salt));
        assertTrue(ex.getMessage().contains("User role cannot be empty"));
    }

    // -------------------------
    // CPR Conversion Tests
    // -------------------------

    @Test
    void testConvertCPRInputValid() {
        User user = new TestUser("12345678", "Alice", "Smith", "customer", "hash", new byte[]{1});
        assertEquals(12345678, user.convertCPRInput("12345678"));
    }

    @Test
    void testConvertCPRInputInvalidLength() {
        User user = new TestUser("12345678", "Alice", "Smith", "customer", "hash", new byte[]{1});
        assertThrows(RuntimeException.class, () -> user.convertCPRInput("123"));
    }

    // -------------------------
    // File-based Exists Tests
    // -------------------------

    @Test
    void testExistsReturnsTrueWhenUserFound(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("users.txt");
        String fakeUser = "12345678;Alice;Smith;customer;hashedPass;[1,2,3]";
        Files.writeString(testFile, fakeUser);

        TestUser user = new TestUser("12345678", "Alice", "Smith", "customer", "hashedPass", new byte[]{1,2,3});
        user.filePath = testFile; // override default path

        assertTrue(user.exists(12345678), "User should exist in the file");
    }

    @Test
    void testExistsReturnsFalseWhenUserNotFound(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("users.txt");
        String fakeUser = "87654321;Bob;Jones;banker;hashedPass;[4,5,6]";
        Files.writeString(testFile, fakeUser);

        TestUser user = new TestUser("12345678", "Alice", "Smith", "customer", "hashedPass", new byte[]{1,2,3});
        user.filePath = testFile;

        assertFalse(user.exists(12345678), "User should not exist in the file");
    }

    // -------------------------
    // File-based Read Tests
    // -------------------------

    @Test
    void testReadReturnsCorrectUserData(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("users.txt");
        String fakeUser = "12345678;Alice;Smith;customer;hashedPass;[1,2,3]";
        Files.writeString(testFile, fakeUser);

        TestUser user = new TestUser("12345678", "Alice", "Smith", "customer", "hashedPass", new byte[]{1,2,3});
        user.filePath = testFile;

        String[] userData = user.read(12345678);

        assertNotNull(userData, "User data should not be null");
        assertEquals("12345678", userData[0]);
        assertEquals("Alice", userData[1]);
        assertEquals("Smith", userData[2]);
        assertEquals("customer", userData[3]);
        assertEquals("hashedPass", userData[4]);
        assertEquals("[1,2,3]", userData[5]); // Arrays.toString format
    }

    @Test
    void testReadReturnsNullWhenUserNotFound(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("users.txt");
        String fakeUser = "87654321;Bob;Jones;banker;hashedPass;[4,5,6]";
        Files.writeString(testFile, fakeUser);

        TestUser user = new TestUser("12345678", "Alice", "Smith", "customer", "hashedPass", new byte[]{1,2,3});
        user.filePath = testFile;

        String[] userData = user.read(12345678);

        assertNull(userData, "Should return null when CPR not found");
    }
}