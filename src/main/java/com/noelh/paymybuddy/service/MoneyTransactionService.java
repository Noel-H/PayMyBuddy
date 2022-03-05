package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.MoneyTransactionDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MoneyTransaction service class
 */
@Service
public class MoneyTransactionService {

    @Autowired
    private UserAccountService userAccountService;

    private static final double TAX_NUMBER = 0.5;

    /**
     * find a tax amount by the amount
     * @param amount is the amount
     * @return a double for the result
     */
    public double findTaxAmount(double amount){
        return userAccountService.roundedAmount((amount*TAX_NUMBER)/100);
    }

    /**
     * fuze a list of user transaction and bank transaction into one transaction list
     * @param id is the user account id to get the list
     * @return a list of MoneyTransactionDTO
     */
    public List<MoneyTransactionDTO> getMoneyTransactionListByUserAccountId(long id){
        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();

        List<MoneyTransactionDTO> moneyTransactionDTOFromBank = mapToMoneyTransaction(userAccountService.getUserAccount(id).getMoneyTransactionWithBankAccountList());

        List<MoneyTransactionDTO> moneyTransactionDTOFromUser = mapToMoneyTransaction(id, userAccountService.getUserAccount(id).getMoneyTransactionWithUserAccountList());

        moneyTransactionDTOList.addAll(moneyTransactionDTOFromBank);
        moneyTransactionDTOList.addAll(moneyTransactionDTOFromUser);
        moneyTransactionDTOList.sort(Comparator.comparing(MoneyTransactionDTO::getDate).reversed());
        return moneyTransactionDTOList;
    }

    /**
     * map a list of bank transaction into a list of transaction
     * @param moneyTransactionWithBankAccountList is the list of bank transaction
     * @return a list of MoneyTransactionDTO
     */
    public List<MoneyTransactionDTO> mapToMoneyTransaction(List<MoneyTransactionWithBankAccount> moneyTransactionWithBankAccountList) {
        return moneyTransactionWithBankAccountList.stream()
                .map(moneyTransactionWithBankAccount -> new MoneyTransactionDTO(
                        moneyTransactionWithBankAccount.getDate(),
                        moneyTransactionWithBankAccount.getBank().getIban(),
                        moneyTransactionWithBankAccount.getAmount(),
                        findTaxAmount(moneyTransactionWithBankAccount.getAmount()),
                        moneyTransactionWithBankAccount.getWithdraw() ? "table-success" : "table-danger",
                        moneyTransactionWithBankAccount.getWithdraw() ? "+" : "-",
                        false,
                        moneyTransactionWithBankAccount.getWithdraw() ?
                                userAccountService.roundedAmount(moneyTransactionWithBankAccount.getAmount()-(findTaxAmount(moneyTransactionWithBankAccount.getAmount()))) :
                                userAccountService.roundedAmount(moneyTransactionWithBankAccount.getAmount()+(findTaxAmount(moneyTransactionWithBankAccount.getAmount())))
                ))
                .collect(Collectors.toList());
    }

    /**
     * map a list of user transaction into a list of transaction
     * @param id is the user account id
     * @param moneyTransactionWithUserAccountList is the list of user transaction
     * @return a list of MoneyTransactionDTO
     */
    public List<MoneyTransactionDTO> mapToMoneyTransaction(long id, List<MoneyTransactionWithUserAccount> moneyTransactionWithUserAccountList){
        return moneyTransactionWithUserAccountList.stream()
                .map(moneyTransactionWithUserAccount -> new MoneyTransactionDTO(
                        moneyTransactionWithUserAccount.getDate(),
                        moneyTransactionWithUserAccount.getSender().getLoginMail().equals(userAccountService.getUserAccount(id).getLoginMail()) ?
                                moneyTransactionWithUserAccount.getReceiver().getLoginMail() :
                                moneyTransactionWithUserAccount.getSender().getLoginMail(),
                        moneyTransactionWithUserAccount.getAmount(),
                        findTaxAmount(moneyTransactionWithUserAccount.getAmount()),
                        moneyTransactionWithUserAccount.getSender().getLoginMail().equals(userAccountService.getUserAccount(id).getLoginMail()) ? "table-danger" : "table-success",
                        moneyTransactionWithUserAccount.getSender().getLoginMail().equals(userAccountService.getUserAccount(id).getLoginMail()) ? "-" : "+",
                        true,
                        userAccountService.roundedAmount(moneyTransactionWithUserAccount.getAmount()+(findTaxAmount(moneyTransactionWithUserAccount.getAmount())))
                ))
                .collect(Collectors.toList());
    }
}
