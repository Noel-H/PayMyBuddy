package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.SignInDTO;
import com.noelh.paymybuddy.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FrontController {

    @Autowired
    private FrontService frontService;

//    @GetMapping("/HomePage")
//    public String getHomePage(Model model){
//        model.addAttribute("baseInfo", frontService.getUserAccountMinimalInfoById(1));
//        model.addAttribute("transaction", frontService.getMoneyTransactionListById(1));
//        return "HomePage";
//    }

    @GetMapping("/UserTransferPage")
    public String getUserTransferPage(Model model){
        model.addAttribute("loginMail","abcd@abcd.abcd");
        return "UserTransferPage";
    }

    @GetMapping()
    public String getSignInPage(Model model){
        SignInDTO signInDTO = new SignInDTO();
        model.addAttribute("signInDTO",signInDTO);
        return "SignInPage";
    }

    @PostMapping()
    public String submitSignInPage(@ModelAttribute("signInDTO") SignInDTO signInDTO, Model model){
        model.addAttribute("baseInfo", frontService.getUserAccountMinimalInfoById(1));
        model.addAttribute("transaction", frontService.getMoneyTransactionListById(1));
        System.out.println(signInDTO);
        return "HomePage";
    }

    @GetMapping("SignUpPage")
    public String getSignUpPage(){
        return "SignUpPage";
    }
}
