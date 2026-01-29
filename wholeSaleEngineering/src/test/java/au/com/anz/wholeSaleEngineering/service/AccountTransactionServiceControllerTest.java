package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
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
 * Integration tests for AccountTransactionServiceController
 * Using MockMvc and Mockito
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
@WebMvcTest(AccountTransactionServiceController.class)
class AccountTransactionServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTransactionService accountTransactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountTransaction testTransaction;

    @BeforeEach
    void setUp() {
        testTransaction = new AccountTransaction();
        testTransaction.setId(1L);
        testTransaction.setAccountNo("585309209");
        testTransaction.setAccountName("SGSavings726");
        testTransaction.setValueDate(LocalDate.of(2018, 11, 8));
        testTransaction.setCurrency("SGD");
        testTransaction.setCreditAmt(new BigDecimal("9540.48"));
        testTransaction.setDebitAmt(null);
        testTransaction.setTxType("Credit");
        testTransaction.setTxNarrative("Salary deposit");
    }

    @Test
    void testGetAllTransactions_Success() throws Exception {
        // Given
        when(accountTransactionService.getAllTransactions()).thenReturn(Arrays.asList(testTransaction));

        // When & Then
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountNo").value("585309209"))
                .andExpect(jsonPath("$[0].txType").value("Credit"));
    }

    @Test
    void testGetAccountTransaction_Found() throws Exception {
        // Given
        when(accountTransactionService.getTransactionsByAccountNo("585309209"))
                .thenReturn(Arrays.asList(testTransaction));

        // When & Then
        mockMvc.perform(get("/api/accountTransaction/585309209"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountNo").value("585309209"));
    }

    @Test
    void testGetAccountTransaction_NotFound() throws Exception {
        // Given
        when(accountTransactionService.getTransactionsByAccountNo("999999999"))
                .thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/accountTransaction/999999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTransactionById_Found() throws Exception {
        // Given
        when(accountTransactionService.getTransactionById(1L)).thenReturn(Optional.of(testTransaction));

        // When & Then
        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountNo").value("585309209"));
    }

    @Test
    void testGetTransactionById_NotFound() throws Exception {
        // Given
        when(accountTransactionService.getTransactionById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/transactions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateTransaction_Success() throws Exception {
        // Given
        when(accountTransactionService.createTransaction(any(AccountTransaction.class)))
                .thenReturn(testTransaction);

        // When & Then
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNo").value("585309209"));
    }

    @Test
    void testUpdateTransaction_Success() throws Exception {
        // Given
        when(accountTransactionService.updateTransaction(eq(1L), any(AccountTransaction.class)))
                .thenReturn(testTransaction);

        // When & Then
        mockMvc.perform(put("/api/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNo").value("585309209"));
    }

    @Test
    void testDeleteTransaction_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isNoContent());
    }
}
