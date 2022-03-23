package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class BankNotFoundInBankAccountListException extends Exception{

    /**
     * Make an BankNotFoundInBankAccountList exception
     */
    public BankNotFoundInBankAccountListException(){
        super();
    }

    /**
     * Make an BankNotFoundInBankAccountList exception with a custom message
     * @param s is the custom message
     */
    public BankNotFoundInBankAccountListException(String s){
        super(s);
    }
}
