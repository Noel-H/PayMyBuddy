package com.noelh.paymybuddy.dto;

import lombok.Data;

@Data
public class ConfirmMoneyTransactionWithBankAccountDTO {
    private String iban;
    private double amount;
    private boolean withdraw;
    private double taxAmount;
    private double totalAmount;
}
