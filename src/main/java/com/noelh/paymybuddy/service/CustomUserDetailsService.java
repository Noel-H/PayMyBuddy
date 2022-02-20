package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.configuration.CustomUserDetails;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount userAccount = userAccountRepository.findByLoginMail(username);
        if (userAccount==null){
            throw new UsernameNotFoundException("UserAccount not found");
        }
        return new CustomUserDetails(userAccount);
    }
}
