package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.customexception.*;
import com.noelh.paymybuddy.dto.SignUpDTO;
import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    void getUserAccount_Should_Return_UserAccount() {
        //Given
        when(userAccountRepository.getById(1L)).thenReturn(new UserAccount());

        //When
        userAccountService.getUserAccount(1L);

        //Then
        verify(userAccountRepository,times(1)).getById(1L);
    }

    @Test
    void getUserAccountByLoginMail_Should_Return_UserAccount() {
        //Given
        when(userAccountRepository.getUserAccountByLoginMail("Test@test.test")).thenReturn(new UserAccount());

        //When
        userAccountService.getUserAccountByLoginMail("Test@test.test");

        //Then
        verify(userAccountRepository,times(1)).getUserAccountByLoginMail("Test@test.test");
    }

    @Test
    void addUserAccountByMailAndPassword_Should_Throw_LoginMailAlreadyExistException() {
        //Given
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setLoginMail("Test@test.test");
        when(userAccountRepository.existsUserAccountByLoginMail("Test@test.test")).thenReturn(true);
        //When
        //Then
        assertThrows(LoginMailAlreadyExistException.class,() -> userAccountService.addUserAccountByMailAndPassword(signUpDTO));
    }

    @Test
    void addBankAccountByUserAccountId_Should_Throw_BankAccountAlreadyAddedException() {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccountList(bankAccountList);
        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountRepository.getById(1L)).thenReturn(userAccount);

        //When
        //Then
        assertThrows(BankAccountAlreadyAddedException.class,() -> userAccountService.addBankAccountByUserAccountId(1L,"TESTIBAN"));
    }

    @Test
    void addBankAccountByUserAccountId_Should_Throw_BankAccountAlreadyUsedException() {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccountList(new ArrayList<>());
        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountRepository.getById(1L)).thenReturn(userAccount);
        when(bankAccountService.isBankAccountExist("TESTIBAN")).thenReturn(true);

        //When
        //Then
        assertThrows(BankAccountAlreadyUsedException.class,() -> userAccountService.addBankAccountByUserAccountId(1L,"TESTIBAN"));
    }

    @Test
    void addBankAccountByUserAccountId() throws BankAccountAlreadyUsedException, BankAccountAlreadyAddedException {
        //Given
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("TESTIBAN");
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccountList(new ArrayList<>());
        when(bankAccountService.getBankAccountByIban("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountRepository.getById(1L)).thenReturn(userAccount);
        when(bankAccountService.isBankAccountExist("TESTIBAN")).thenReturn(false);
        when(bankAccountService.addBankAccount("TESTIBAN")).thenReturn(bankAccount);
        when(userAccountRepository.save(any())).thenReturn(new UserAccount());

        List<BankAccount> expectedResult = new ArrayList<>();
        expectedResult.add(bankAccount);
        //When
        userAccountService.addBankAccountByUserAccountId(1L,"TESTIBAN");

        //Then
        assertThat(userAccount.getBankAccountList()).isEqualTo(expectedResult);
        verify(userAccountRepository,times(1)).save(any());
    }

    @Test
    void addFriendByUserAccountId_Should_Throw_UserAccountNotFoundException() {
        //Given
        when(userAccountRepository.existsUserAccountByLoginMail("Test@test.test")).thenReturn(false);

        //When
        //Then
        assertThrows(UserAccountNotFoundException.class, () -> userAccountService.addFriendByUserAccountId(1L,"Test@test.test"));
    }

    @Test
    void addFriendByUserAccountId_Should_Throw_LoginMailAlreadyAddedException() {
        //Given
        UserAccount userAccount = new UserAccount();
        UserAccount userAccount2 = new UserAccount();
        userAccount2.setLoginMail("Test@test.test");
        List<UserAccount> userAccountList = new ArrayList<>();
        userAccountList.add(userAccount2);
        userAccount.setFriendList(userAccountList);
        when(userAccountRepository.existsUserAccountByLoginMail("Test@test.test")).thenReturn(true);
        when(userAccountRepository.getUserAccountByLoginMail("Test@test.test")).thenReturn(userAccount2);
        when(userAccountRepository.getById(1L)).thenReturn(userAccount);

        //When
        //Then
        assertThrows(LoginMailAlreadyAddedException.class, () -> userAccountService.addFriendByUserAccountId(1L,"Test@test.test"));
    }

    @Test
    void addFriendByUserAccountId() throws LoginMailAlreadyAddedException, UserAccountNotFoundException {
        //Given
        UserAccount userAccount = new UserAccount();
        UserAccount userAccount2 = new UserAccount();
        userAccount2.setLoginMail("Test@test.test");
        userAccount.setFriendList(new ArrayList<>());
        when(userAccountRepository.existsUserAccountByLoginMail("Test@test.test")).thenReturn(true);
        when(userAccountRepository.getUserAccountByLoginMail("Test@test.test")).thenReturn(userAccount2);
        when(userAccountRepository.getById(1L)).thenReturn(userAccount);

        List<UserAccount> expectedResult = new ArrayList<>();
        expectedResult.add(userAccount2);

        //When
        userAccountService.addFriendByUserAccountId(1L,"Test@test.test");

        //Then
        assertThat(userAccount.getFriendList()).isEqualTo(expectedResult);
        verify(userAccountRepository,times(1)).save(any());
    }

    @Test
    void existsUserAccountByLoginMail() {
        //Given
        when(userAccountRepository.existsUserAccountByLoginMail("Test@test.test")).thenReturn(true);

        //When
        userAccountService.existsUserAccountByLoginMail("Test@test.test");

        //Then
        verify(userAccountRepository,times(1)).existsUserAccountByLoginMail("Test@test.test");
    }

    @Test
    void addMoneyTransactionWithUser() {
        //Given
        UserAccount userAccount = new UserAccount();
        userAccount.setMoneyTransactionWithUserAccountList(new ArrayList<>());
        userAccount.setBalance(500.0);
        UserAccount userAccount2 = new UserAccount();
        userAccount2.setMoneyTransactionWithUserAccountList(new ArrayList<>());
        userAccount2.setBalance(500.0);
        MoneyTransactionWithUserAccount moneyTransactionWithUserAccount = new MoneyTransactionWithUserAccount();

        List<MoneyTransactionWithUserAccount> expectedResult = new ArrayList<>();
        expectedResult.add(moneyTransactionWithUserAccount);

        //When
        userAccountService.addMoneyTransactionWithUser(userAccount,100.0,userAccount2,100.0,moneyTransactionWithUserAccount);

        //Then
        assertThat(userAccount.getMoneyTransactionWithUserAccountList()).isEqualTo(expectedResult);
        assertThat(userAccount2.getMoneyTransactionWithUserAccountList()).isEqualTo(expectedResult);
        assertThat(userAccount.getBalance()).isEqualTo(400.0);
        assertThat(userAccount2.getBalance()).isEqualTo(600.0);
        verify(userAccountRepository,times(1)).save(userAccount);
        verify(userAccountRepository,times(1)).save(userAccount2);
    }

//    @Test
//    void addWithdrawMoneyTransactionWithBank() {
//    }
//
//    @Test
//    void addDepositMoneyTransactionWithBank() {
//    }
//
//    @Test
//    void findUserAccountByAuthentication() {
//    }

    @Test
    void roundedAmount() {
        //Given
        double expectedResult = 100.15;

        //When
        double result = userAccountService.roundedAmount(100.15365987452);

        //Then
        assertThat(result).isEqualTo(expectedResult);
    }
}