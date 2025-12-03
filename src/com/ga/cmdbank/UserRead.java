package com.ga.cmdbank;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Read user data, including login.
 */
public class UserRead extends User implements IPassword {

    public UserRead(int CPR, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String[] userData = login(CPR, password);

        cprInput = userData[0];
        firstName = userData[1];
        lastName = userData[2];
        userRole = userData[3];
    }

    /**
     * New user constructor.
     * @param cprInput String user's CPR number. Unique user identifier and login ID.
     * @param firstName String user's first name.
     * @param lastName String user's last name.
     * @param userRole String user's role, 1 of 2 options: [banker, customer].
     * @param hashedPassword String User's hashed password
     * @param passwordSalt String User's password's unique salt value.
     */
    public UserRead(String cprInput, String firstName, String lastName, String userRole, String hashedPassword, byte[] passwordSalt) {
        super(cprInput.trim(), firstName.trim().toLowerCase(), lastName.trim().toLowerCase(), userRole.trim(), hashedPassword, passwordSalt);
    }

    /**
     * Login an existing user to the system.
     * @param username int User's username, default is their CPR number.
     * @param password  String  User's supplied password, must be verified first before allowing login.
     * @return String[] User's stored data in users.txt file. Format: {cpr,firstName,lastName,accountRole,hashedPassword,passwordSalt}
     */
    String[] login(int username, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (!exists(username)) throw new IOException("A user with username " + username + " does not exist.");

        String[] userData = read(username);
        String userStoredHash = userData[4];
        byte[] userStoredSalt = IPassword.decodeBase64Salt(userData[5]);

        if (!IPassword.verifyPassword(password, userStoredHash, userStoredSalt)) throw new IOException("Password does not match.");

        return userData;
    }
}
