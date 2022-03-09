package com.noelh.paymybuddy.controller;

import com.noelh.paymybuddy.dto.MoneyTransactionDTO;
import com.noelh.paymybuddy.model.UserAccount;
import com.noelh.paymybuddy.service.MoneyTransactionService;
import com.noelh.paymybuddy.service.MoneyTransactionWithBankAccountService;
import com.noelh.paymybuddy.service.MoneyTransactionWithUserAccountService;
import com.noelh.paymybuddy.service.UserAccountService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(MockMvcResultMatchers.model().attribute("transaction", moneyTransactionDTOList));
    }
//
//    @Test
//    void getLogin() {
//    }
//
//    @Test
//    void getSignUpPage() {
//    }
//
//    @Test
//    void submitSignUpPage() {
//    }
//
//    @Test
//    void getHome() {
//    }
//
//    @Test
//    void getUserTransfer() {
//    }
//
//    @Test
//    void submitUserTransfer() {
//    }
//
//    @Test
//    void submitUserTransferConfirmation() {
//    }
//
//    @Test
//    void getBankTransfer() {
//    }
//
//    @Test
//    void submitBankTransfer() {
//    }
//
//    @Test
//    void submitBankTransferConfirmation() {
//    }
//
//    @Test
//    void getFriendListPage() {
//    }
//
//    @Test
//    void submitFriendListPage() {
//    }
//
//    @Test
//    void getBankListPagePage() {
//    }
//
//    @Test
//    void submitBankListPage() {
//    }
}