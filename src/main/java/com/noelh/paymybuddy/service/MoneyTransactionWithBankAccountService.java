package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.customexception.BankNotFoundInBankAccountListException;
import com.noelh.paymybuddy.customexception.NotEnoughMoneyException;
import com.noelh.paymybuddy.dto.ConfirmMoneyTransactionWithBankAccountDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.repository.MoneyTransactionWithBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Money transaction with bank account service class
 */
@Service
public class MoneyTransactionWithBankAccountService {

    @Autowired
    private MoneyTransactionWithBankAccountRepository moneyTransactionWithBankAccountRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Add a new money transaction with bank
     * @param id is the user account id
     * @param confirmMoneyTransactionWithBankAccountDTO is the info of the transaction
     * @throws BankNotFoundInBankAccountListException if the bank don't exist in the bank account list of the user
     * @throws NotEnoughMoneyException if the user lack money for the transaction
     */
    @Transactional
    public void addMoneyTransactionWithBank(Long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO) throws BankNotFoundInBankAccountListException, NotEnoughMoneyException {
        if (confirmMoneyTransactionWithBankAccountDTO.isWithdraw()){
            addWithdrawMoneyTransaction(id,confirmMoneyTransactionWithBankAccountDTO);
        } else {
            addDepositMoneyTransaction(id,confirmMoneyTransactionWithBankAccountDTO);
        }
    }

    /**
     * Add a new money transaction with bank for a withdraw
     * @param id is the user account id
     * @param confirmMoneyTransactionWithBankAccountDTO is the info of the transaction
     * @throws BankNotFoundInBankAccountListException if the bank don't exist in the bank account list of the user
     */
    public void addWithdrawMoneyTransaction(Long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO) throws BankNotFoundInBankAccountListException {
        if (!userAccountService.getUserAccount(id).getBankAccountList().contains(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()))){
            throw new BankNotFoundInBankAccountListException("Bank "+confirmMoneyTransactionWithBankAccountDTO.getIban()+" is not in your bank account list");
        }

        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = mapToMoneyTransactionWithBankAccount(id,confirmMoneyTransactionWithBankAccountDTO);

        userAccountService.addWithdrawMoneyTransactionWithBank(userAccountService.getUserAccount(id),moneyTransactionWithBankAccountRepository.save(moneyTransactionWithBankAccount));
    }

    /**
     * Add a new money transaction with bank for a deposit
     * @param id is the user account id
     * @param confirmMoneyTransactionWithBankAccountDTO is the info of the transaction
     * @throws BankNotFoundInBankAccountListException if the bank don't exist in the bank account list of the user
     * @throws NotEnoughMoneyException if the user lack money for the transaction
     */
    public void addDepositMoneyTransaction(Long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO) throws BankNotFoundInBankAccountListException, NotEnoughMoneyException {
        if (!userAccountService.getUserAccount(id).getBankAccountList().contains(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()))){
            throw new BankNotFoundInBankAccountListException("Bank "+confirmMoneyTransactionWithBankAccountDTO.getIban()+" is not in your bank account list");
        }

        if (userAccountService.getUserAccount(id).getBalance()<confirmMoneyTransactionWithBankAccountDTO.getTotalAmount()){
            throw new NotEnoughMoneyException("Not enough Money in your account");
        }

        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = mapToMoneyTransactionWithBankAccount(id, confirmMoneyTransactionWithBankAccountDTO);

        userAccountService.addDepositMoneyTransactionWithBank(userAccountService.getUserAccount(id),moneyTransactionWithBankAccountRepository.save(moneyTransactionWithBankAccount));
    }

    /**
     * Get a status based on if the transaction is a withdraw or deposit
     * @param withdraw is a boolean
     * @return the String "checked" if withdraw is true or null if not
     */
    public String getWithdrawStatus(boolean withdraw) {
        if (withdraw){
            return "checked";
        } else {
            return null;
        }
    }

    /**
     * Map a confirm money transaction with bank into a Money transaction with bank
     * @param id is the user account id
     * @param confirmMoneyTransactionWithBankAccountDTO is the info of the transaction
     * @return the Money transaction with bank account
     */
    public MoneyTransactionWithBankAccount mapToMoneyTransactionWithBankAccount(long id, ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO){
        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = new MoneyTransactionWithBankAccount();
        moneyTransactionWithBankAccount.setDate(LocalDateTime.now());
        moneyTransactionWithBankAccount.setAmount(confirmMoneyTransactionWithBankAccountDTO.getAmount());
        moneyTransactionWithBankAccount.setTaxAmount(confirmMoneyTransactionWithBankAccountDTO.getTaxAmount());
        moneyTransactionWithBankAccount.setBank(bankAccountService.getBankAccountByIban(confirmMoneyTransactionWithBankAccountDTO.getIban()));
        moneyTransactionWithBankAccount.setUser(userAccountService.getUserAccount(id));
        moneyTransactionWithBankAccount.setWithdraw(confirmMoneyTransactionWithBankAccountDTO.isWithdraw());
        return moneyTransactionWithBankAccount;
    }
}
