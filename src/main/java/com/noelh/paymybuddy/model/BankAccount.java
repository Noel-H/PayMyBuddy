package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;

/**
 * BankAccount model
 */
@Entity
@Data
public class BankAccount {

    /**
     * id for BankAccount
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * iban for BankAccount
     */
    @Column(nullable = false,
            unique = true)
    private String iban;
}
