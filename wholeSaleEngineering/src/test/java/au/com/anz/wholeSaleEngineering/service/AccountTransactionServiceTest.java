package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import au.com.anz.wholeSaleEngineering.repository.AccountTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

/**
 * Unit tests for AccountTransactionService using JUnit 5 and Mockito
 * @author Suwath Mihindukulasooriya
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AccountTransactionService Unit Tests")
@SuppressWarnings("null")
public class AccountTransactionServiceTest {

    @Mock
    private AccountTransactionRepository transactionRepository;

    @InjectMocks
    private AccountTransactionService transactionService;

    private AccountTransaction testTransaction;

    @BeforeEach
    public void setUp() {
        testTransaction = new AccountTransaction("585309209", "SGSavings726",
                LocalDate.of(2018, 11, 8), "SGD",
                null, new BigDecimal("9540.48"), "Credit", "Payment");
        testTransaction.setId(1L);
    }

    @Test
    @DisplayName("Should retrieve all transactions successfully")
    public void testGetAllTransactions() {
        // Arrange
        List<AccountTransaction> transactions = Arrays.asList(
                testTransaction,
                new AccountTransaction("791066619", "AUSavings933",
                        LocalDate.of(2018, 11, 8), "AUD",
                        new BigDecimal("5000.00"), null, "Debit", "Withdrawal")
        );
        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        List<AccountTransaction> result = transactionService.getAllTransactions();

        // Assert
        assertThat(result).isNotNull().hasSize(2);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve transaction by ID successfully")
    public void testGetTransactionById() {
        // Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));

        // Act
        Optional<AccountTransaction> result = transactionService.getTransactionById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getAccountNo()).isEqualTo("585309209");
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty Optional when transaction ID not found")
    public void testGetTransactionByIdNotFound() {
        // Arrange
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<AccountTransaction> result = transactionService.getTransactionById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(transactionRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should retrieve transactions by account number successfully")
    public void testGetTransactionsByAccountNo() {
        // Arrange
        List<AccountTransaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByAccountNo("585309209")).thenReturn(transactions);

        // Act
        List<AccountTransaction> result = transactionService.getTransactionsByAccountNo("585309209");

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getAccountNo()).isEqualTo("585309209");
        verify(transactionRepository, times(1)).findByAccountNo("585309209");
    }

    @Test
    @DisplayName("Should retrieve transactions by date range successfully")
    public void testGetTransactionsByDateRange() {
        // Arrange
        LocalDate startDate = LocalDate.of(2018, 11, 1);
        LocalDate endDate = LocalDate.of(2018, 11, 30);
        List<AccountTransaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByAccountNoAndValueDateBetween("585309209", startDate, endDate))
                .thenReturn(transactions);

        // Act
        List<AccountTransaction> result = transactionService.getTransactionsByDateRange("585309209", startDate, endDate);

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        verify(transactionRepository, times(1))
                .findByAccountNoAndValueDateBetween("585309209", startDate, endDate);
    }

    @Test
    @DisplayName("Should retrieve credit transactions successfully")
    public void testGetCreditTransactions() {
        // Arrange
        List<AccountTransaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByAccountNoAndTxType("585309209", "Credit"))
                .thenReturn(transactions);

        // Act
        List<AccountTransaction> result = transactionService.getCreditTransactions("585309209");

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getTxType()).isEqualTo("Credit");
        verify(transactionRepository, times(1))
                .findByAccountNoAndTxType("585309209", "Credit");
    }

    @Test
    @DisplayName("Should retrieve debit transactions successfully")
    public void testGetDebitTransactions() {
        // Arrange
        AccountTransaction debitTx = new AccountTransaction("585309209", "SGSavings726",
                LocalDate.of(2018, 11, 8), "SGD",
                new BigDecimal("1000.00"), null, "Debit", "Withdrawal");
        List<AccountTransaction> transactions = Arrays.asList(debitTx);
        when(transactionRepository.findByAccountNoAndTxType("585309209", "Debit"))
                .thenReturn(transactions);

        // Act
        List<AccountTransaction> result = transactionService.getDebitTransactions("585309209");

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getTxType()).isEqualTo("Debit");
        verify(transactionRepository, times(1))
                .findByAccountNoAndTxType("585309209", "Debit");
    }

    @Test
    @DisplayName("Should retrieve transactions by currency successfully")
    public void testGetTransactionsByCurrency() {
        // Arrange
        List<AccountTransaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByCurrency("SGD")).thenReturn(transactions);

        // Act
        List<AccountTransaction> result = transactionService.getTransactionsByCurrency("SGD");

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getCurrency()).isEqualTo("SGD");
        verify(transactionRepository, times(1)).findByCurrency("SGD");
    }

    @Test
    @DisplayName("Should create a new transaction successfully")
    public void testCreateTransaction() {
        // Arrange
        when(transactionRepository.save(any(AccountTransaction.class))).thenReturn(testTransaction);

        // Act
        AccountTransaction result = transactionService.createTransaction(testTransaction);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccountNo()).isEqualTo("585309209");
        assertThat(result.getId()).isEqualTo(1L);
        verify(transactionRepository, times(1)).save(any(AccountTransaction.class));
    }

    @Test
    @DisplayName("Should update a transaction successfully")
    public void testUpdateTransaction() {
        // Arrange
        when(transactionRepository.existsById(1L)).thenReturn(true);
        when(transactionRepository.save(any(AccountTransaction.class))).thenReturn(testTransaction);

        // Act
        AccountTransaction result = transactionService.updateTransaction(testTransaction);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccountNo()).isEqualTo("585309209");
        verify(transactionRepository, times(1)).existsById(1L);
        verify(transactionRepository, times(1)).save(any(AccountTransaction.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent transaction")
    public void testUpdateTransactionNotFound() {
        // Arrange
        when(transactionRepository.existsById(999L)).thenReturn(false);
        testTransaction.setId(999L);

        // Act & Assert
        assertThatThrownBy(() -> transactionService.updateTransaction(testTransaction))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Transaction not found");
        verify(transactionRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("Should delete a transaction successfully")
    public void testDeleteTransaction() {
        // Arrange
        when(transactionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(1L);

        // Act
        transactionService.deleteTransaction(1L);

        // Assert
        verify(transactionRepository, times(1)).existsById(1L);
        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent transaction")
    public void testDeleteTransactionNotFound() {
        // Arrange
        when(transactionRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> transactionService.deleteTransaction(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Transaction not found");
        verify(transactionRepository, times(1)).existsById(999L);
        verify(transactionRepository, never()).deleteById(any());
    }
}
