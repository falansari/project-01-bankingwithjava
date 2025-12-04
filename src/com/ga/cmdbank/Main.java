package com.ga.cmdbank;

public class Main {
    public static void main(String[] args) {
        UserRead userRead = new UserRead().display();

        switch (userRead.getUserRole()) {
            case "customer":
                userRead.displayMainMenuCustomer(userRead);
                break;
            case "banker":
                userRead.displayMainMenuBanker(userRead);
                break;
        }
    }
}
