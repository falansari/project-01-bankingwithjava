package com.ga.cmdbank;

public class DebitMastercardTitanium implements IDebitcard {
    final double depositLimitDaily = 200_000.0;
    final double withdrawLimitDaily = 10_000.0;
    final double transferLimitOwnAccountDaily = 40_000.0;
    final double transferLimitOtherAccountDaily = 20_000.0;
}
