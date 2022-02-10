package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.AccountConnexionInfoDTO;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    public UserAccount getUserAccount(AccountConnexionInfoDTO accountConnexionInfoDTO) {
        return userAccountRepository.getById(1L);
    }
}
