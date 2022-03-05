package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.customexception.*;
import com.noelh.paymybuddy.dto.*;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Main Controller
 */
@Slf4j
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

    /**
     * Get / page
     * @param model used for the html template
     * @return Home.html
     */
    @GetMapping("/")
    public String getIndex(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("GET / : Id = "+userAccount.getId());
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    /**
     * Get login page
     * @return login.html
     */
    @GetMapping("/login")
    public String getLogin(){
        log.info("GET /login");
        return "login";
    }

    /**
     * Get Sign up page
     * @param model used for the html template
     * @return SignUp.html
     */
    @GetMapping("/SignUp")
    public String getSignUpPage(Model model){
        log.info("GET /SignUp");
        model.addAttribute("signUpDTO", new SignUpDTO());
        return "SignUp";
    }

    /**
     * Post request to add a new account
     * @param signUpDTO used for the html template
     * @return login.html or SignUp.html if an error is caught
     */
    @PostMapping("/SignUp")
    public String submitSignUpPage(@ModelAttribute("signUpDTO") SignUpDTO signUpDTO){
        log.info("POST /SignUp");
        try{
            userAccountService.addUserAccountByMailAndPassword(signUpDTO);
        }catch (LoginMailAlreadyExistException e){
            log.error("POST /SignUP : ERROR = "+e.getMessage());
            return "SignUp";
        }
        return "login";
    }

    /**
     * Get Home page
     * @param model used for the html template
     * @return Home.html
     */
    @GetMapping("/Home")
    public String getHome(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("GET /Home : Id = "+userAccount.getId());
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    /**
     * Get The User Transfer page
     * @param model used for the html template
     * @return UserTransfer.html
     */
    @GetMapping("/UserTransfer")
    public String getUserTransfer(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("GET /UserTransfer : Id = "+userAccount.getId());
        model.addAttribute("addMoneyTransactionWithUserAccountDTO", new AddMoneyTransactionWithUserAccountDTO());
        model.addAttribute("userAccount", userAccount);
        return "UserTransfer";
    }

    /**
     * Post request to confirm a user transaction
     * @param confirmMoneyTransactionWithUserAccountDTO used for the html template
     * @param model used for the html template
     * @return UserTransferConfirmation.html
     */
    @PostMapping("/UserTransfer")
    public String submitUserTransfer(@ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                     Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("POST /UserTransfer : Id = "+userAccount.getId());
        confirmMoneyTransactionWithUserAccountDTO.setTaxAmount(userAccountService.roundedAmount(moneyTransactionService.findTaxAmount(confirmMoneyTransactionWithUserAccountDTO.getAmount())));
        confirmMoneyTransactionWithUserAccountDTO.setTotalAmount(userAccountService.roundedAmount(confirmMoneyTransactionWithUserAccountDTO.getAmount()+confirmMoneyTransactionWithUserAccountDTO.getTaxAmount()));
        model.addAttribute("confirmTransactionRecap", confirmMoneyTransactionWithUserAccountDTO);
        model.addAttribute("userAccount", userAccount);
        return "UserTransferConfirmation";
    }

    /**
     * Post request to add a new user transaction
     * @param confirmMoneyTransactionWithUserAccountDTO used for the html template
     * @param model used for the html template
     * @return Home.html or UserTransfer.html if an error is caught
     */
    @PostMapping("/UserTransferConfirmation")
    public String submitUserTransferConfirmation(@ModelAttribute("confirmMoneyTransactionWithUserAccountDTO")ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO,
                                                 Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("POST /UserTransferConfirmation : Id = "+userAccount.getId());
        try{
            moneyTransactionWithUserAccountService.addMoneyTransactionWithUserAccount(userAccount.getId(),confirmMoneyTransactionWithUserAccountDTO);
        }catch (NotEnoughMoneyException | UserAccountNotFoundException e){
            log.error("POST /UserTransferConfirmation : Id = "+userAccount.getId()+" : ERROR = "+e.getMessage());
            model.addAttribute("addMoneyTransactionWithUserAccountDTO", new AddMoneyTransactionWithUserAccountDTO());
            model.addAttribute("userAccount", userAccount);
            return "UserTransfer";
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    /**
     * Get Bank Transfer page
     * @param model used for the html template
     * @return BankTransfer.html
     */
    @GetMapping("/BankTransfer")
    public String getBankTransfer(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("GET /BankTransfer : Id = "+userAccount.getId());
        model.addAttribute("addMoneyTransactionWithBankAccountDTO", new AddMoneyTransactionWithBankAccountDTO());
        model.addAttribute("userAccount", userAccount);
        return "BankTransfer";
    }

    /**
     * Post request to confirm a Bank transaction
     * @param confirmMoneyTransactionWithBankAccountDTO used for the html template
     * @param model used for the html template
     * @return BankTransferConfirmation.html
     */
    @PostMapping("/BankTransfer")
    public String submitBankTransfer(@ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                     Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("POST /BankTransfer : Id = "+userAccount.getId());
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(userAccountService.roundedAmount(moneyTransactionService.findTaxAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount())));
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(userAccountService.roundedAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount()+confirmMoneyTransactionWithBankAccountDTO.getTaxAmount()));
        model.addAttribute("confirmTransactionRecap",confirmMoneyTransactionWithBankAccountDTO);
        model.addAttribute("withdraw",confirmMoneyTransactionWithBankAccountDTO.isWithdraw());
        model.addAttribute("withdrawStatus",moneyTransactionWithBankAccountService.getWithdrawStatus(confirmMoneyTransactionWithBankAccountDTO.isWithdraw()));
        model.addAttribute("userAccount", userAccount);
        return "BankTransferConfirmation";
    }

    /**
     * Post request to add a new Bank transaction
     * @param confirmMoneyTransactionWithBankAccountDTO used for the html template
     * @param model used for the html template
     * @return Home.html or BankTransfer.html if an error is caught
     */
    @PostMapping("/BankTransferConfirmation")
    public String submitBankTransferConfirmation(@ModelAttribute("confirmMoneyTransactionWithBankAccountDTO")ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO,
                                                 Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("POST /BankTransferConfirmation : Id = "+userAccount.getId());
        try {
            moneyTransactionWithBankAccountService.addMoneyTransactionWithBank(userAccount.getId(),confirmMoneyTransactionWithBankAccountDTO);
        }catch (BankNotFoundInBankAccountListException | NotEnoughMoneyException e){
            log.error("POST /BankTransferConfirmation : Id = "+userAccount.getId()+" : ERROR = "+e.getMessage());
            model.addAttribute("addMoneyTransactionWithBankAccountDTO", new AddMoneyTransactionWithBankAccountDTO());
            model.addAttribute("userAccount", userAccount);
            return "BankTransfer";
        }
        model.addAttribute("userAccount", userAccount);
        model.addAttribute("transaction", moneyTransactionService.getMoneyTransactionListByUserAccountId(userAccount.getId()));
        return "Home";
    }

    /**
     * Get Friend list page
     * @param model used for the html template
     * @return FriendList.html
     */
    @GetMapping("/FriendList")
    public String getFriendListPage(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("GET /FriendList : Id = "+userAccount.getId());
        model.addAttribute("addFriendDTO", new AddFriendDTO());
        model.addAttribute("userAccount", userAccount);
        return "FriendList";
    }

    /**
     * Post request to add a new friend
     * @param addFriendDTO used for the html template
     * @param model used for the html template
     * @return FriendList.html
     */
    @PostMapping("/FriendList")
    public String submitFriendListPage(@ModelAttribute("addFriendDTO") AddFriendDTO addFriendDTO, Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("POST /FriendList : Id = "+userAccount.getId());
        try{
            userAccountService.addFriendByUserAccountId(userAccount.getId(), addFriendDTO.getLoginMail());
        }catch (UserAccountNotFoundException | LoginMailAlreadyAddedException e){
            log.error("POST /FriendList : Id = "+userAccount.getId()+" : ERROR = "+e.getMessage());
            model.addAttribute("userAccount", userAccount);
            return "FriendList";
        }
        model.addAttribute("userAccount", userAccount);
        return "FriendList";
    }

    /**
     * Get Bank list page
     * @param model used for the html template
     * @return BankList.html
     */
    @GetMapping("/BankList")
    public String getBankListPagePage(Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("GET /BankList : Id = "+userAccount.getId());
        model.addAttribute("addBankDTO", new AddBankDTO());
        model.addAttribute("userAccount", userAccount);
        return "BankList";
    }

    /**
     * Post request to add a new bank
     * @param addBankDTO used for the html template
     * @param model used for the html template
     * @return BankList.html
     */
    @PostMapping("/BankList")
    public String submitBankListPage(@ModelAttribute("addBankDTO") AddBankDTO addBankDTO, Model model){
        UserAccount userAccount = userAccountService.findUserAccountByAuthentication();
        log.info("POST /BankList : Id = "+userAccount.getId());
        try {
            userAccountService.addBankAccountByUserAccountId(userAccount.getId(), addBankDTO.getIban());
        }catch (BankAccountAlreadyAddedException | BankAccountAlreadyUsedException e){
            log.error("POST /BankList : Id = "+userAccount.getId()+" : ERROR = "+e.getMessage());
            model.addAttribute("userAccount", userAccount);
            return "BankList";
        }
        model.addAttribute("userAccount", userAccount);
        return "BankList";
    }
}
