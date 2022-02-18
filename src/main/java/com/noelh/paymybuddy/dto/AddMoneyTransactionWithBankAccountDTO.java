package com.noelh.paymybuddy.dto;

import lombok.Data;

@Data
public class AddMoneyTransactionWithBankAccountDTO {
    private String iban;
    private double amount;
    private boolean withdraw;
}
