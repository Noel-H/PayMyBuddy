package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.customexception.BankNotFoundInBankAccountListException;
import com.noelh.paymybuddy.customexception.NotEnoughMoneyException;
import com.noelh.paymybuddy.dto.ConfirmMoneyTransactionWithBankAccountDTO;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.MoneyTransactionWithBankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoneyTransactionWithBankAccountServiceTest {

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private MoneyTransactionWithBankAccountRepository moneyTransactionWithBankAccountRepository;

    @InjectMocks
    private MoneyTransactionWithBankAccountService moneyTransactionWithBankAccountService;

    @Test
    void addMoneyTransactionWithBank() throws NotEnoughMoneyException, BankNotFoundInBankAccountListException {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccountList(bankAccountList);
        ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO = new ConfirmMoneyTransactionWithBankAccountDTO();
        confirmMoneyTransactionWithBankAccountDTO.setWithdraw(true);
        confirmMoneyTransactionWithBankAccountDTO.setIban("TESTIBAN");
        confirmMoneyTransactionWithBankAccountDTO.setAmount(100.0);
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(10.0);
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(110.0);

        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        when(moneyTransactionWithBankAccountRepository.save(any())).thenReturn(new MoneyTransactionWithBankAccount());
        doNothing().when(userAccountService).addWithdrawMoneyTransactionWithBank(any(),any());
        //When
        moneyTransactionWithBankAccountService.addMoneyTransactionWithBank(1L,confirmMoneyTransactionWithBankAccountDTO);

        //Then
        verify(userAccountService,times(1)).addWithdrawMoneyTransactionWithBank(any(),any());
    }

    @Test
    void addMoneyTransactionWithBank_Should_Throw_Exception_When_Withdraw(){
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccountList(new ArrayList<>());
        ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO = new ConfirmMoneyTransactionWithBankAccountDTO();
        confirmMoneyTransactionWithBankAccountDTO.setWithdraw(true);
        confirmMoneyTransactionWithBankAccountDTO.setIban("TESTIBAN");
        confirmMoneyTransactionWithBankAccountDTO.setAmount(100.0);
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(10.0);
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(110.0);

        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);

        //When
        //Then
        assertThrows(BankNotFoundInBankAccountListException.class, ()-> moneyTransactionWithBankAccountService.addWithdrawMoneyTransaction(1L, confirmMoneyTransactionWithBankAccountDTO));
    }

    @Test
    void addMoneyTransactionWithBank_Should_Make_Deposit() throws NotEnoughMoneyException, BankNotFoundInBankAccountListException {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(2000.0);
        userAccount.setBankAccountList(bankAccountList);
        ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO = new ConfirmMoneyTransactionWithBankAccountDTO();
        confirmMoneyTransactionWithBankAccountDTO.setWithdraw(false);
        confirmMoneyTransactionWithBankAccountDTO.setIban("TESTIBAN");
        confirmMoneyTransactionWithBankAccountDTO.setAmount(100.0);
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(10.0);
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(110.0);

        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        when(moneyTransactionWithBankAccountRepository.save(any())).thenReturn(new MoneyTransactionWithBankAccount());
        doNothing().when(userAccountService).addDepositMoneyTransactionWithBank(any(),any());
        //When
        moneyTransactionWithBankAccountService.addMoneyTransactionWithBank(1L,confirmMoneyTransactionWithBankAccountDTO);

        //Then
        verify(userAccountService,times(1)).addDepositMoneyTransactionWithBank(any(),any());
    }

    @Test
    void addMoneyTransactionWithBank_Should_Throw_Exception_When_Deposit(){
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccountList(new ArrayList<>());
        ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO = new ConfirmMoneyTransactionWithBankAccountDTO();
        confirmMoneyTransactionWithBankAccountDTO.setWithdraw(false);
        confirmMoneyTransactionWithBankAccountDTO.setIban("TESTIBAN");
        confirmMoneyTransactionWithBankAccountDTO.setAmount(100.0);
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(10.0);
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(110.0);

        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);

        //When
        //Then
        assertThrows(BankNotFoundInBankAccountListException.class, ()-> moneyTransactionWithBankAccountService.addDepositMoneyTransaction(1L, confirmMoneyTransactionWithBankAccountDTO));
    }

    @Test
    void addMoneyTransactionWithBank_Should_Throw_NotEnoughMoneyException_When_Deposit(){
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(50.0);
        userAccount.setBankAccountList(bankAccountList);
        ConfirmMoneyTransactionWithBankAccountDTO confirmMoneyTransactionWithBankAccountDTO = new ConfirmMoneyTransactionWithBankAccountDTO();
        confirmMoneyTransactionWithBankAccountDTO.setWithdraw(false);
        confirmMoneyTransactionWithBankAccountDTO.setIban("TESTIBAN");
        confirmMoneyTransactionWithBankAccountDTO.setAmount(100.0);
        confirmMoneyTransactionWithBankAccountDTO.setTaxAmount(10.0);
        confirmMoneyTransactionWithBankAccountDTO.setTotalAmount(110.0);

        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);

        //When
        //Then
        assertThrows(NotEnoughMoneyException.class, ()-> moneyTransactionWithBankAccountService.addDepositMoneyTransaction(1L, confirmMoneyTransactionWithBankAccountDTO));
    }

    @Test
    void getWithdrawStatus_Should_Return_String(){
        //When
        String result = moneyTransactionWithBankAccountService.getWithdrawStatus(true);

        //Then
        assertThat(result).isEqualTo("checked");
    }

    @Test
    void getWithdrawStatus_Should_Return_Null(){
        //When
        String result = moneyTransactionWithBankAccountService.getWithdrawStatus(false);

        //Then
        assertThat(result).isEqualTo(null);
    }
}