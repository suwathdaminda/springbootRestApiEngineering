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
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
@Service
@Transactional
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Get all accounts
     * 
     * @return list of all accounts
     */
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        logger.debug("Found {} accounts", accounts.size());
        return accounts;
    }
    
    /**
     * Get account by ID
     * 
     * @param id the account ID
     * @return Optional containing the account if found
     */
    public Optional<Account> getAccountById(Long id) {
        logger.info("Fetching account with ID: {}", id);
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            logger.debug("Found account: {}", account.get().getAccountNo());
        } else {
            logger.warn("Account with ID {} not found", id);
        }
        return account;
    }
    
    /**
     * Get account by account number
     * 
     * @param accountNo the account number
     * @return Optional containing the account if found
     */
    public Optional<Account> getAccountByAccountNo(String accountNo) {
        logger.info("Fetching account with account number: {}", accountNo);
        Optional<Account> account = accountRepository.findByAccountNo(accountNo);
        if (account.isPresent()) {
            logger.debug("Found account: {}", account.get().getAccountName());
        } else {
            logger.warn("Account with number {} not found", accountNo);
        }
        return account;
    }
    
    /**
     * Create new account
     * 
     * @param account the account to create
     * @return the created account
     */
    public Account createAccount(Account account) {
        logger.info("Creating new account: {}", account.getAccountNo());
        if (accountRepository.existsByAccountNo(account.getAccountNo())) {
            logger.error("Account with number {} already exists", account.getAccountNo());
            throw new IllegalArgumentException("Account with this number already exists");
        }
        Account savedAccount = accountRepository.save(account);
        logger.info("Successfully created account with ID: {}", savedAccount.getId());
        return savedAccount;
    }
    
    /**
     * Update existing account
     * 
     * @param id the account ID
     * @param accountDetails the updated account details
     * @return the updated account
     */
    public Account updateAccount(Long id, Account accountDetails) {
        logger.info("Updating account with ID: {}", id);
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Account with ID {} not found for update", id);
                return new IllegalArgumentException("Account not found with id: " + id);
            });
        
        account.setAccountName(accountDetails.getAccountName());
        account.setAccountType(accountDetails.getAccountType());
        account.setBalanceDate(accountDetails.getBalanceDate());
        account.setCurrency(accountDetails.getCurrency());
        account.setOpeningAvailBal(accountDetails.getOpeningAvailBal());
        
        Account updatedAccount = accountRepository.save(account);
        logger.info("Successfully updated account: {}", updatedAccount.getAccountNo());
        return updatedAccount;
    }
    
    /**
     * Delete account by ID
     * 
     * @param id the account ID
     */
    public void deleteAccount(Long id) {
        logger.info("Deleting account with ID: {}", id);
        if (!accountRepository.existsById(id)) {
            logger.error("Account with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
        logger.info("Successfully deleted account with ID: {}", id);
    }
}
