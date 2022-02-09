package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class MoneyTransactionWithBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double taxAmount;

    @Column(nullable = false)
    private String ibanBankAccount;

    @Column(nullable = false)
    private String loginMailUserAccount;

    @Column(nullable = false)
    private Boolean withdraw;
}
