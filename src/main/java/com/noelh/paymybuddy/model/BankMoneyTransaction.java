package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class BankMoneyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime date;

    private double amount;

    private double taxAmount;

    @OneToOne
    private BankAccount bankInfo;

    private boolean withdraw;
}
