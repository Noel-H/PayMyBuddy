package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.AccountConnexionInfoDTO;
import com.noelh.paymybuddy.dto.MoneyTransactionDTO;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
public class FrontController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping
    public String getHomePage(Model model){
        model.addAttribute("loginMail","abcd@abcd.abcd");
        model.addAttribute("password","12345");
        model.addAttribute("balance",100.0);
        model.addAttribute("transaction", Arrays.asList(
                new MoneyTransactionDTO(LocalDateTime.now(),"poml",100,90,10,true),
                new MoneyTransactionDTO(LocalDateTime.now(),"ikju",30,20,10,true),
                new MoneyTransactionDTO(LocalDateTime.now(),"yhgt",5000,4090,10,false),
                new MoneyTransactionDTO(LocalDateTime.now(),"rfde",5.75,5,0.75,false),
                new MoneyTransactionDTO(LocalDateTime.now(),"zsqa",10,9,1,true)
        ));
        return "HomePage";
    }
}
