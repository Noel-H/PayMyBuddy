package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String iban;
}
