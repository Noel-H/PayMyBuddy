package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * MoneyTransactionWithUserAccount model
 */
@Entity
@Data
public class MoneyTransactionWithUserAccount {

    /**
     * id for the MoneyTransactionWithUserAccount
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * date for the MoneyTransactionWithUserAccount
     */
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * amount for the MoneyTransactionWithUserAccount
     */
    @Column(nullable = false)
    private Double amount;

    /**
     * tax amount for the MoneyTransactionWithUserAccount
     */
    @Column(nullable = false)
    private Double taxAmount;

    /**
     * sender for the MoneyTransactionWithUserAccount
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserAccount sender;

    /**
     * receiver for the MoneyTransactionWithUserAccount
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserAccount receiver;
}
