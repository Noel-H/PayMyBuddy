package com.noelh.paymybuddy.dto;

import lombok.Data;

/**
 * SignUp dto
 */
@Data
public class SignUpDTO {

    /**
     * loginMail for the sign up
     */
    private String loginMail;

    /**
     * password for the sign up
     */
    private String password;
}
