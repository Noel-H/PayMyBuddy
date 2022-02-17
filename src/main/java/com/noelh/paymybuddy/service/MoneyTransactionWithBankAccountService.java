package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.ConfirmMoneyTransactionWithBankAccountDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.repository.MoneyTransactionWithBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MoneyTransactionWithBankAccountService {

    @Autowired
    private MoneyTransactionWithBankAccountRepository moneyTransactionWithBankAccountRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private BankAccountService bankAccountService;

    public void addWithdrawMoneyTransaction(Long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO) {
        if (!userAccountService.getUserAccount(id).getBankAccountList().contains(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()))){
            throw new IllegalArgumentException("Bank "+confirmMoneyTransactionWithBankAccountDTO.getIban()+" is not in your bank account list");
        }

        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = new MoneyTransactionWithBankAccount();
        moneyTransactionWithBankAccount.setDate(LocalDateTime.now());
        moneyTransactionWithBankAccount.setAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount());
        moneyTransactionWithBankAccount.setTaxAmount(confirmMoneyTransactionWithBankAccountDTO.getTaxAmount());
        moneyTransactionWithBankAccount.setBank(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()));
        moneyTransactionWithBankAccount.setUser(userAccountService.getUserAccount(id));
        moneyTransactionWithBankAccount.setWithdraw(confirmMoneyTransactionWithBankAccountDTO.isWithdraw());

        userAccountService.addWithdrawMoneyTransactionWithBank(userAccountService.getUserAccount(id),moneyTransactionWithBankAccountRepository.save(moneyTransactionWithBankAccount));

    }

    public void addDepositMoneyTransaction(Long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO) {
        if (!userAccountService.getUserAccount(id).getBankAccountList().contains(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()))){
            throw new IllegalArgumentException("Bank "+confirmMoneyTransactionWithBankAccountDTO.getIban()+" is not in your bank account list");
        }

        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = new MoneyTransactionWithBankAccount();
        moneyTransactionWithBankAccount.setDate(LocalDateTime.now());
        moneyTransactionWithBankAccount.setAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount());
        moneyTransactionWithBankAccount.setTaxAmount(confirmMoneyTransactionWithBankAccountDTO.getTaxAmount());
        moneyTransactionWithBankAccount.setBank(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()));
        moneyTransactionWithBankAccount.setUser(userAccountService.getUserAccount(id));
        moneyTransactionWithBankAccount.setWithdraw(confirmMoneyTransactionWithBankAccountDTO.isWithdraw());

        userAccountService.addDepositMoneyTransactionWithBank(userAccountService.getUserAccount(id),moneyTransactionWithBankAccountRepository.save(moneyTransactionWithBankAccount));
    }
}
