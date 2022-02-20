package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.*;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrontService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MoneyTransactionWithUserAccountService moneyTransactionWithUserAccountService;

    @Autowired
    private MoneyTransactionWithBankAccountService moneyTransactionWithBankAccountService;

    public UserAccount getUserAccountById(long id){
        return userAccountService.getUserAccount(id);
    }

    public UserAccount getUserAccountByMailAndPassword(SignInDTO signInDTO) {
        return userAccountService.getUserAccountByMailAndPassword(signInDTO);
    }

    public boolean isUserConnected(UserAccount userAccount){
        return userAccountService.isUserConnected(userAccount);
    }

    public UserAccount addUserAccountByMailAndPassword(SignUpDTO signUpDTO) {
        return userAccountService.addUserAccountByMailAndPassword(signUpDTO);
    }

    public List<BankAccount> getBankAccountListByUser(UserAccount userAccount){
        return userAccountService.getBankaccountListbyUser(userAccount);
    }

    public boolean addBankAccountByUserAccountId(Long userAccountId, String iban) {
        return userAccountService.addBankAccountInUserAccount(userAccountId,iban);
    }

    public List<UserAccount> getFriendListByUser(UserAccount userAccount) {
        return userAccountService.getFriendListByUser(userAccount);
    }

    public void addFriendByUserAccountId(Long id, String loginMail) {
        userAccountService.addFriendByUserAccountId(id,loginMail);
    }

    public void addMoneyTransactionWithUserAccount(Long id, ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO) {
        moneyTransactionWithUserAccountService.addMoneyTransactionWithUserAccount(id,confirmMoneyTransactionWithUserAccountDTO);
    }

    public String getWithdrawStatus(boolean withdraw) {
        if (withdraw){
            return "checked";
        } else {
            return null;
        }
    }

    public void addMoneyTransactionWithBank(Long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO) {
        if (confirmMoneyTransactionWithBankAccountDTO.isWithdraw()){
            moneyTransactionWithBankAccountService.addWithdrawMoneyTransaction(id,confirmMoneyTransactionWithBankAccountDTO);
        } else {
            moneyTransactionWithBankAccountService.addDepositMoneyTransaction(id,confirmMoneyTransactionWithBankAccountDTO);
        }
    }
}
