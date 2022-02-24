package com.noelh.paymybuddy.customexception;

public class UserAccountNotFoundException extends Exception{

    public UserAccountNotFoundException(){
        super();
    }

    public UserAccountNotFoundException(String s){
        super(s);
    }
}
