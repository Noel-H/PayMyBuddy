package com.noelh.paymybuddy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MoneyTransactionDTO {

    private LocalDateTime date;
    private String target;
    private double totalAmount;
    private double amount;
    private double taxAmount;
    private String tableColor;
    private String operator;
    private boolean transferToUser;

    public MoneyTransactionDTO(LocalDateTime date,
                               String target,
                               double amount,
                               double taxAmount,
                               String tableColor,
                               String operator,
                               boolean transferToUser,
                               double totalAmount) {
        this.date = date;
        this.target = target;
        this.amount = amount;
        this.taxAmount = taxAmount;
        this.tableColor = tableColor;
        this.operator = operator;
        this.transferToUser = transferToUser;
        this.totalAmount = totalAmount;
    }
}
