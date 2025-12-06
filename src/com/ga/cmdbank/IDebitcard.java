package com.ga.cmdbank;

/**
 * Currency support: US Dollars.
 */
public interface IDebitcard {
    double depositLimitOwnAccountDaily = 0.0;
    double withdrawLimitDaily = 0.0;
    double transferLimitOwnAccountDaily = 0.0;
    double transferLimitOtherAccountDaily = 0.0;
}
