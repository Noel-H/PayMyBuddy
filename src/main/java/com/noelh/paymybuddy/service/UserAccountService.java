package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.SignInDTO;
import com.noelh.paymybuddy.dto.UserAccountMinInfoDTO;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    public UserAccount getUserAccount(long id) {
        return userAccountRepository.getById(id);
    }

    public UserAccount getUserAccountByMailAndPassword(SignInDTO signInDTO) {
        return userAccountRepository.findByLoginMailAndPassword(signInDTO.getLoginMail(),signInDTO.getPassword())
                .orElseThrow(()-> new NoSuchElementException("Echec lors de la connexion !"));
    }
}
