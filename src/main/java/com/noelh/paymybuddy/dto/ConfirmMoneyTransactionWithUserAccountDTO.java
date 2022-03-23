package com.noelh.paymybuddy.dto;

import lombok.Data;

/**
 * ConfirmMoneyTransactionWithUserAccount dto
 */
@Data
public class ConfirmMoneyTransactionWithUserAccountDTO {

    /**
     * loginMail to confirm the transaction
     */
    private String loginMail;

    /**
     * amount to confirm the transaction
     */
    private double amount;

    /**
     * tax amount to confirm the transaction
     */
    private double taxAmount;

    /**
     * total amount to confirm the transaction
     */
    private double totalAmount;
}
