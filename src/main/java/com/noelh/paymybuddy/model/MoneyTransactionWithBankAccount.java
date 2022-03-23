package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * MoneyTransactionWithBankAccount model
 */
@Entity
@Data
public class MoneyTransactionWithBankAccount {

    /**
     * id for the MoneyTransactionWithBankAccount
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * date for the MoneyTransactionWithBankAccount
     */
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * amount for the MoneyTransactionWithBankAccount
     */
    @Column(nullable = false)
    private Double amount;

    /**
     * tax amount for the MoneyTransactionWithBankAccount
     */
    @Column(nullable = false)
    private Double taxAmount;

    /**
     * bank for the MoneyTransactionWithBankAccount
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BankAccount bank;

    /**
     * user for the MoneyTransactionWithBankAccount
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserAccount user;

    /**
     * withdraw status for the MoneyTransactionWithBankAccount
     */
    @Column(nullable = false)
    private Boolean withdraw;
}
