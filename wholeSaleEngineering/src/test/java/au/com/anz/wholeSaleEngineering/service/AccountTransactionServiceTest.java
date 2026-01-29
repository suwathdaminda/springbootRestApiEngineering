package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import au.com.anz.wholeSaleEngineering.repository.AccountTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AccountTransactionService using JUnit 5 and Mockito
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceTest {

    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @InjectMocks
    private AccountTransactionService accountTransactionService;

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
    void testGetAllTransactions_Success() {
        // Given
        AccountTransaction transaction2 = new AccountTransaction();
        transaction2.setId(2L);
        transaction2.setAccountNo("585309209");
        transaction2.setAccountName("SGSavings726");
        transaction2.setValueDate(LocalDate.of(2018, 10, 15));
        transaction2.setCurrency("SGD");
        transaction2.setDebitAmt(new BigDecimal("1200.00"));
        transaction2.setCreditAmt(null);
        transaction2.setTxType("Debit");
        transaction2.setTxNarrative("ATM withdrawal");

        List<AccountTransaction> expectedTransactions = Arrays.asList(testTransaction, transaction2);
        when(accountTransactionRepository.findAll()).thenReturn(expectedTransactions);

        // When
        List<AccountTransaction> actualTransactions = accountTransactionService.getAllTransactions();

        // Then
        assertThat(actualTransactions).isNotNull();
        assertThat(actualTransactions).hasSize(2);
        assertThat(actualTransactions).containsExactlyElementsOf(expectedTransactions);
        verify(accountTransactionRepository, times(1)).findAll();
    }

    @Test
    void testGetTransactionById_Found() {
        // Given
        when(accountTransactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));

        // When
        Optional<AccountTransaction> result = accountTransactionService.getTransactionById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getAccountNo()).isEqualTo("585309209");
        assertThat(result.get().getTxType()).isEqualTo("Credit");
        verify(accountTransactionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() {
        // Given
        when(accountTransactionRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<AccountTransaction> result = accountTransactionService.getTransactionById(999L);

        // Then
        assertThat(result).isEmpty();
        verify(accountTransactionRepository, times(1)).findById(999L);
    }

    @Test
    void testGetTransactionsByAccountNo_Success() {
        // Given
        AccountTransaction transaction2 = new AccountTransaction();
        transaction2.setId(2L);
        transaction2.setAccountNo("585309209");
        transaction2.setAccountName("SGSavings726");
        transaction2.setValueDate(LocalDate.of(2018, 9, 20));
        transaction2.setCurrency("SGD");
        transaction2.setCreditAmt(new BigDecimal("5564.79"));
        transaction2.setTxType("Credit");

        List<AccountTransaction> expectedTransactions = Arrays.asList(testTransaction, transaction2);
        when(accountTransactionRepository.findByAccountNoOrderByValueDateDesc("585309209"))
                .thenReturn(expectedTransactions);

        // When
        List<AccountTransaction> result = accountTransactionService.getTransactionsByAccountNo("585309209");

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getAccountNo()).isEqualTo("585309209");
        verify(accountTransactionRepository, times(1))
                .findByAccountNoOrderByValueDateDesc("585309209");
    }

    @Test
    void testGetTransactionsByAccountNo_EmptyList() {
        // Given
        when(accountTransactionRepository.findByAccountNoOrderByValueDateDesc("999999999"))
                .thenReturn(Arrays.asList());

        // When
        List<AccountTransaction> result = accountTransactionService.getTransactionsByAccountNo("999999999");

        // Then
        assertThat(result).isEmpty();
        verify(accountTransactionRepository, times(1))
                .findByAccountNoOrderByValueDateDesc("999999999");
    }

    @Test
    void testCreateTransaction_Success() {
        // Given
        when(accountTransactionRepository.save(any(AccountTransaction.class))).thenReturn(testTransaction);

        // When
        AccountTransaction result = accountTransactionService.createTransaction(testTransaction);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAccountNo()).isEqualTo("585309209");
        assertThat(result.getTxType()).isEqualTo("Credit");
        verify(accountTransactionRepository, times(1)).save(testTransaction);
    }

    @Test
    void testUpdateTransaction_Success() {
        // Given
        AccountTransaction updatedDetails = new AccountTransaction();
        updatedDetails.setAccountName("UpdatedName");
        updatedDetails.setValueDate(LocalDate.of(2024, 1, 1));
        updatedDetails.setCurrency("USD");
        updatedDetails.setCreditAmt(new BigDecimal("20000.00"));
        updatedDetails.setDebitAmt(null);
        updatedDetails.setTxType("Credit");
        updatedDetails.setTxNarrative("Updated narrative");

        when(accountTransactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));
        when(accountTransactionRepository.save(any(AccountTransaction.class))).thenReturn(testTransaction);

        // When
        AccountTransaction result = accountTransactionService.updateTransaction(1L, updatedDetails);

        // Then
        assertThat(result).isNotNull();
        verify(accountTransactionRepository, times(1)).findById(1L);
        verify(accountTransactionRepository, times(1)).save(any(AccountTransaction.class));
    }

    @Test
    void testUpdateTransaction_NotFound() {
        // Given
        when(accountTransactionRepository.findById(999L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> accountTransactionService.updateTransaction(999L, testTransaction))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Transaction not found");
        
        verify(accountTransactionRepository, times(1)).findById(999L);
        verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
    }

    @Test
    void testDeleteTransaction_Success() {
        // Given
        when(accountTransactionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(accountTransactionRepository).deleteById(1L);

        // When
        accountTransactionService.deleteTransaction(1L);

        // Then
        verify(accountTransactionRepository, times(1)).existsById(1L);
        verify(accountTransactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTransaction_NotFound() {
        // Given
        when(accountTransactionRepository.existsById(999L)).thenReturn(false);

        // When / Then
        assertThatThrownBy(() -> accountTransactionService.deleteTransaction(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Transaction not found");
        
        verify(accountTransactionRepository, times(1)).existsById(999L);
        verify(accountTransactionRepository, never()).deleteById(any(Long.class));
    }
}
