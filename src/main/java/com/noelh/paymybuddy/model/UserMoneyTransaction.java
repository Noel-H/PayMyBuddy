package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserMoneyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime date;

    private double amount;

    private double taxAmount;

    @OneToOne
    private UserAccount sender;

    @OneToOne
    private UserAccount receiver;
}
