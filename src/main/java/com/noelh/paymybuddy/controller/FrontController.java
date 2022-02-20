package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.*;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.FrontService;
import com.noelh.paymybuddy.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FrontController {

    @Autowired
    private FrontService frontService;

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/")
    public String getIndex(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
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
           frontService.addUserAccountByMailAndPassword(signUpDTO);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return "SignUp";
        }
        return "login";
    }

    @GetMapping("HomePage")
    public String getHomePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
    }

    @GetMapping("UserTransferPage")
    public String getUserTransferPage(Model model){
        AddMoneyTransactionWithUserAccountDTO addMoneyTransactionWithUserAccountDTO = new AddMoneyTransactionWithUserAccountDTO();
        model.addAttribute("addMoneyTransactionWithUserAccountDTO", addMoneyTransactionWithUserAccountDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            model.addAttribute("userAccount", userAccount);
            return "UserTransferPage";
    }

    @PostMapping("UserTransferPage")
    public String submitUserTransferPage(@ModelAttribute("addMoneyTransactionWithUserAccountDTO") AddMoneyTransactionWithUserAccountDTO addMoneyTransactionWithUserAccountDTO,
                                         @ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                         Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
        confirmMoneyTransactionWithUserAccountDTO.setTaxAmount((addMoneyTransactionWithUserAccountDTO.getAmount()*0.5)/100);
        confirmMoneyTransactionWithUserAccountDTO.setTotalAmount(addMoneyTransactionWithUserAccountDTO.getAmount()+confirmMoneyTransactionWithUserAccountDTO.getTaxAmount());
        model.addAttribute("transactionRecap",addMoneyTransactionWithUserAccountDTO);
        model.addAttribute("test", confirmMoneyTransactionWithUserAccountDTO);
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        return "UserTransferConfirmationPage";
    }

    @PostMapping("UserTransferConfirmationPage")
    public String submitUserTransferConfirmationPage(@ModelAttribute("addMoneyTransactionWithUserAccountDTO") AddMoneyTransactionWithUserAccountDTO addMoneyTransactionWithUserAccountDTO,
                                                     @ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                                     Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            frontService.addMoneyTransactionBetweenUser(userAccount.getId(),confirmMoneyTransactionWithUserAccountDTO);
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
    }

    @GetMapping("BankTransferPage")
    public String getBankTransferPage(Model model){
        AddMoneyTransactionWithBankAccountDTO addMoneyTransactionWithBankAccountDTO = new AddMoneyTransactionWithBankAccountDTO();
        model.addAttribute("addMoneyTransactionWithBankAccountDTO", addMoneyTransactionWithBankAccountDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
            return "BankTransferPage";
    }

    @PostMapping("BankTransferPage")
    public String submitBankTransferPage(@ModelAttribute("addMoneyTransactionWithBankAccountDTO") AddMoneyTransactionWithBankAccountDTO addMoneyTransactionWithBankAccountDTO,
                                         @ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                         Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount((addMoneyTransactionWithBankAccountDTO.getAmount()*0.5)/100);
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(addMoneyTransactionWithBankAccountDTO.getAmount()+confirmMoneyTransactionWithBankAccountDTO.getTaxAmount());
        model.addAttribute("transactionRecap",addMoneyTransactionWithBankAccountDTO);
        model.addAttribute("test", confirmMoneyTransactionWithBankAccountDTO);
        model.addAttribute("withdraw",confirmMoneyTransactionWithBankAccountDTO.isWithdraw());
        model.addAttribute("withdrawStatus",frontService.getWithdrawStatus(confirmMoneyTransactionWithBankAccountDTO.isWithdraw()));
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        return "BankTransferConfirmationPage";
    }

    @PostMapping("BankTransferConfirmationPage")
    public String submitBankTransferConfirmationPage(@ModelAttribute("addMoneyTransactionWithBankAccountDTO") AddMoneyTransactionWithBankAccountDTO addMoneyTransactionWithBankAccountDTO,
                                                     @ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                                     Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            frontService.addMoneyTransactionWithBank(userAccount.getId(),confirmMoneyTransactionWithBankAccountDTO);
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
    }

    @GetMapping("FriendListPage")
    public String getFriendListPage(Model model){
        AddFriendDTO addFriendDTO = new AddFriendDTO();
        model.addAttribute("addFriendDTO",addFriendDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
            model.addAttribute("friendList",frontService.getFriendListByUser(userAccount));
            return "FriendListPage";
    }

    @PostMapping("FriendListPage")
    public String submitFriendListPage(@ModelAttribute("addFriendDTO") AddFriendDTO addFriendDTO, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            frontService.addFriendByUserAccountId(userAccount.getId(), addFriendDTO.getLoginMail());
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("friendList",frontService.getFriendListByUser(userAccount));
        return "FriendListPage";
    }

    @GetMapping("BankListPage")
    public String getBankListPagePage(Model model){
        AddBankDTO addBankDTO = new AddBankDTO();
        model.addAttribute("addBankDTO", addBankDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
            return "BankListPage";
    }

    @PostMapping("BankListPage")
    public String submitBankListPage(@ModelAttribute("addBankDTO") AddBankDTO addBankDTO, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userAccountService.getUserAccountByLoginMail(authentication.getName());
            frontService.addBankAccountByUserAccountId(userAccount.getId(), addBankDTO.getIban());
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
        return "BankListPage";
    }
}
