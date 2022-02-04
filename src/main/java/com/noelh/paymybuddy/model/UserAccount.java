package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String password;

    private double balance;

    @OneToMany
    private List<UserAccount> friendList;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BankAccount> bankAccountList;

    @OneToMany
    private List<BankMoneyTransaction> bankMoneyTransactionList;

    @OneToMany
    private List<UserMoneyTransaction> userMoneyTransactionList;

}
