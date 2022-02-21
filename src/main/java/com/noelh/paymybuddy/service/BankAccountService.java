package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public boolean isBankAccountExist(String iban) {
        return bankAccountRepository.existsBankAccountByIban(iban);
    }

    public BankAccount getBankAccountByIban(String iban){
        return bankAccountRepository.getBankAccountByIban(iban);
    }

    public BankAccount addBankAccount(String iban) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(iban);
        return bankAccountRepository.save(bankAccount);
    }
}
