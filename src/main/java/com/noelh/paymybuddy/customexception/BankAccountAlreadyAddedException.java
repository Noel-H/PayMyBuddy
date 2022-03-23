package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class BankAccountAlreadyAddedException extends Exception{

    /**
     * Make an BankAccountAlreadyAdded exception
     */
    public BankAccountAlreadyAddedException(){
        super();
    }

    /**
     * Make an BankAccountAlreadyAdded exception with a custom message
     * @param s is the custom message
     */
    public BankAccountAlreadyAddedException(String s){
        super(s);
    }
}
