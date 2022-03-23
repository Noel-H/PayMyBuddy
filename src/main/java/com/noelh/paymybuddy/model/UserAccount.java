package com.noelh.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * UserAccount model
 */
@Entity
@Data
public class UserAccount {

    /**
     * id for the UserAccount
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * loginMail for the UserAccount
     */
    @Column(nullable = false,
            unique = true)
    private String loginMail;

    /**
     * password for the UserAccount
     */
    @Column(nullable = false)
    private String password;

    /**
     * balance for the UserAccount
     */
    @Column(nullable = false)
    private Double balance;

    /**
     * friendList for the UserAccount
     */
    @ManyToMany
    private List<UserAccount> friendList;

    /**
     * bankAccountList for the UserAccount
     */
    @OneToMany
    private List<BankAccount> bankAccountList;

    /**
     * moneyTransactionWithBankAccountList for the UserAccount
     */
    @OneToMany
    private List<MoneyTransactionWithBankAccount> moneyTransactionWithBankAccountList;

    /**
     * moneyTransactionWithUserAccountList for the UserAccount
     */
    @ManyToMany
    private List<MoneyTransactionWithUserAccount> moneyTransactionWithUserAccountList;
}
