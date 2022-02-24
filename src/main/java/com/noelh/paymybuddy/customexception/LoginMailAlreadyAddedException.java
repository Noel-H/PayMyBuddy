package com.noelh.paymybuddy.customexception;

public class LoginMailAlreadyAddedException extends Exception{

    public LoginMailAlreadyAddedException(){
        super();
    }

    public LoginMailAlreadyAddedException(String s){
        super(s);
    }
}
