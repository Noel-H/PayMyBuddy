package com.noelh.paymybuddy.dto;

import lombok.Data;

/**
 * ConfirmMoneyTransactionWithBankAccount dto
 */
@Data
public class ConfirmMoneyTransactionWithBankAccountDTO {

    /**
     * iban to confirm the transaction
     */
    private String iban;

    /**
     * amount to confirm the transaction
     */
    private double amount;

    /**
     * withdraw status to confirm the transaction
     */
    private boolean withdraw;

    /**
     * tax amount to confirm the transaction
     */
    private double taxAmount;

    /**
     * total amount to confirm the transaction
     */
    private double totalAmount;
}
