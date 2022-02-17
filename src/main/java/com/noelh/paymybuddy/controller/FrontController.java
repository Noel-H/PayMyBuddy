package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.*;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
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
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
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
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
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
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
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
        AddMoneyTransactionWithUserAccountDTO addMoneyTransactionWithUserAccountDTO = new AddMoneyTransactionWithUserAccountDTO();
        model.addAttribute("addMoneyTransactionWithUserAccountDTO", addMoneyTransactionWithUserAccountDTO);
        try {
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
            return "UserTransferPage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @PostMapping("UserTransferPage")
    public String submitUserTransferPage(@ModelAttribute("addMoneyTransactionWithUserAccountDTO") AddMoneyTransactionWithUserAccountDTO addMoneyTransactionWithUserAccountDTO,
                                         @ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                         Model model){
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
        try {
            frontService.addMoneyTransactionBetweenUser(userAccount.getId(),confirmMoneyTransactionWithUserAccountDTO);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
    }

    @GetMapping("BankTransferPage")
    public String getBankTransferPage(Model model){
        if (!frontService.isUserConnected(userAccount)){
            SignInDTO signInDTO = new SignInDTO();
            model.addAttribute("signInDTO",signInDTO);
            return "SignInPage";
        }
        AddMoneyTransactionWithBankAccountDTO addMoneyTransactionWithBankAccountDTO = new AddMoneyTransactionWithBankAccountDTO();
        model.addAttribute("addMoneyTransactionWithBankAccountDTO", addMoneyTransactionWithBankAccountDTO);
        try {
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
            return "BankTransferPage";
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return "SignInPage";
        }
    }

    @PostMapping("BankTransferPage")
    public String submitBankTransferPage(@ModelAttribute("addMoneyTransactionWithBankAccountDTO") AddMoneyTransactionWithBankAccountDTO addMoneyTransactionWithBankAccountDTO,
                                         @ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                         Model model){
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
//        try {
//            frontService.addMoneyTransactionBetweenUser(userAccount.getId(),confirmMoneyTransactionWithUserAccountDTO);
//        } catch (IllegalArgumentException e){
//            System.out.println(e.getMessage());
//        }
        System.out.println(confirmMoneyTransactionWithBankAccountDTO);
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("transaction", frontService.getMoneyTransactionListByUserId(userAccount.getId()));
        return "HomePage";
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
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
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
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
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
            model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
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
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
        return "BankListPage";
    }
}
