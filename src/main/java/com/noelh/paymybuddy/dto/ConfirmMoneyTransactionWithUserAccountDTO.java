package com.noelh.paymybuddy.dto;

import lombok.Data;

@Data
public class ConfirmMoneyTransactionWithUserAccountDTO {
    private String loginMail;
    private double amount;
    private double taxAmount;
    private double totalAmount;
}
