package com.noelh.paymybuddy.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class BankMoneyTransaction extends MoneyTransaction{

    @OneToOne
    private BankAccount bankInfo;

    @OneToOne
    private UserAccount userInfo;

    private boolean withdraw;

    public BankAccount getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankAccount bankInfo) {
        this.bankInfo = bankInfo;
    }

    public UserAccount getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserAccount userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isWithdraw() {
        return withdraw;
    }

    public void setWithdraw(boolean withdraw) {
        this.withdraw = withdraw;
    }
}
