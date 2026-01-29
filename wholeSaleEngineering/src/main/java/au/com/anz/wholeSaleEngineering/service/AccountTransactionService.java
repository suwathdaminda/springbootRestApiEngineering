package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import au.com.anz.wholeSaleEngineering.repository.AccountTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for AccountTransaction operations
 * 
 * @author Refactored for Java 21
 * @date 2024
 */
@Service
@Transactional
public class AccountTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionService.class);
    
    @Autowired
    private AccountTransactionRepository accountTransactionRepository;
    
    /**
     * Get all transactions
     * 
     * @return list of all transactions
     */
    public List<AccountTransaction> getAllTransactions() {
        logger.info("Fetching all transactions");
        List<AccountTransaction> transactions = accountTransactionRepository.findAll();
        logger.debug("Found {} transactions", transactions.size());
        return transactions;
    }
    
    /**
     * Get transaction by ID
     * 
     * @param id the transaction ID
     * @return Optional containing the transaction if found
     */
    public Optional<AccountTransaction> getTransactionById(Long id) {
        logger.info("Fetching transaction with ID: {}", id);
        Optional<AccountTransaction> transaction = accountTransactionRepository.findById(id);
        if (transaction.isPresent()) {
            logger.debug("Found transaction for account: {}", transaction.get().getAccountNo());
        } else {
            logger.warn("Transaction with ID {} not found", id);
        }
        return transaction;
    }
    
    /**
     * Get all transactions for a specific account number
     * 
     * @param accountNo the account number
     * @return list of transactions for the account
     */
    public List<AccountTransaction> getTransactionsByAccountNo(String accountNo) {
        logger.info("Fetching transactions for account number: {}", accountNo);
        List<AccountTransaction> transactions = accountTransactionRepository.findByAccountNoOrderByValueDateDesc(accountNo);
        logger.debug("Found {} transactions for account {}", transactions.size(), accountNo);
        return transactions;
    }
    
    /**
     * Create new transaction
     * 
     * @param transaction the transaction to create
     * @return the created transaction
     */
    public AccountTransaction createTransaction(AccountTransaction transaction) {
        logger.info("Creating new transaction for account: {}", transaction.getAccountNo());
        AccountTransaction savedTransaction = accountTransactionRepository.save(transaction);
        logger.info("Successfully created transaction with ID: {}", savedTransaction.getId());
        return savedTransaction;
    }
    
    /**
     * Update existing transaction
     * 
     * @param id the transaction ID
     * @param transactionDetails the updated transaction details
     * @return the updated transaction
     */
    public AccountTransaction updateTransaction(Long id, AccountTransaction transactionDetails) {
        logger.info("Updating transaction with ID: {}", id);
        AccountTransaction transaction = accountTransactionRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Transaction with ID {} not found for update", id);
                return new IllegalArgumentException("Transaction not found with id: " + id);
            });
        
        transaction.setAccountName(transactionDetails.getAccountName());
        transaction.setValueDate(transactionDetails.getValueDate());
        transaction.setCurrency(transactionDetails.getCurrency());
        transaction.setDebitAmt(transactionDetails.getDebitAmt());
        transaction.setCreditAmt(transactionDetails.getCreditAmt());
        transaction.setTxType(transactionDetails.getTxType());
        transaction.setTxNarrative(transactionDetails.getTxNarrative());
        
        AccountTransaction updatedTransaction = accountTransactionRepository.save(transaction);
        logger.info("Successfully updated transaction for account: {}", updatedTransaction.getAccountNo());
        return updatedTransaction;
    }
    
    /**
     * Delete transaction by ID
     * 
     * @param id the transaction ID
     */
    public void deleteTransaction(Long id) {
        logger.info("Deleting transaction with ID: {}", id);
        if (!accountTransactionRepository.existsById(id)) {
            logger.error("Transaction with ID {} not found for deletion", id);
            throw new IllegalArgumentException("Transaction not found with id: " + id);
        }
        accountTransactionRepository.deleteById(id);
        logger.info("Successfully deleted transaction with ID: {}", id);
    }
}
