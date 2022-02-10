package com.noelh.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MoneyTransactionDTO {

    private LocalDateTime date;
    private String target;
    private double totalAmount;
    private double amount;
    private double taxAmount;
    private boolean give;
}
