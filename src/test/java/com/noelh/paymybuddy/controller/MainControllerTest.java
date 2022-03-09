package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.customexception.*;
import com.noelh.paymybuddy.dto.*;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.MoneyTransactionService;
import com.noelh.paymybuddy.service.MoneyTransactionWithBankAccountService;
import com.noelh.paymybuddy.service.MoneyTransactionWithUserAccountService;
import com.noelh.paymybuddy.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MainController.class)
@AutoConfigureMockMvc(addFilters = false)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private MoneyTransactionService moneyTransactionService;

    @MockBean
    private MoneyTransactionWithUserAccountService moneyTransactionWithUserAccountService;

    @MockBean
    private MoneyTransactionWithBankAccountService moneyTransactionWithBankAccountService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void getIndexShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();
        when(moneyTransactionService.getMoneyTransactionListByUserAccountId(anyLong())).thenReturn(moneyTransactionDTOList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount))
                .andExpect(MockMvcResultMatchers.model().attribute("transaction", moneyTransactionDTOList));
    }

    @Test
    void getLoginShouldReturnOk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void getSignUpPageShouldReturnOk() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();

        mockMvc.perform(get("/SignUp"))
                .andExpect(status().isOk())
                .andExpect(view().name("SignUp"))
                .andExpect(MockMvcResultMatchers.model().attribute("signUpDTO", signUpDTO));
    }

    @Test
    void submitSignUpPageShouldReturnOk() throws Exception {
        doNothing().when(userAccountService).addUserAccountByMailAndPassword(any());

        mockMvc.perform(post("/SignUp"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void submitSignUpPageShouldReturnException() throws Exception {
        doThrow(new LoginMailAlreadyExistException()).when(userAccountService).addUserAccountByMailAndPassword(any());

        mockMvc.perform(post("/SignUp"))
                .andExpect(status().isOk())
                .andExpect(view().name("SignUp"))
                .andExpect(MockMvcResultMatchers.model().attribute("signUpDTO", new SignUpDTO()));
    }

    @Test
    void getHomeShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();
        when(moneyTransactionService.getMoneyTransactionListByUserAccountId(anyLong())).thenReturn(moneyTransactionDTOList);

        mockMvc.perform(get("/Home"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount))
                .andExpect(MockMvcResultMatchers.model().attribute("transaction", moneyTransactionDTOList));
    }

    @Test
    void getUserTransferShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        mockMvc.perform(get("/UserTransfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTransfer"))
                .andExpect(MockMvcResultMatchers.model().attribute("addMoneyTransactionWithUserAccountDTO", new AddMoneyTransactionWithUserAccountDTO()))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitUserTransferShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        mockMvc.perform(post("/UserTransfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTransferConfirmation"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitUserTransferConfirmationShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doNothing().when(moneyTransactionWithUserAccountService).addMoneyTransactionWithUserAccount(anyLong(),any());

        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();
        when(moneyTransactionService.getMoneyTransactionListByUserAccountId(anyLong())).thenReturn(moneyTransactionDTOList);

        mockMvc.perform(post("/UserTransferConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount))
                .andExpect(MockMvcResultMatchers.model().attribute("transaction", moneyTransactionDTOList));
    }

    @Test
    void submitUserTransferConfirmationShouldThrowNotEnoughMoneyException() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doThrow(new NotEnoughMoneyException()).when(moneyTransactionWithUserAccountService).addMoneyTransactionWithUserAccount(anyLong(),any());

        mockMvc.perform(post("/UserTransferConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTransfer"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount))
                .andExpect(MockMvcResultMatchers.model().attribute("addMoneyTransactionWithUserAccountDTO", new AddMoneyTransactionWithUserAccountDTO()));
    }

    @Test
    void getBankTransferShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        mockMvc.perform(get("/BankTransfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("BankTransfer"))
                .andExpect(MockMvcResultMatchers.model().attribute("addMoneyTransactionWithBankAccountDTO", new AddMoneyTransactionWithBankAccountDTO()))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitBankTransferShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        mockMvc.perform(post("/BankTransfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("BankTransferConfirmation"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitBankTransferConfirmationShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doNothing().when(moneyTransactionWithBankAccountService).addMoneyTransactionWithBank(anyLong(),any());

        List<MoneyTransactionDTO> moneyTransactionDTOList = new ArrayList<>();
        when(moneyTransactionService.getMoneyTransactionListByUserAccountId(anyLong())).thenReturn(moneyTransactionDTOList);

        mockMvc.perform(post("/BankTransferConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("Home"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount))
                .andExpect(MockMvcResultMatchers.model().attribute("transaction", moneyTransactionDTOList));
    }

    @Test
    void submitBankTransferConfirmationShouldThrowNotEnoughMoneyException() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doThrow(new NotEnoughMoneyException()).when(moneyTransactionWithBankAccountService).addMoneyTransactionWithBank(anyLong(),any());

        mockMvc.perform(post("/BankTransferConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("BankTransfer"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount))
                .andExpect(MockMvcResultMatchers.model().attribute("addMoneyTransactionWithBankAccountDTO", new AddMoneyTransactionWithBankAccountDTO()));
    }

    @Test
    void getFriendListPageShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        mockMvc.perform(get("/FriendList"))
                .andExpect(status().isOk())
                .andExpect(view().name("FriendList"))
                .andExpect(MockMvcResultMatchers.model().attribute("addFriendDTO", new AddFriendDTO()))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitFriendListPageShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doNothing().when(userAccountService).addFriendByUserAccountId(anyLong(),anyString());

        mockMvc.perform(post("/FriendList"))
                .andExpect(status().isOk())
                .andExpect(view().name("FriendList"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitFriendListPageShouldThrowUserAccountNotFoundException() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doThrow(new UserAccountNotFoundException()).when(userAccountService).addFriendByUserAccountId(anyLong(),anyString());

        mockMvc.perform(post("/FriendList"))
                .andExpect(status().isOk())
                .andExpect(view().name("FriendList"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitFriendListPageShouldThrowLoginMailAlreadyAddedException() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doThrow(new LoginMailAlreadyAddedException()).when(userAccountService).addFriendByUserAccountId(anyLong(),anyString());

        mockMvc.perform(post("/FriendList"))
                .andExpect(status().isOk())
                .andExpect(view().name("FriendList"))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void getBankListPagePageShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        mockMvc.perform(get("/BankList"))
                .andExpect(status().isOk())
                .andExpect(view().name("BankList"))
                .andExpect(MockMvcResultMatchers.model().attribute("addBankDTO", new AddBankDTO()))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitBankListPageShouldReturnOk() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doNothing().when(userAccountService).addBankAccountByUserAccountId(anyLong(),anyString());

        mockMvc.perform(post("/BankList"))
                .andExpect(status().isOk())
                .andExpect(view().name("BankList"))
                .andExpect(MockMvcResultMatchers.model().attribute("addBankDTO", new AddBankDTO()))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }

    @Test
    void submitBankListPageShouldThrowBankAccountAlreadyAddedException() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        when(userAccountService.findUserAccountByAuthentication()).thenReturn(userAccount);

        doThrow(new BankAccountAlreadyAddedException()).when(userAccountService).addBankAccountByUserAccountId(anyLong(),anyString());

        mockMvc.perform(post("/BankList"))
                .andExpect(status().isOk())
                .andExpect(view().name("BankList"))
                .andExpect(MockMvcResultMatchers.model().attribute("addBankDTO", new AddBankDTO()))
                .andExpect(MockMvcResultMatchers.model().attribute("userAccount", userAccount));
    }
}