package com.ga.cmdbank;

public class DebitMastercardPlatinum implements IDebitcard {
    final double depositLimitDaily = 200_000.0;
    final double withdrawLimitDaily = 20_000.0;
    final double transferLimitOwnAccountDaily = 80_000.0;
    final double transferLimitOtherAccountDaily = 40_000.0;
}
