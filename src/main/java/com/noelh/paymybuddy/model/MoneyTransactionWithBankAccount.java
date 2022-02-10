package com.noelh.paymybuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(nullable = false)
    private BankAccount bank;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserAccount user;

    @Column(nullable = false)
    private Boolean withdraw;
}
