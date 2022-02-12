package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.UserAccountMinInfoDTO;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    public UserAccount getUserAccount(long id) {
        return userAccountRepository.getById(id);
    }
}
