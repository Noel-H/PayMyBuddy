package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;

@Controller
public class FrontController {

    @Autowired
    private FrontService frontService;

    @GetMapping
    public String getIndex(){
        return "Index";
    }

    @GetMapping("/HomePage")
    public String getHomePage(Model model){
        model.addAttribute("baseInfo", frontService.getUserAccountMinimalInfoById(1));
        model.addAttribute("transaction", frontService.getMoneyTransactionListById(1));
        return "HomePage";
    }

    @GetMapping("/UserTransferPage")
    public String getUserTransferPage(Model model){
        model.addAttribute("loginMail","abcd@abcd.abcd");
        return "UserTransferPage";
    }
}
