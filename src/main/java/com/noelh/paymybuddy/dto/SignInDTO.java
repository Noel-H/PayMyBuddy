package com.noelh.paymybuddy.dto;

import lombok.Data;

/**
 * SignIn dto
 */
@Data
public class SignInDTO {

    /**
     * loginMail for the sign in
     */
    private String loginMail;

    /**
     * password for the sign in
     */
    private String password;
}
