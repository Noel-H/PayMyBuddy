package com.noelh.paymybuddy.model;

import javax.persistence.*;
import java.util.List;

@Entity
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
    private List<MoneyTransaction> moneyTransactionList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<UserAccount> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<UserAccount> friendList) {
        this.friendList = friendList;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public List<MoneyTransaction> getMoneyTransactionList() {
        return moneyTransactionList;
    }

    public void setMoneyTransactionList(List<MoneyTransaction> moneyTransactionList) {
        this.moneyTransactionList = moneyTransactionList;
    }
}
