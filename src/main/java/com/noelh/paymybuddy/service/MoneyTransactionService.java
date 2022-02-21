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

@Service
public class MoneyTransactionService {

    @Autowired
    private UserAccountService userAccountService;

    private static final double TAX_NUMBER = 0.5;

    public double findTaxAmount(double amount){
        return (amount*TAX_NUMBER)/100;
    }

    public List<MoneyTransactionDTO> getMoneyTransactionListByUserAccountId(long id){
        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();

        List<MoneyTransactionDTO> moneyTransactionDTOFromBank = mapToMoneyTransaction(userAccountService.getUserAccount(id).getMoneyTransactionWithBankAccountList());

        List<MoneyTransactionDTO> moneyTransactionDTOFromUser = mapToMoneyTransaction(id, userAccountService.getUserAccount(id).getMoneyTransactionWithUserAccountList());

        moneyTransactionDTOList.addAll(moneyTransactionDTOFromBank);
        moneyTransactionDTOList.addAll(moneyTransactionDTOFromUser);
        moneyTransactionDTOList.sort(Comparator.comparing(MoneyTransactionDTO::getDate).reversed());
        return moneyTransactionDTOList;
    }

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
                                moneyTransactionWithBankAccount.getAmount()-(findTaxAmount(moneyTransactionWithBankAccount.getAmount())) :
                                moneyTransactionWithBankAccount.getAmount()+(findTaxAmount(moneyTransactionWithBankAccount.getAmount()))
                ))
                .collect(Collectors.toList());
    }

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
                        moneyTransactionWithUserAccount.getAmount()+(findTaxAmount(moneyTransactionWithUserAccount.getAmount()))
                ))
                .collect(Collectors.toList());
    }
}
