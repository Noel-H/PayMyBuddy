package com.noelh.paymybuddy.service;

import com.noelh.paymybuddy.model.BankAccount;
import com.noelh.paymybuddy.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    void isBankAccountExist() {
        String iban = "TEST1234";
        when(bankAccountRepository.existsBankAccountByIban(iban)).thenReturn(true);

        bankAccountService.isBankAccountExist(iban);

        verify(bankAccountRepository,times(1)).existsBankAccountByIban(iban);
    }

    @Test
    void getBankAccountByIban() {
        String iban = "TEST1234";
        when(bankAccountRepository.getBankAccountByIban(iban)).thenReturn(new BankAccount());

        bankAccountService.getBankAccountByIban(iban);

        verify(bankAccountRepository,times(1)).getBankAccountByIban(iban);
    }

    @Test
    void addBankAccount() {
        String iban = "TEST1234";
        BankAccount  bankAccount = new BankAccount();
        bankAccount.setIban(iban);
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);

        bankAccountService.addBankAccount(iban);

        verify(bankAccountRepository,times(1)).save(any());
    }
}