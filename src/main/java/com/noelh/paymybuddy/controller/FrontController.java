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
public class FrontController {

    @Autowired
    private FrontService frontService;

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
            frontService.addUserAccountByMailAndPassword(signUpDTO);
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
            frontService.addMoneyTransactionWithBank(userAccount.getId(),confirmMoneyTransactionWithBankAccountDTO);
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

    @GetMapping("FriendListPage")
    public String getFriendListPage(Model model){
        AddFriendDTO addFriendDTO = new AddFriendDTO();
        model.addAttribute("addFriendDTO",addFriendDTO);
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("friendList",frontService.getFriendListByUser(userAccount));
        return "FriendListPage";
    }

    @PostMapping("FriendListPage")
    public String submitFriendListPage(@ModelAttribute("addFriendDTO") AddFriendDTO addFriendDTO, Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        frontService.addFriendByUserAccountId(userAccount.getId(), addFriendDTO.getLoginMail());
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("friendList",frontService.getFriendListByUser(userAccount));
        return "FriendListPage";
    }

    @GetMapping("BankListPage")
    public String getBankListPagePage(Model model){
        AddBankDTO addBankDTO = new AddBankDTO();
        model.addAttribute("addBankDTO", addBankDTO);
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
        return "BankListPage";
    }

    @PostMapping("BankListPage")
    public String submitBankListPage(@ModelAttribute("addBankDTO") AddBankDTO addBankDTO, Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        frontService.addBankAccountByUserAccountId(userAccount.getId(), addBankDTO.getIban());
        model.addAttribute("userAccount", frontService.getUserAccountById(userAccount.getId()));
        model.addAttribute("bankAccountList",frontService.getBankAccountListByUser(userAccount));
        return "BankListPage";
    }
}
