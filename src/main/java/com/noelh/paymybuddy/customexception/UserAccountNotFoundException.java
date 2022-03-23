package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class UserAccountNotFoundException extends Exception{

    /**
     * Make an UserAccountNotFound exception
     */
    public UserAccountNotFoundException(){
        super();
    }

    /**
     * Make an UserAccountNotFound exception with a custom message
     * @param s is the custom message
     */
    public UserAccountNotFoundException(String s){
        super(s);
    }
}
