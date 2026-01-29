package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.Account;
import au.com.anz.wholeSaleEngineering.repository.AccountRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AccountService using JUnit 5 and Mockito
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

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
    void testGetAllAccounts_Success() {
        // Given
        Account account2 = new Account();
        account2.setId(2L);
        account2.setAccountNo("791066619");
        account2.setAccountName("AUSavings933");
        account2.setAccountType("Savings");
        account2.setBalanceDate(LocalDate.of(2018, 11, 8));
        account2.setCurrency("AUD");
        account2.setOpeningAvailBal(new BigDecimal("88005.93"));

        List<Account> expectedAccounts = Arrays.asList(testAccount, account2);
        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        // When
        List<Account> actualAccounts = accountService.getAllAccounts();

        // Then
        assertThat(actualAccounts).isNotNull();
        assertThat(actualAccounts).hasSize(2);
        assertThat(actualAccounts).containsExactlyElementsOf(expectedAccounts);
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testGetAccountById_Found() {
        // Given
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // When
        Optional<Account> result = accountService.getAccountById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getAccountNo()).isEqualTo("585309209");
        assertThat(result.get().getAccountName()).isEqualTo("SGSavings726");
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAccountById_NotFound() {
        // Given
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Account> result = accountService.getAccountById(999L);

        // Then
        assertThat(result).isEmpty();
        verify(accountRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAccountByAccountNo_Found() {
        // Given
        when(accountRepository.findByAccountNo("585309209")).thenReturn(Optional.of(testAccount));

        // When
        Optional<Account> result = accountService.getAccountByAccountNo("585309209");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getAccountName()).isEqualTo("SGSavings726");
        verify(accountRepository, times(1)).findByAccountNo("585309209");
    }

    @Test
    void testCreateAccount_Success() {
        // Given
        when(accountRepository.existsByAccountNo(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // When
        Account result = accountService.createAccount(testAccount);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAccountNo()).isEqualTo("585309209");
        verify(accountRepository, times(1)).existsByAccountNo("585309209");
        verify(accountRepository, times(1)).save(testAccount);
    }

    @Test
    void testCreateAccount_AlreadyExists() {
        // Given
        when(accountRepository.existsByAccountNo("585309209")).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> accountService.createAccount(testAccount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account with this number already exists");
        
        verify(accountRepository, times(1)).existsByAccountNo("585309209");
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testUpdateAccount_Success() {
        // Given
        Account updatedDetails = new Account();
        updatedDetails.setAccountName("UpdatedName");
        updatedDetails.setAccountType("Current");
        updatedDetails.setBalanceDate(LocalDate.of(2024, 1, 1));
        updatedDetails.setCurrency("USD");
        updatedDetails.setOpeningAvailBal(new BigDecimal("100000.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        // When
        Account result = accountService.updateAccount(1L, updatedDetails);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAccountName()).isEqualTo("UpdatedName");
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testUpdateAccount_NotFound() {
        // Given
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> accountService.updateAccount(999L, testAccount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account not found");
        
        verify(accountRepository, times(1)).findById(999L);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testDeleteAccount_Success() {
        // Given
        when(accountRepository.existsById(1L)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(1L);

        // When
        accountService.deleteAccount(1L);

        // Then
        verify(accountRepository, times(1)).existsById(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAccount_NotFound() {
        // Given
        when(accountRepository.existsById(999L)).thenReturn(false);

        // When / Then
        assertThatThrownBy(() -> accountService.deleteAccount(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account not found");
        
        verify(accountRepository, times(1)).existsById(999L);
        verify(accountRepository, never()).deleteById(any(Long.class));
    }
}
