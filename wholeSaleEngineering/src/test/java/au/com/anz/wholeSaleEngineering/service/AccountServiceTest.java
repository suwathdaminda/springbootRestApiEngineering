package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.Account;
import au.com.anz.wholeSaleEngineering.repository.AccountRepository;
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
 * Unit tests for AccountService using JUnit 5 and Mockito
 * @author Suwath Mihindukulasooriya
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AccountService Unit Tests")
@SuppressWarnings("null")
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    public void setUp() {
        testAccount = new Account("585309209", "SGSavings726", "Savings",
                LocalDate.of(2018, 11, 8), "SGD", new BigDecimal("84327.51"));
        testAccount.setId(1L);
    }

    @Test
    @DisplayName("Should retrieve all accounts successfully")
    public void testGetAllAccounts() {
        // Arrange
        List<Account> accounts = Arrays.asList(
                testAccount,
                new Account("791066619", "AUSavings933", "Savings",
                        LocalDate.of(2018, 11, 8), "AUD", new BigDecimal("88005.93"))
        );
        when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<Account> result = accountService.getAllAccounts();

        // Assert
        assertThat(result).isNotNull().hasSize(2);
        assertThat(result.get(0).getAccountNo()).isEqualTo("585309209");
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve account by ID successfully")
    public void testGetAccountById() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act
        Optional<Account> result = accountService.getAccountById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getAccountNo()).isEqualTo("585309209");
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty Optional when account ID not found")
    public void testGetAccountByIdNotFound() {
        // Arrange
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountService.getAccountById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(accountRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should retrieve account by account number successfully")
    public void testGetAccountByAccountNo() {
        // Arrange
        when(accountRepository.findByAccountNo("585309209")).thenReturn(Optional.of(testAccount));

        // Act
        Optional<Account> result = accountService.getAccountByAccountNo("585309209");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getAccountName()).isEqualTo("SGSavings726");
        verify(accountRepository, times(1)).findByAccountNo("585309209");
    }

    @Test
    @DisplayName("Should retrieve accounts by type successfully")
    public void testGetAccountsByType() {
        // Arrange
        List<Account> savingsAccounts = Arrays.asList(testAccount);
        when(accountRepository.findByAccountType("Savings")).thenReturn(savingsAccounts);

        // Act
        List<Account> result = accountService.getAccountsByType("Savings");

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getAccountType()).isEqualTo("Savings");
        verify(accountRepository, times(1)).findByAccountType("Savings");
    }

    @Test
    @DisplayName("Should retrieve accounts by currency successfully")
    public void testGetAccountsByCurrency() {
        // Arrange
        List<Account> sgdAccounts = Arrays.asList(testAccount);
        when(accountRepository.findByCurrency("SGD")).thenReturn(sgdAccounts);

        // Act
        List<Account> result = accountService.getAccountsByCurrency("SGD");

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getCurrency()).isEqualTo("SGD");
        verify(accountRepository, times(1)).findByCurrency("SGD");
    }

    @Test
    @DisplayName("Should create a new account successfully")
    public void testCreateAccount() {
        // Arrange
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // Act
        Account result = accountService.createAccount(testAccount);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccountNo()).isEqualTo("585309209");
        assertThat(result.getId()).isEqualTo(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should update an account successfully")
    public void testUpdateAccount() {
        // Arrange
        when(accountRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // Act
        Account result = accountService.updateAccount(testAccount);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccountNo()).isEqualTo("585309209");
        verify(accountRepository, times(1)).existsById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent account")
    public void testUpdateAccountNotFound() {
        // Arrange
        when(accountRepository.existsById(999L)).thenReturn(false);
        testAccount.setId(999L);

        // Act & Assert
        assertThatThrownBy(() -> accountService.updateAccount(testAccount))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Account not found");
        verify(accountRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("Should delete an account successfully")
    public void testDeleteAccount() {
        // Arrange
        when(accountRepository.existsById(1L)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(1L);

        // Act
        accountService.deleteAccount(1L);

        // Assert
        verify(accountRepository, times(1)).existsById(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent account")
    public void testDeleteAccountNotFound() {
        // Arrange
        when(accountRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> accountService.deleteAccount(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Account not found");
        verify(accountRepository, times(1)).existsById(999L);
        verify(accountRepository, never()).deleteById(any());
    }
}
