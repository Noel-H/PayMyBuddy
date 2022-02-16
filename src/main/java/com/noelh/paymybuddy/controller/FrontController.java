package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.AddBankDTO;
import com.noelh.paymybuddy.dto.AddFriendDTO;
import com.noelh.paymybuddy.dto.SignInDTO;
import com.noelh.paymybuddy.dto.SignUpDTO;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

@Controller
public class FrontController {

    UserAccount userAccount;

    @Autowired
    private FrontService frontService;

    @GetMapping()
    public String getSignInPage(Model model){
        SignInDTO signInDTO = new SignInDTO();
        model.addAttribute("signInDTO",signInDTO);
        return "SignInPage";
    }

    @PostMapping()
    public String submitSignInPage(@ModelAttribute("signInDTO") SignInDTO signInDTO, Model model){
        try {
             userAccount = frontService.getUserAccountByMailAndPassword(signInDTO);
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
    }

    @GetMapping("SignUpPage")
    public String getSignUpPage(Model model){
        SignUpDTO signUpDTO = new SignUpDTO();
        model.addAttribute("signUpDTO", signUpDTO);
        return "SignUpPage";
    }

    @PostMapping("SignUpPage")
    public String submitSignUpPage(@ModelAttribute("signUpDTO") SignUpDTO signUpDTO, Model model){
        try {
            userAccount = frontService.addUserAccountByMailAndPassword(signUpDTO);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return "SignUpPage";
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
    }

    @GetMapping("DisconnectUser")
    public String DisconnectUser(Model model){
        userAccount=null;
        SignInDTO signInDTO = new SignInDTO();
        model.addAttribute("signInDTO",signInDTO);
        return "DisconnectUser";
    }

    @GetMapping("HomePage")
    public String getHomePage(Model model){
        if (!frontService.isUserConnected(userAccount)){
            SignInDTO signInDTO = new SignInDTO();
            model.addAttribute("signInDTO",signInDTO);
            return "SignInPage";
        }
        try {
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
            return "HomePage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @GetMapping("UserTransferPage")
    public String getUserTransferPage(Model model){
        if (!frontService.isUserConnected(userAccount)){
            SignInDTO signInDTO = new SignInDTO();
            model.addAttribute("signInDTO",signInDTO);
            return "SignInPage";
        }
        try {
            model.addAttribute("userAccount", userAccount);
            return "UserTransferPage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @GetMapping("BankTransferPage")
    public String getBankTransferPage(Model model){
        if (!frontService.isUserConnected(userAccount)){
            SignInDTO signInDTO = new SignInDTO();
            model.addAttribute("signInDTO",signInDTO);
            return "SignInPage";
        }
        try {
            model.addAttribute("userAccount", userAccount);
            return "BankTransferPage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @GetMapping("FriendListPage")
    public String getFriendListPage(Model model){
        if (!frontService.isUserConnected(userAccount)){
            SignInDTO signInDTO = new SignInDTO();
            model.addAttribute("signInDTO",signInDTO);
            return "SignInPage";
        }
        AddFriendDTO addFriendDTO = new AddFriendDTO();
        model.addAttribute("addFriendDTO",addFriendDTO);
        try {
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("friendList",frontService.getFriendListByUser(userAccount));
            return "FriendListPage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @PostMapping("FriendListPage")
    public String submitFriendListPage(@ModelAttribute("addFriendDTO") AddFriendDTO addFriendDTO, Model model){
        try {
            frontService.addFriendByUserAccountId(userAccount.getId(), addFriendDTO.getLoginMail());
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("friendList",frontService.getFriendListByUser(userAccount));
        return "FriendListPage";
    }

    @GetMapping("BankListPage")
    public String getBankListPagePage(Model model){
        if (!frontService.isUserConnected(userAccount)){
            SignInDTO signInDTO = new SignInDTO();
            model.addAttribute("signInDTO",signInDTO);
            return "SignInPage";
        }
        AddBankDTO addBankDTO = new AddBankDTO();
        model.addAttribute("addBankDTO", addBankDTO);
        try {
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
            return "BankListPage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @PostMapping("BankListPage")
    public String submitBankListPage(@ModelAttribute("addBankDTO") AddBankDTO addBankDTO, Model model){
        try {
            frontService.addBankAccountByUserAccountId(userAccount.getId(), addBankDTO.getIban());
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
        return "BankListPage";
    }
}
