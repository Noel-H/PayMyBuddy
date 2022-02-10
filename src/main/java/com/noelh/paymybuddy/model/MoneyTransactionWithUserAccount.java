package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class MoneyTransactionWithUserAccount {

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
    private UserAccount sender;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserAccount receiver;
}
