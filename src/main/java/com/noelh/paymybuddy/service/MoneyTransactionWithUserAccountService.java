package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.ConfirmMoneyTransactionWithUserAccountDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.repository.MoneyTransactionWithUserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MoneyTransactionWithUserAccountService {

    @Autowired
    private MoneyTransactionWithUserAccountRepository moneyTransactionWithUserAccountRepository;

    @Autowired
    private UserAccountService userAccountService;

    public void addMoneyTransactionWithUserAccount(Long id, ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO) throws IllegalArgumentException{
        if (userAccountService.getUserAccount(id).getBalance()<confirmMoneyTransactionWithUserAccountDTO.getTotalAmount()){
            throw new IllegalArgumentException("Not enough money in your account");
        }

        if (!userAccountService.existsUserAccountByLoginMail(confirmMoneyTransactionWithUserAccountDTO.getLoginMail())){
            throw new IllegalArgumentException("User "+confirmMoneyTransactionWithUserAccountDTO.getLoginMail()+" don't exist");
        }

        MoneyTransactionWithUserAccount moneyTransactionWithUserAccount = new MoneyTransactionWithUserAccount();
        moneyTransactionWithUserAccount.setDate(LocalDateTime.now());
        moneyTransactionWithUserAccount.setAmount(confirmMoneyTransactionWithUserAccountDTO.getAmount());
        moneyTransactionWithUserAccount.setTaxAmount(confirmMoneyTransactionWithUserAccountDTO.getTaxAmount());
        moneyTransactionWithUserAccount.setSender(userAccountService.getUserAccount(id));
        moneyTransactionWithUserAccount.setReceiver(userAccountService.getUserAccountByLoginMail(confirmMoneyTransactionWithUserAccountDTO.getLoginMail()));

        userAccountService.addMoneyTransactionWithUser(userAccountService.getUserAccount(id),
                confirmMoneyTransactionWithUserAccountDTO.getTotalAmount(),
                userAccountService.getUserAccountByLoginMail(confirmMoneyTransactionWithUserAccountDTO.getLoginMail()),
                confirmMoneyTransactionWithUserAccountDTO.getAmount(),
                moneyTransactionWithUserAccountRepository.save(moneyTransactionWithUserAccount));
    }
}
