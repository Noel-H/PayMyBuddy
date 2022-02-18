package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.SignInDTO;
import com.noelh.paymybuddy.dto.SignUpDTO;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BankAccountService bankAccountService;

    public UserAccount getUserAccount(long id) {
        return userAccountRepository.getById(id);
    }

    public UserAccount getUserAccountByLoginMail(String loginMail){
        return userAccountRepository.getUserAccountByLoginMail(loginMail);
    }

    public boolean isUserConnected(UserAccount userAccount){
        return userAccount != null;
    }

    public UserAccount getUserAccountByMailAndPassword(SignInDTO signInDTO) {
        return userAccountRepository.findByLoginMailAndPassword(signInDTO.getLoginMail(),signInDTO.getPassword())
                .orElseThrow(()-> new NoSuchElementException("Echec lors de la connexion !"));
    }

    public UserAccount addUserAccountByMailAndPassword(SignUpDTO signUpDTO) {
        if(userAccountRepository.existsUserAccountByLoginMail(signUpDTO.getLoginMail())){
            throw new IllegalArgumentException("The login "+signUpDTO.getLoginMail()+" is already taken");
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setLoginMail(signUpDTO.getLoginMail());
        userAccount.setPassword(signUpDTO.getPassword());
        userAccount.setBalance(0.0);
        userAccount.setFriendList(new ArrayList<>());
        userAccount.setBankAccountList(new ArrayList<>());
        userAccount.setMoneyTransactionWithBankAccountList(new ArrayList<>());
        userAccount.setMoneyTransactionWithUserAccountList(new ArrayList<>());
        return userAccountRepository.save(userAccount);
    }

    public List<BankAccount> getBankaccountListbyUser(UserAccount userAccount){
        return userAccountRepository.getById(userAccount.getId()).getBankAccountList();
    }

    public boolean addBankAccountInUserAccount(Long userAccountId, String iban) {
        if (getUserAccount(userAccountId).getBankAccountList().contains(bankAccountService.getBankAccountByIban(iban))){
            throw new IllegalArgumentException("The Bank Account "+iban+" is already added");
        }
        if (bankAccountService.isBankAccountExist(iban)){
            throw new IllegalArgumentException("The Bank Account "+iban+" is already used");
        }
        getUserAccount(userAccountId).getBankAccountList().add(bankAccountService.addBankAccount(iban));
        userAccountRepository.save(getUserAccount(userAccountId));
        return true;
    }

    public List<UserAccount> getFriendListByUser(UserAccount userAccount) {
        return userAccountRepository.getById(userAccount.getId()).getFriendList();
    }

    public void addFriendByUserAccountId(Long userAccountId, String loginMail) {
        if (!userAccountRepository.existsUserAccountByLoginMail(loginMail)){
            throw new IllegalArgumentException("The login/Mail "+loginMail+" don't exist");
        }
        if (getUserAccount(userAccountId).getFriendList().contains(getUserAccountByLoginMail(loginMail))){
            throw new IllegalArgumentException("The login/Mail "+loginMail+" is already added");
        }
        getUserAccount(userAccountId).getFriendList().add(getUserAccountByLoginMail(loginMail));
        userAccountRepository.save(getUserAccount(userAccountId));
    }

    public boolean existsUserAccountByLoginMail(String loginMail){
        return userAccountRepository.existsUserAccountByLoginMail(loginMail);
    }

    public void addMoneyTransactionWithUser(UserAccount userAccount1,
                                            double moneySend,
                                            UserAccount userAccount2,
                                            double moneyReceive,
                                            MoneyTransactionWithUserAccount moneyTransactionWithUserAccount) {
        userAccount1.getMoneyTransactionWithUserAccountList().add(moneyTransactionWithUserAccount);
        userAccount1.setBalance(userAccount1.getBalance()-moneySend);
        userAccount2.getMoneyTransactionWithUserAccountList().add(moneyTransactionWithUserAccount);
        userAccount2.setBalance(userAccount2.getBalance()+moneyReceive);
        userAccountRepository.save(userAccount1);
        userAccountRepository.save(userAccount2);
    }

    public void addWithdrawMoneyTransactionWithBank(UserAccount userAccount, MoneyTransactionWithBankAccount moneyTransactionWithBankAccount) {
        userAccount.getMoneyTransactionWithBankAccountList().add(moneyTransactionWithBankAccount);
        userAccount.setBalance(userAccount.getBalance()+moneyTransactionWithBankAccount.getAmount());
        userAccountRepository.save(userAccount);
    }

    public void addDepositMoneyTransactionWithBank(UserAccount userAccount, MoneyTransactionWithBankAccount moneyTransactionWithBankAccount) {
        userAccount.getMoneyTransactionWithBankAccountList().add(moneyTransactionWithBankAccount);
        userAccount.setBalance(userAccount.getBalance()-(moneyTransactionWithBankAccount.getAmount()+moneyTransactionWithBankAccount.getTaxAmount()));
        userAccountRepository.save(userAccount);
    }
}
