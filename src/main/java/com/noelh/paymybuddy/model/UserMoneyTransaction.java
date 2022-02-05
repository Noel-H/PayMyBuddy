package com.noelh.paymybuddy.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class UserMoneyTransaction extends MoneyTransaction{

    @OneToOne
    private UserAccount sender;

    @OneToOne
    private UserAccount receiver;

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }
}
