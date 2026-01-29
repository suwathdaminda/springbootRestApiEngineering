package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.Account;
import au.com.anz.wholeSaleEngineering.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Account operations
 * Provides business logic for account management
 * @author Suwath Mihindukulasooriya
 */
@Service
@Transactional
@SuppressWarnings("null")
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Retrieve all accounts
     * @return List of all accounts
     */
    public List<Account> getAllAccounts() {
        logger.debug("Fetching all accounts from database");
        List<Account> accounts = accountRepository.findAll();
        logger.info("Retrieved {} accounts", accounts.size());
        return accounts;
    }

    /**
     * Retrieve account by ID
     * @param id the account ID
     * @return Optional containing the account if found
     */
    public Optional<Account> getAccountById(Long id) {
        logger.debug("Fetching account by ID: {}", id);
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            logger.info("Account found with ID: {}", id);
        } else {
            logger.warn("Account not found with ID: {}", id);
        }
        return account;
    }

    /**
     * Retrieve account by account number
     * @param accountNo the account number
     * @return Optional containing the account if found
     */
    public Optional<Account> getAccountByAccountNo(String accountNo) {
        logger.debug("Fetching account by account number: {}", accountNo);
        Optional<Account> account = accountRepository.findByAccountNo(accountNo);
        if (account.isPresent()) {
            logger.info("Account found with number: {}", accountNo);
        } else {
            logger.warn("Account not found with number: {}", accountNo);
        }
        return account;
    }

    /**
     * Retrieve accounts by type
     * @param accountType the account type
     * @return List of accounts of the specified type
     */
    public List<Account> getAccountsByType(String accountType) {
        logger.debug("Fetching accounts by type: {}", accountType);
        List<Account> accounts = accountRepository.findByAccountType(accountType);
        logger.info("Retrieved {} accounts of type: {}", accounts.size(), accountType);
        return accounts;
    }

    /**
     * Retrieve accounts by currency
     * @param currency the currency code
     * @return List of accounts in the specified currency
     */
    public List<Account> getAccountsByCurrency(String currency) {
        logger.debug("Fetching accounts by currency: {}", currency);
        List<Account> accounts = accountRepository.findByCurrency(currency);
        logger.info("Retrieved {} accounts in currency: {}", accounts.size(), currency);
        return accounts;
    }

    /**
     * Create a new account
     * @param account the account to create
     * @return the created account
     */
    public Account createAccount(Account account) {
        logger.debug("Creating new account: {}", account.getAccountNo());
        Account savedAccount = accountRepository.save(account);
        logger.info("Account created successfully with ID: {} and number: {}", savedAccount.getId(), savedAccount.getAccountNo());
        return savedAccount;
    }

    /**
     * Update an existing account
     * @param account the account to update
     * @return the updated account
     */
    public Account updateAccount(Account account) {
        logger.debug("Updating account: {}", account.getId());
        if (accountRepository.existsById(account.getId())) {
            Account updatedAccount = accountRepository.save(account);
            logger.info("Account updated successfully with ID: {}", account.getId());
            return updatedAccount;
        } else {
            logger.error("Account not found for update with ID: {}", account.getId());
            throw new RuntimeException("Account not found with ID: " + account.getId());
        }
    }

    /**
     * Delete an account by ID
     * @param id the account ID
     */
    public void deleteAccount(Long id) {
        logger.debug("Deleting account with ID: {}", id);
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            logger.info("Account deleted successfully with ID: {}", id);
        } else {
            logger.error("Account not found for deletion with ID: {}", id);
            throw new RuntimeException("Account not found with ID: " + id);
        }
    }
}
