package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import au.com.anz.wholeSaleEngineering.repository.AccountTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Account Transaction operations
 * Provides business logic for transaction management
 * @author Suwath Mihindukulasooriya
 */
@Service
@Transactional
@SuppressWarnings("null")
public class AccountTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionService.class);

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    /**
     * Retrieve all transactions
     * @return List of all transactions
     */
    public List<AccountTransaction> getAllTransactions() {
        logger.debug("Fetching all transactions from database");
        List<AccountTransaction> transactions = accountTransactionRepository.findAll();
        logger.info("Retrieved {} transactions", transactions.size());
        return transactions;
    }

    /**
     * Retrieve transaction by ID
     * @param id the transaction ID
     * @return Optional containing the transaction if found
     */
    public Optional<AccountTransaction> getTransactionById(Long id) {
        logger.debug("Fetching transaction by ID: {}", id);
        Optional<AccountTransaction> transaction = accountTransactionRepository.findById(id);
        if (transaction.isPresent()) {
            logger.info("Transaction found with ID: {}", id);
        } else {
            logger.warn("Transaction not found with ID: {}", id);
        }
        return transaction;
    }

    /**
     * Retrieve all transactions for a specific account
     * @param accountNo the account number
     * @return List of transactions for the account
     */
    public List<AccountTransaction> getTransactionsByAccountNo(String accountNo) {
        logger.debug("Fetching transactions for account: {}", accountNo);
        List<AccountTransaction> transactions = accountTransactionRepository.findByAccountNo(accountNo);
        logger.info("Retrieved {} transactions for account: {}", transactions.size(), accountNo);
        return transactions;
    }

    /**
     * Retrieve transactions for an account within a date range
     * @param accountNo the account number
     * @param startDate the start date
     * @param endDate the end date
     * @return List of transactions within the date range
     */
    public List<AccountTransaction> getTransactionsByDateRange(String accountNo, LocalDate startDate, LocalDate endDate) {
        logger.debug("Fetching transactions for account: {} between {} and {}", accountNo, startDate, endDate);
        List<AccountTransaction> transactions = accountTransactionRepository.findByAccountNoAndValueDateBetween(accountNo, startDate, endDate);
        logger.info("Retrieved {} transactions for account: {} in date range", transactions.size(), accountNo);
        return transactions;
    }

    /**
     * Retrieve credit transactions for an account
     * @param accountNo the account number
     * @return List of credit transactions
     */
    public List<AccountTransaction> getCreditTransactions(String accountNo) {
        logger.debug("Fetching credit transactions for account: {}", accountNo);
        List<AccountTransaction> transactions = accountTransactionRepository.findByAccountNoAndTxType(accountNo, "Credit");
        logger.info("Retrieved {} credit transactions for account: {}", transactions.size(), accountNo);
        return transactions;
    }

    /**
     * Retrieve debit transactions for an account
     * @param accountNo the account number
     * @return List of debit transactions
     */
    public List<AccountTransaction> getDebitTransactions(String accountNo) {
        logger.debug("Fetching debit transactions for account: {}", accountNo);
        List<AccountTransaction> transactions = accountTransactionRepository.findByAccountNoAndTxType(accountNo, "Debit");
        logger.info("Retrieved {} debit transactions for account: {}", transactions.size(), accountNo);
        return transactions;
    }

    /**
     * Retrieve transactions by currency
     * @param currency the currency code
     * @return List of transactions in the specified currency
     */
    public List<AccountTransaction> getTransactionsByCurrency(String currency) {
        logger.debug("Fetching transactions by currency: {}", currency);
        List<AccountTransaction> transactions = accountTransactionRepository.findByCurrency(currency);
        logger.info("Retrieved {} transactions in currency: {}", transactions.size(), currency);
        return transactions;
    }

    /**
     * Create a new transaction
     * @param transaction the transaction to create
     * @return the created transaction
     */
    public AccountTransaction createTransaction(AccountTransaction transaction) {
        logger.debug("Creating new transaction for account: {}", transaction.getAccountNo());
        AccountTransaction savedTransaction = accountTransactionRepository.save(transaction);
        logger.info("Transaction created successfully with ID: {} for account: {}", savedTransaction.getId(), savedTransaction.getAccountNo());
        return savedTransaction;
    }

    /**
     * Update an existing transaction
     * @param transaction the transaction to update
     * @return the updated transaction
     */
    public AccountTransaction updateTransaction(AccountTransaction transaction) {
        logger.debug("Updating transaction: {}", transaction.getId());
        if (accountTransactionRepository.existsById(transaction.getId())) {
            AccountTransaction updatedTransaction = accountTransactionRepository.save(transaction);
            logger.info("Transaction updated successfully with ID: {}", transaction.getId());
            return updatedTransaction;
        } else {
            logger.error("Transaction not found for update with ID: {}", transaction.getId());
            throw new RuntimeException("Transaction not found with ID: " + transaction.getId());
        }
    }

    /**
     * Delete a transaction by ID
     * @param id the transaction ID
     */
    public void deleteTransaction(Long id) {
        logger.debug("Deleting transaction with ID: {}", id);
        if (accountTransactionRepository.existsById(id)) {
            accountTransactionRepository.deleteById(id);
            logger.info("Transaction deleted successfully with ID: {}", id);
        } else {
            logger.error("Transaction not found for deletion with ID: {}", id);
            throw new RuntimeException("Transaction not found with ID: " + id);
        }
    }
}
