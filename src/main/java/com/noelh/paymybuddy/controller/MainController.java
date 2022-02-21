package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.*;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MoneyTransactionService moneyTransactionService;

    @Autowired
    private MoneyTransactionWithUserAccountService moneyTransactionWithUserAccountService;

    @Autowired
    private MoneyTransactionWithBankAccountService moneyTransactionWithBankAccountService;

    @GetMapping("/")
    public String getIndex(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/SignUp")
    public String getSignUpPage(Model model){
        model.addAttribute("signUpDTO", new SignUpDTO());
        return "SignUp";
    }

    @PostMapping("/SignUp")
    public String submitSignUpPage(@ModelAttribute("signUpDTO") SignUpDTO signUpDTO){
        try{
            userAccountService.addUserAccountByMailAndPassword(signUpDTO);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return "SignUp";
        }
        return "login";
    }

    @GetMapping("/Home")
    public String getHome(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    @GetMapping("/UserTransfer")
    public String getUserTransfer(Model model){
        model.addAttribute("addMoneyTransactionWithUserAccountDTO", new AddMoneyTransactionWithUserAccountDTO());
        model.addAttribute("userAccount", userAccountService.findUserAccountByAuthentication());
        return "UserTransfer";
    }

    @PostMapping("/UserTransfer")
    public String submitUserTransfer(@ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                     Model model){
        confirmMoneyTransactionWithUserAccountDTO.setTaxAmount(moneyTransactionService.findTaxAmount(confirmMoneyTransactionWithUserAccountDTO.getAmount()));
        confirmMoneyTransactionWithUserAccountDTO.setTotalAmount(confirmMoneyTransactionWithUserAccountDTO.getAmount()+confirmMoneyTransactionWithUserAccountDTO.getTaxAmount());
        model.addAttribute("confirmTransactionRecap", confirmMoneyTransactionWithUserAccountDTO);
        model.addAttribute("userAccount", userAccountService.findUserAccountByAuthentication());
        return "UserTransferConfirmation";
    }

    @PostMapping("/UserTransferConfirmation")
    public String submitUserTransferConfirmation(@ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                                 Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        try{
            moneyTransactionWithUserAccountService.addMoneyTransactionWithUserAccount(userAccount.getId(),confirmMoneyTransactionWithUserAccountDTO);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            model.addAttribute("addMoneyTransactionWithUserAccountDTO", new AddMoneyTransactionWithUserAccountDTO());
            model.addAttribute("userAccount", userAccount);
            return "UserTransfer";
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    @GetMapping("/BankTransfer")
    public String getBankTransfer(Model model){
        model.addAttribute("addMoneyTransactionWithBankAccountDTO", new AddMoneyTransactionWithBankAccountDTO());
        model.addAttribute("userAccount", userAccountService.findUserAccountByAuthentication());
        return "BankTransfer";
    }

    @PostMapping("/BankTransfer")
    public String submitBankTransfer(@ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                     Model model){
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(moneyTransactionService.findTaxAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount()));
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount()+confirmMoneyTransactionWithBankAccountDTO.getTaxAmount());
        model.addAttribute("confirmTransactionRecap",confirmMoneyTransactionWithBankAccountDTO);
        model.addAttribute("withdraw",confirmMoneyTransactionWithBankAccountDTO.isWithdraw());
        model.addAttribute("withdrawStatus",moneyTransactionWithBankAccountService.getWithdrawStatus(confirmMoneyTransactionWithBankAccountDTO.isWithdraw()));
        model.addAttribute("userAccount", userAccountService.findUserAccountByAuthentication());
        return "BankTransferConfirmation";
    }

    @PostMapping("/BankTransferConfirmation")
    public String submitBankTransferConfirmation(@ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                                 Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        try {
            moneyTransactionWithBankAccountService.addMoneyTransactionWithBank(userAccount.getId(),confirmMoneyTransactionWithBankAccountDTO);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            model.addAttribute("addMoneyTransactionWithBankAccountDTO", new AddMoneyTransactionWithBankAccountDTO());
            model.addAttribute("userAccount", userAccount);
            return "BankTransfer";
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    @GetMapping("/FriendList")
    public String getFriendListPage(Model model){
        model.addAttribute("addFriendDTO", new AddFriendDTO());
        model.addAttribute("userAccount", userAccountService.findUserAccountByAuthentication());
        return "FriendList";
    }

    @PostMapping("/FriendList")
    public String submitFriendListPage(@ModelAttribute("addFriendDTO") AddFriendDTO addFriendDTO, Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        try{
            userAccountService.addFriendByUserAccountId(userAccount.getId(), addFriendDTO.getLoginMail());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            model.addAttribute("userAccount", userAccount);
            return "FriendList";
        }
        model.addAttribute("userAccount", userAccount);
        return "FriendList";
    }

    @GetMapping("/BankList")
    public String getBankListPagePage(Model model){
        model.addAttribute("addBankDTO", new AddBankDTO());
        model.addAttribute("userAccount", userAccountService.findUserAccountByAuthentication());
        return "BankList";
    }

    @PostMapping("/BankList")
    public String submitBankListPage(@ModelAttribute("addBankDTO") AddBankDTO addBankDTO, Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        try {
            userAccountService.addBankAccountByUserAccountId(userAccount.getId(), addBankDTO.getIban());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            model.addAttribute("userAccount", userAccount);
            return "BankList";
        }
        model.addAttribute("userAccount", userAccount);
        return "BankList";
    }
}
