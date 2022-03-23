package com.noelh.paymybuddy.dto;

import lombok.Data;

/**
 * AddMoneyTransactionWithUserAccount dto
 */
@Data
public class AddMoneyTransactionWithUserAccountDTO {

    /**
     * loginMail for the AddMoneyTransactionWithUserAccount
     */
    private String loginMail;

    /**
     * amount for the AddMoneyTransactionWithUserAccount
     */
    private double amount;
}
