package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.MoneyTransactionDTO;
import com.noelh.paymybuddy.dto.SignInDTO;
import com.noelh.paymybuddy.dto.SignUpDTO;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FrontService {

    @Autowired
    private UserAccountService userAccountService;

    public UserAccount getUserAccountByMailAndPassword(SignInDTO signInDTO) {
        return userAccountService.getUserAccountByMailAndPassword(signInDTO);
    }

    public boolean isUserConnected(UserAccount userAccount){
        return userAccountService.isUserConnected(userAccount);
    }

    public UserAccount addUserAccountByMailAndPassword(SignUpDTO signUpDTO) {
        return userAccountService.addUserAccountByMailAndPassword(signUpDTO);
    }

    public List<MoneyTransactionDTO> getMoneyTransactionListByUserId(long id){
        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();
        List<MoneyTransactionDTO> moneyTransactionWithBankAccountListDTOList = userAccountService.getUserAccount(id).getMoneyTransactionWithBankAccountList().stream()
                .map(moneyTransactionWithBankAccount -> new MoneyTransactionDTO(
                        moneyTransactionWithBankAccount.getDate(),
                        moneyTransactionWithBankAccount.getBank().getIban(),
                        moneyTransactionWithBankAccount.getAmount(),
                        0.5*moneyTransactionWithBankAccount.getAmount()/100,
                        moneyTransactionWithBankAccount.getWithdraw() ? "table-success" : "table-danger",
                        moneyTransactionWithBankAccount.getWithdraw() ? "+" : "-",
                        false,
                        moneyTransactionWithBankAccount.getWithdraw() ?
                                moneyTransactionWithBankAccount.getAmount()-(0.5*moneyTransactionWithBankAccount.getAmount()/100) :
                                moneyTransactionWithBankAccount.getAmount()+(0.5*moneyTransactionWithBankAccount.getAmount()/100)
                ))
                .collect(Collectors.toList());
        List<MoneyTransactionDTO> moneyTransactionWithUserAccountListDTOList = userAccountService.getUserAccount(id).getMoneyTransactionWithUserAccountList().stream()
                .map(moneyTransactionWithUserAccount -> new MoneyTransactionDTO(
                        moneyTransactionWithUserAccount.getDate(),
                        moneyTransactionWithUserAccount.getSender().getLoginMail().equals(userAccountService.getUserAccount(id).getLoginMail()) ?
                                moneyTransactionWithUserAccount.getReceiver().getLoginMail() :
                                moneyTransactionWithUserAccount.getSender().getLoginMail(),
                        moneyTransactionWithUserAccount.getAmount(),
                        0.5*moneyTransactionWithUserAccount.getAmount()/100,
                        moneyTransactionWithUserAccount.getSender().getLoginMail().equals(userAccountService.getUserAccount(id).getLoginMail()) ? "table-danger" : "table-success",
                        moneyTransactionWithUserAccount.getSender().getLoginMail().equals(userAccountService.getUserAccount(id).getLoginMail()) ? "-" : "+",
                        true,
                        moneyTransactionWithUserAccount.getAmount()+(0.5*moneyTransactionWithUserAccount.getAmount()/100)
                ))
                .collect(Collectors.toList());

        moneyTransactionWithBankAccountListDTOList.forEach(moneyTransactionDTO -> moneyTransactionDTOList.add(moneyTransactionDTO) );
        moneyTransactionWithUserAccountListDTOList.forEach(moneyTransactionDTO -> moneyTransactionDTOList.add(moneyTransactionDTO) );
        Collections.sort(moneyTransactionDTOList, Comparator.comparing(MoneyTransactionDTO::getDate).reversed());
        return moneyTransactionDTOList;
    }

    public List<BankAccount> getBankAccountListByUser(UserAccount userAccount){
        return userAccountService.getBankaccountListbyUser(userAccount);
    }
}
