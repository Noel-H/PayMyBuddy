package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class NotEnoughMoneyException extends Exception{

    /**
     * Make an NotEnoughMoney exception
     */
    public NotEnoughMoneyException(){
        super();
    }

    /**
     * Make an NotEnoughMoney exception with a custom message
     * @param s is the custom message
     */
    public NotEnoughMoneyException(String s){
        super(s);
    }
}
