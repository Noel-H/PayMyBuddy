package com.noelh.paymybuddy.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * MoneyTransaction dto
 */
@Data
public class MoneyTransactionDTO {

    /**
     * date of the money transaction
     */
    private LocalDateTime date;

    /**
     * target of the money transaction
     */
    private String target;

    /**
     * total amount of the money transaction
     */
    private double totalAmount;

    /**
     * amount of the money transaction
     */
    private double amount;

    /**
     * tax amount of the money transaction
     */
    private double taxAmount;

    /**
     * table color of the money transaction
     */
    private String tableColor;

    /**
     * operator of the money transaction
     */
    private String operator;

    /**
     * transferToUser of the money transaction
     */
    private boolean transferToUser;

    /**
     * Constructor for MoneyTransaction
     * @param date for the money transaction
     * @param target for the money transaction
     * @param amount for the money transaction
     * @param taxAmount for the money transaction
     * @param tableColor for the money transaction
     * @param operator for the money transaction
     * @param transferToUser for the money transaction
     * @param totalAmount for the money transaction
     */
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
