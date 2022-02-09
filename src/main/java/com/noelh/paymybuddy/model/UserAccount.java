package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true)
    private String loginMail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Double balance;

    @OneToMany
    private List<Friend> friendList;

    @OneToMany
    private List<BankAccount> bankAccountList;

    @OneToMany
    private List<MoneyTransactionWithBankAccount> moneyTransactionWithBankAccountList;

    @OneToMany
    private List<MoneyTransactionWithUserAccount> moneyTransactionWithUserAccountList;
}
