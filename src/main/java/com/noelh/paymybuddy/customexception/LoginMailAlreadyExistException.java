package com.noelh.paymybuddy.customexception;

public class LoginMailAlreadyExistException extends Exception{

    public LoginMailAlreadyExistException(){
        super();
    }

    public LoginMailAlreadyExistException(String s){
        super(s);
    }
}
