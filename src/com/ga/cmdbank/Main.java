package com.ga.cmdbank;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner inputScanner = new Scanner(System.in); // Universal app scanner
        UserRead userRead = new UserRead().display();

        switch (userRead.getUserRole()) {
            case "customer":
                userRead.displayMainMenuCustomer(userRead, inputScanner);
                break;
            case "banker":
                userRead.displayMainMenuBanker(userRead, inputScanner);
                break;
        }
    }
}
