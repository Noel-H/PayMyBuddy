package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.customexception.NotEnoughMoneyException;
import com.noelh.paymybuddy.customexception.UserAccountNotFoundException;
import com.noelh.paymybuddy.dto.ConfirmMoneyTransactionWithUserAccountDTO;
import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.repository.MoneyTransactionWithUserAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoneyTransactionWithUserAccountServiceTest {

    @Mock
    private MoneyTransactionWithUserAccountRepository moneyTransactionWithUserAccountRepository;

    @Mock
    private UserAccountService userAccountService;

    @InjectMocks
    private MoneyTransactionWithUserAccountService moneyTransactionWithUserAccountService;

    @Test
    void addMoneyTransactionWithUserAccount_Should_Throw_NotEnoughMoneyException(){
        //Given
        ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO = new ConfirmMoneyTransactionWithUserAccountDTO();
        confirmMoneyTransactionWithUserAccountDTO.setTotalAmount(100.0);
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(50.0);

        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        //When
        //Then
        assertThrows(NotEnoughMoneyException.class,()-> moneyTransactionWithUserAccountService.addMoneyTransactionWithUserAccount(1L,confirmMoneyTransactionWithUserAccountDTO));
    }

    @Test
    void addMoneyTransactionWithUserAccount_Should_Throw_UserAccountNotFoundException(){
        //Given
        ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO = new ConfirmMoneyTransactionWithUserAccountDTO();
        confirmMoneyTransactionWithUserAccountDTO.setTotalAmount(100.0);
        confirmMoneyTransactionWithUserAccountDTO.setLoginMail("Test@test.test");
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(250.0);

        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        when(userAccountService.existsUserAccountByLoginMail("Test@test.test")).thenReturn(false);
        //When
        //Then
        assertThrows(UserAccountNotFoundException.class,()-> moneyTransactionWithUserAccountService.addMoneyTransactionWithUserAccount(1L,confirmMoneyTransactionWithUserAccountDTO));
    }

    @Test
    void addMoneyTransactionWithUserAccount() throws NotEnoughMoneyException, UserAccountNotFoundException {
        //Given
        ConfirmMoneyTransactionWithUserAccountDTO confirmMoneyTransactionWithUserAccountDTO = new ConfirmMoneyTransactionWithUserAccountDTO();
        confirmMoneyTransactionWithUserAccountDTO.setTotalAmount(100.0);
        confirmMoneyTransactionWithUserAccountDTO.setLoginMail("Test@test.test");
        confirmMoneyTransactionWithUserAccountDTO.setAmount(90.0);
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(250.0);
        UserAccount userAccount2 = new UserAccount();
        MoneyTransactionWithUserAccount moneyTransactionWithUserAccount = new MoneyTransactionWithUserAccount();

        when(userAccountService.getUserAccount(1L)).thenReturn(userAccount);
        when(userAccountService.existsUserAccountByLoginMail("Test@test.test")).thenReturn(true);
        when(userAccountService.getUserAccountByLoginMail("Test@test.test")).thenReturn(userAccount2);
        when(moneyTransactionWithUserAccountRepository.save(any())).thenReturn(moneyTransactionWithUserAccount);
        doNothing().when(userAccountService).addMoneyTransactionWithUser(userAccount,100.0,userAccount2,90.0,moneyTransactionWithUserAccount);
        //When
        moneyTransactionWithUserAccountService.addMoneyTransactionWithUserAccount(1L,confirmMoneyTransactionWithUserAccountDTO);
        //Then
        verify(moneyTransactionWithUserAccountRepository,times(1)).save(any());
        verify(userAccountService,times(1)).addMoneyTransactionWithUser(userAccount,100.0,userAccount2,90.0,moneyTransactionWithUserAccount);
    }
}