package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.customexception.NotEnoughMoneyException;
import com.noelh.paymybuddy.customexception.UserAccountNotFoundException;
import com.noelh.paymybuddy.dto.ConfirmMoneyTransactionWithUserAccountDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.repository.MoneyTransactionWithUserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Money transaction with user account service class
 */
@Service
public class MoneyTransactionWithUserAccountService {

    @Autowired
    private MoneyTransactionWithUserAccountRepository moneyTransactionWithUserAccountRepository;

    @Autowired
    private UserAccountService userAccountService;

    /**
     * Add a new money transaction with user account
     * @param id is the user account id
     * @param confirmMoneyTransactionWithUserAccountDTO is the info of the transaction
     * @throws NotEnoughMoneyException if the user lack money for the transaction
     * @throws UserAccountNotFoundException if the target user don't exist in the database
     */
    @Transactional
    public void addMoneyTransactionWithUserAccount(Long id, ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO) throws NotEnoughMoneyException, UserAccountNotFoundException {
        if (userAccountService.getUserAccount(id).getBalance()<confirmMoneyTransactionWithUserAccountDTO.getTotalAmount()){
            throw new NotEnoughMoneyException("Not enough money in your account");
        }

        if (!userAccountService.existsUserAccountByLoginMail(confirmMoneyTransactionWithUserAccountDTO.getLoginMail())){
            throw new UserAccountNotFoundException("User "+confirmMoneyTransactionWithUserAccountDTO.getLoginMail()+" don't exist");
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
