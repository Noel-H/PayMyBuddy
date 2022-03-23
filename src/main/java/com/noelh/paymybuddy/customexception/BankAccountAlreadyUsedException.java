package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class BankAccountAlreadyUsedException extends Exception{

    /**
     * Make an BankAccountAlreadyUsed exception
     */
    public BankAccountAlreadyUsedException(){
        super();
    }

    /**
     * Make an BankAccountAlreadyUsed exception with a custom message
     * @param s is the custom message
     */
    public BankAccountAlreadyUsedException(String s){
        super(s);
    }
}
