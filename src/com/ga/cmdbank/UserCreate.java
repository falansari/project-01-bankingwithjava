package com.ga.cmdbank;

public class UserCreate extends User {
    public UserCreate(String cprInput, String firstName, String lastName, String userRole, String hashedPassword, String passwordSalt) {
        super(cprInput, firstName, lastName, userRole, hashedPassword, passwordSalt);
    }
}
