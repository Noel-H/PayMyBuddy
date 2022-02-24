package com.noelh.paymybuddy.customexception;

public class BankAccountAlreadyUsedException extends Exception{

    public BankAccountAlreadyUsedException(){
        super();
    }

    public BankAccountAlreadyUsedException(String s){
        super(s);
    }
}
