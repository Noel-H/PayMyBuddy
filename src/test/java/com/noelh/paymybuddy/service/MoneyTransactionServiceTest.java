package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.dto.MoneyTransactionDTO;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.model.UserAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyTransactionServiceTest {

    @Mock
    private UserAccountService userAccountService;

    @InjectMocks
    private MoneyTransactionService moneyTransactionService;

    @Test
    void findTaxAmount_Should_Return_0_5() {
        //Given
        double expectedResult = 0.5;
        when(userAccountService.roundedAmount(0.5)).thenReturn(expectedResult);

        //When
        double result = moneyTransactionService.findTaxAmount(100);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void getMoneyTransactionListByUserAccountId_Should_Return_ExpectedResult() {
        //Given
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setLoginMail("test@test.test");

        UserAccount userAccount2 = new UserAccount();
        userAccount2.setId(2L);
        userAccount2.setLoginMail("test2@test.test");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");

        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = new MoneyTransactionWithBankAccount();
        moneyTransactionWithBankAccount.setId(1L);
        moneyTransactionWithBankAccount.setBank(bankAccount);
        moneyTransactionWithBankAccount.setUser(userAccount);
        moneyTransactionWithBankAccount.setAmount(100.0);
        moneyTransactionWithBankAccount.setWithdraw(true);
        moneyTransactionWithBankAccount.setDate(LocalDateTime.of(2022,3,15,16,1));
        List<MoneyTransactionWithBankAccount> moneyTransactionWithBankAccountList = new ArrayList<>();
        moneyTransactionWithBankAccountList.add(moneyTransactionWithBankAccount);
        userAccount.setMoneyTransactionWithBankAccountList(moneyTransactionWithBankAccountList);

        MoneyTransactionWithUserAccount moneyTransactionWithUserAccount = new MoneyTransactionWithUserAccount();
        moneyTransactionWithUserAccount.setId(1L);
        moneyTransactionWithUserAccount.setDate(LocalDateTime.of(2022,3,15,16,0));
        moneyTransactionWithUserAccount.setAmount(500.0);
        moneyTransactionWithUserAccount.setSender(userAccount2);
        moneyTransactionWithUserAccount.setReceiver(userAccount);
        List<MoneyTransactionWithUserAccount> moneyTransactionWithUserAccountList = new ArrayList<>();
        moneyTransactionWithUserAccountList.add(moneyTransactionWithUserAccount);
        userAccount.setMoneyTransactionWithUserAccountList(moneyTransactionWithUserAccountList);

        MoneyTransactionDTO moneyTransactionDTO = new MoneyTransactionDTO(
                LocalDateTime.of(2022,3,15,16,1),
                "TESTIBAN",
                100.0,
                10.0,
                "table-success",
                "+",
                false,
                10.0);

        MoneyTransactionDTO moneyTransactionDTO2 = new MoneyTransactionDTO(
                LocalDateTime.of(2022,3,15,16,0),
                "test2@test.test",
                500.0,
                10.0,
                "table-success",
                "+",
                true,
                10.0);

        List<MoneyTransactionDTO> expectedResult = new ArrayList<>();
        expectedResult.add(moneyTransactionDTO);
        expectedResult.add(moneyTransactionDTO2);

        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        when(userAccountService.roundedAmount(anyDouble())).thenReturn(10.0);

        //When
        List<MoneyTransactionDTO> result = moneyTransactionService.getMoneyTransactionListByUserAccountId(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void getMoneyTransactionListByUserAccountId_Should_Return_ExpectedResult_2() {
        //Given
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setLoginMail("test@test.test");

        UserAccount userAccount2 = new UserAccount();
        userAccount2.setId(2L);
        userAccount2.setLoginMail("test2@test.test");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");

        MoneyTransactionWithBankAccount moneyTransactionWithBankAccount = new MoneyTransactionWithBankAccount();
        moneyTransactionWithBankAccount.setId(1L);
        moneyTransactionWithBankAccount.setBank(bankAccount);
        moneyTransactionWithBankAccount.setUser(userAccount);
        moneyTransactionWithBankAccount.setAmount(100.0);
        moneyTransactionWithBankAccount.setWithdraw(false);
        moneyTransactionWithBankAccount.setDate(LocalDateTime.of(2022,3,15,16,1));
        List<MoneyTransactionWithBankAccount> moneyTransactionWithBankAccountList = new ArrayList<>();
        moneyTransactionWithBankAccountList.add(moneyTransactionWithBankAccount);
        userAccount.setMoneyTransactionWithBankAccountList(moneyTransactionWithBankAccountList);

        MoneyTransactionWithUserAccount moneyTransactionWithUserAccount = new MoneyTransactionWithUserAccount();
        moneyTransactionWithUserAccount.setId(1L);
        moneyTransactionWithUserAccount.setDate(LocalDateTime.of(2022,3,15,16,0));
        moneyTransactionWithUserAccount.setAmount(500.0);
        moneyTransactionWithUserAccount.setSender(userAccount);
        moneyTransactionWithUserAccount.setReceiver(userAccount2);
        List<MoneyTransactionWithUserAccount> moneyTransactionWithUserAccountList = new ArrayList<>();
        moneyTransactionWithUserAccountList.add(moneyTransactionWithUserAccount);
        userAccount.setMoneyTransactionWithUserAccountList(moneyTransactionWithUserAccountList);

        MoneyTransactionDTO moneyTransactionDTO = new MoneyTransactionDTO(
                LocalDateTime.of(2022,3,15,16,1),
                "TESTIBAN",
                100.0,
                10.0,
                "table-danger",
                "-",
                false,
                10.0);

        MoneyTransactionDTO moneyTransactionDTO2 = new MoneyTransactionDTO(
                LocalDateTime.of(2022,3,15,16,0),
                "test2@test.test",
                500.0,
                10.0,
                "table-danger",
                "-",
                true,
                10.0);

        List<MoneyTransactionDTO> expectedResult = new ArrayList<>();
        expectedResult.add(moneyTransactionDTO);
        expectedResult.add(moneyTransactionDTO2);

        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        when(userAccountService.roundedAmount(anyDouble())).thenReturn(10.0);

        //When
        List<MoneyTransactionDTO> result = moneyTransactionService.getMoneyTransactionListByUserAccountId(1L);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }
}