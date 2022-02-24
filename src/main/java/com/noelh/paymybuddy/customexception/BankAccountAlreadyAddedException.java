package com.noelh.paymybuddy.customexception;

public class BankAccountAlreadyAddedException extends Exception{

    public BankAccountAlreadyAddedException(){
        super();
    }

    public BankAccountAlreadyAddedException(String s){
        super(s);
    }
}
