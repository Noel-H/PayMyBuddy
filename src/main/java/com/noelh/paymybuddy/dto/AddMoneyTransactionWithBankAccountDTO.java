package com.noelh.paymybuddy.dto;

import lombok.Data;

/**
 * AddMoneyTransactionWithBankAccount dto
 */
@Data
public class AddMoneyTransactionWithBankAccountDTO {

    /**
     * iban for the AddMoneyTransactionWithBankAccount
     */
    private String iban;

    /**
     * amount for the AddMoneyTransactionWithBankAccount
     */
    private double amount;

    /**
     * withdraw status for the AddMoneyTransactionWithBankAccount
     */
    private boolean withdraw;
}
