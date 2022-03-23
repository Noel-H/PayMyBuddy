package com.noelh.paymybuddy.customexception;

/**
 * Custom exception class
 */
public class LoginMailAlreadyExistException extends Exception{

    /**
     * Make an LoginMailAlreadyExist exception
     */
    public LoginMailAlreadyExistException(){
        super();
    }

    /**
     * Make an LoginMailAlreadyExist exception with a custom message
     * @param s is the custom message
     */
    public LoginMailAlreadyExistException(String s){
        super(s);
    }
}
