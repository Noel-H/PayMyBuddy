package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true)
    private String iban;
}
