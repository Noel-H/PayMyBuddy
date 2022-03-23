package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class LoginMailAlreadyAddedException extends Exception{

    /**
     * Make an LoginMailAlreadyAdded exception
     */
    public LoginMailAlreadyAddedException(){
        super();
    }

    /**
     * Make an LoginMailAlreadyAdded exception with a custom message
     * @param s is the custom message
     */
    public LoginMailAlreadyAddedException(String s){
        super(s);
    }
}
