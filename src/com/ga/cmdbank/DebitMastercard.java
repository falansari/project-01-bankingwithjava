package com.ga.cmdbank;

public class DebitMastercard implements IDebitcard {
    final double depositLimitDaily = 200_000.0;
    final double withdrawLimitDaily = 5_000.0;
    final double transferLimitOwnAccountDaily = 20_000.0;
    final double transferLimitOtherAccountDaily = 10_000.0;
}
