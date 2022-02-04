package com.noelh.paymybuddy;

import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    UserAccount user = new UserAccount();
    user.setBalance(500);
    user.setLogin("abcd@abcd.abcd");
    userAccountRepository.save(user);
    }
}
