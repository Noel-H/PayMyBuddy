package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.SignInDTO;
import com.noelh.paymybuddy.dto.SignUpDTO;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public UserAccount addUserAccountByMailAndPassword(SignUpDTO signUpDTO) {
        if(userAccountRepository.existsUserAccountByLoginMail(signUpDTO.getLoginMail())){
            throw new IllegalArgumentException("The login "+signUpDTO.getLoginMail()+" is already taken");
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setLoginMail(signUpDTO.getLoginMail());
        userAccount.setPassword(signUpDTO.getPassword());
        userAccount.setBalance(0.0);
        userAccount.setFriendList(new ArrayList<>());
        userAccount.setBankAccountList(new ArrayList<>());
        userAccount.setMoneyTransactionWithBankAccountList(new ArrayList<>());
        userAccount.setMoneyTransactionWithUserAccountList(new ArrayList<>());
        return userAccountRepository.save(userAccount);
    }
}
