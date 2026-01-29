package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AccountServiceController
 * Using MockMvc and Mockito
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
@WebMvcTest(AccountServiceController.class)
class AccountServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setAccountNo("585309209");
        testAccount.setAccountName("SGSavings726");
        testAccount.setAccountType("Savings");
        testAccount.setBalanceDate(LocalDate.of(2018, 11, 8));
        testAccount.setCurrency("SGD");
        testAccount.setOpeningAvailBal(new BigDecimal("84327.51"));
    }

    @Test
    void testGetAllAccounts_Success() throws Exception {
        // Given
        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(testAccount));

        // When & Then
        mockMvc.perform(get("/api/accountDetail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountNo").value("585309209"))
                .andExpect(jsonPath("$[0].accountName").value("SGSavings726"))
                .andExpect(jsonPath("$[0].accountType").value("Savings"));
    }

    @Test
    void testGetAccountById_Found() throws Exception {
        // Given
        when(accountService.getAccountById(1L)).thenReturn(Optional.of(testAccount));

        // When & Then
        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountNo").value("585309209"))
                .andExpect(jsonPath("$.accountName").value("SGSavings726"));
    }

    @Test
    void testGetAccountById_NotFound() throws Exception {
        // Given
        when(accountService.getAccountById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/accounts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAccountByAccountNo_Found() throws Exception {
        // Given
        when(accountService.getAccountByAccountNo("585309209")).thenReturn(Optional.of(testAccount));

        // When & Then
        mockMvc.perform(get("/api/accounts/byNumber/585309209"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountNo").value("585309209"));
    }

    @Test
    void testCreateAccount_Success() throws Exception {
        // Given
        when(accountService.createAccount(any(Account.class))).thenReturn(testAccount);

        // When & Then
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNo").value("585309209"));
    }

    @Test
    void testUpdateAccount_Success() throws Exception {
        // Given
        when(accountService.updateAccount(eq(1L), any(Account.class))).thenReturn(testAccount);

        // When & Then
        mockMvc.perform(put("/api/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAccount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNo").value("585309209"));
    }

    @Test
    void testDeleteAccount_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isNoContent());
    }
}
