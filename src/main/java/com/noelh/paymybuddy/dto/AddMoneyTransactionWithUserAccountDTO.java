package com.noelh.paymybuddy.dto;

import lombok.Data;

@Data
public class AddMoneyTransactionWithUserAccountDTO {
    private String loginMail;
    private double amount;
}
