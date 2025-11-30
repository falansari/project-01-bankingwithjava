package com.ga.cmdbank;

public abstract class User {
    public String getCprInput() {
        return cprInput;
    }

    public void setCprInput(String cprInput) {
        this.cprInput = cprInput;
    }

    public Integer getCpr() {
        return cpr;
    }

    public void setCpr(Integer cpr) {
        this.cpr = cpr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    private String cprInput;
    private Integer cpr;
    private String firstName;
    private String lastName;
    private String userRole;
    private String hashedPassword;
    private String passwordSalt;

    public User(String cprInput, String firstName, String lastName, String userRole, String hashedPassword, String passwordSalt) {
        this.cprInput = cprInput;
        this.cpr = convertCPRInput(cprInput);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.hashedPassword = hashedPassword;
        this.passwordSalt = passwordSalt;
    }

}