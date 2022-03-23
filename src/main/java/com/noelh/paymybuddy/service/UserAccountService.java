package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.customexception.*;
import com.noelh.paymybuddy.dto.SignUpDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * User account service class
 */
@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Get a UserAccount by his id
     * @param id is the user account id
     * @return the UserAccount
     */
    public UserAccount getUserAccount(long id) {
        return userAccountRepository.getById(id);
    }

    /**
     * Get a UserAccount by his login mail
     * @param loginMail is the user account login mail
     * @return the UserAccount
     */
    public UserAccount getUserAccountByLoginMail(String loginMail){
        return userAccountRepository.getUserAccountByLoginMail(loginMail);
    }

    /**
     * Add a new user account with a mail and password
     * @param signUpDTO is the info for the new user account
     * @throws LoginMailAlreadyExistException if the login mail already exist in the database
     */
    @Transactional
    public void addUserAccountByMailAndPassword(SignUpDTO signUpDTO) throws LoginMailAlreadyExistException {
        if(userAccountRepository.existsUserAccountByLoginMail(signUpDTO.getLoginMail())){
            throw new LoginMailAlreadyExistException("The login "+signUpDTO.getLoginMail()+" is already taken");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        UserAccount userAccount = new UserAccount();
        userAccount.setLoginMail(signUpDTO.getLoginMail());
        userAccount.setPassword(bCryptPasswordEncoder.encode(signUpDTO.getPassword()));
        userAccount.setBalance(0.0);
        userAccount.setFriendList(new ArrayList<>());
        userAccount.setBankAccountList(new ArrayList<>());
        userAccount.setMoneyTransactionWithBankAccountList(new ArrayList<>());
        userAccount.setMoneyTransactionWithUserAccountList(new ArrayList<>());
        userAccountRepository.save(userAccount);
    }

    /**
     * Add a new bank account for a user
     * @param userAccountId is the user account id
     * @param iban is the iban of the bank
     * @throws BankAccountAlreadyAddedException if the bank already exist in the user list
     * @throws BankAccountAlreadyUsedException if the bank is already used by a user
     */
    @Transactional
    public void addBankAccountByUserAccountId(Long userAccountId, String iban) throws BankAccountAlreadyAddedException, BankAccountAlreadyUsedException {
        if (getUserAccount(userAccountId).getBankAccountList().contains(bankAccountService.getBankAccountByIban(iban))){
            throw new BankAccountAlreadyAddedException("The Bank Account "+iban+" is already added");
        }
        if (bankAccountService.isBankAccountExist(iban)){
            throw new BankAccountAlreadyUsedException("The Bank Account "+iban+" is already used");
        }
        getUserAccount(userAccountId).getBankAccountList().add(bankAccountService.addBankAccount(iban));
        userAccountRepository.save(getUserAccount(userAccountId));
    }

    /**
     * Add a new friend for a user
     * @param userAccountId is the user accound id
     * @param loginMail is the target login mail
     * @throws UserAccountNotFoundException if the login mail don't exist in the database
     * @throws LoginMailAlreadyAddedException if the login mail is already added in the user friend list
     */
    @Transactional
    public void addFriendByUserAccountId(Long userAccountId, String loginMail) throws UserAccountNotFoundException, LoginMailAlreadyAddedException {
        if (!userAccountRepository.existsUserAccountByLoginMail(loginMail)){
            throw new UserAccountNotFoundException("The login/Mail "+loginMail+" don't exist");
        }
        if (getUserAccount(userAccountId).getFriendList().contains(getUserAccountByLoginMail(loginMail))){
            throw new LoginMailAlreadyAddedException("The login/Mail "+loginMail+" is already added");
        }
        getUserAccount(userAccountId).getFriendList().add(getUserAccountByLoginMail(loginMail));
        userAccountRepository.save(getUserAccount(userAccountId));
    }

    /**
     * Check if the user exist by the login mail
     * @param loginMail is the login mail of the target
     * @return true if the user exist
     */
    public boolean existsUserAccountByLoginMail(String loginMail){
        return userAccountRepository.existsUserAccountByLoginMail(loginMail);
    }

    /**
     * Add a new money transaction with user in the user account
     * @param userAccount1 is the first user
     * @param moneySend is the money send
     * @param userAccount2 is the second user
     * @param moneyReceive is the money receive
     * @param moneyTransactionWithUserAccount is the info of the transaction
     */
    @Transactional
    public void addMoneyTransactionWithUser(UserAccount userAccount1,
                                            double moneySend,
                                            UserAccount userAccount2,
                                            double moneyReceive,
                                            MoneyTransactionWithUserAccount moneyTransactionWithUserAccount) {
        userAccount1.getMoneyTransactionWithUserAccountList().add(moneyTransactionWithUserAccount);
        userAccount1.setBalance(roundedAmount(userAccount1.getBalance()-moneySend));
        userAccountRepository.save(userAccount1);
        userAccount2.getMoneyTransactionWithUserAccountList().add(moneyTransactionWithUserAccount);
        userAccount2.setBalance(roundedAmount(userAccount2.getBalance()+moneyReceive));
        userAccountRepository.save(userAccount2);
    }

    /**
     * Add a new bank transaction withdraw
     * @param userAccount is the user account
     * @param moneyTransactionWithBankAccount is the transaction
     */
    public void addWithdrawMoneyTransactionWithBank(UserAccount userAccount, MoneyTransactionWithBankAccount moneyTransactionWithBankAccount) {
        userAccount.getMoneyTransactionWithBankAccountList().add(moneyTransactionWithBankAccount);
        userAccount.setBalance(roundedAmount(userAccount.getBalance()+(moneyTransactionWithBankAccount.getAmount()-moneyTransactionWithBankAccount.getTaxAmount())));
        userAccountRepository.save(userAccount);
    }

    /**
     * Add a new bank transaction deposit
     * @param userAccount is the user account
     * @param moneyTransactionWithBankAccount is the transaction
     */
    public void addDepositMoneyTransactionWithBank(UserAccount userAccount, MoneyTransactionWithBankAccount moneyTransactionWithBankAccount) {
        userAccount.getMoneyTransactionWithBankAccountList().add(moneyTransactionWithBankAccount);
        userAccount.setBalance(roundedAmount(userAccount.getBalance()-(moneyTransactionWithBankAccount.getAmount()+moneyTransactionWithBankAccount.getTaxAmount())));
        userAccountRepository.save(userAccount);
    }

    /**
     * Find a UserAccount by his authentication
     * @return a UserAccount
     */
    public UserAccount findUserAccountByAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserAccountByLoginMail(authentication.getName());
    }

    /**
     * Round the amount
     * @param amount is the amount to be rounded
     * @return the rounded amount
     */
    public double roundedAmount(double amount){
        return Math.round(amount*100)/100.0;
    }
}
