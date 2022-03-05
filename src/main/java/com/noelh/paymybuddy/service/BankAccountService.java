package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BankAccount service class
 */
@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * Check if a bank account already exist in the database
     * @param iban is the iban of the bank
     * @return true if the bank account already exist in the database
     */
    public boolean isBankAccountExist(String iban) {
        return bankAccountRepository.existsBankAccountByIban(iban);
    }

    /**
     * Get a bank account by his iban
     * @param iban is the iban of the bank
     * @return the BankAccount
     */
    public BankAccount getBankAccountByIban(String iban){
        return bankAccountRepository.getBankAccountByIban(iban);
    }

    /**
     * Add a new bank account
     * @param iban is the iban of the bank
     * @return a BankAccount
     */
    public BankAccount addBankAccount(String iban) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(iban);
        return bankAccountRepository.save(bankAccount);
    }
}
