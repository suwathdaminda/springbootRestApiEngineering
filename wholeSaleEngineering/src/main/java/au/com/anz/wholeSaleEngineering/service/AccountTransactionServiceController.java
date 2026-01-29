package au.com.anz.wholeSaleEngineering.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST Controller for Account Transaction operations
 * Provides REST endpoints for transaction management
 * @author Suwath Mihindukulasooriya
 */
@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Management", description = "Endpoints for managing account transactions")
public class AccountTransactionServiceController {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionServiceController.class);

    @Autowired
    private AccountTransactionService accountTransactionService;

    /**
     * Get all transactions
     * @return List of all transactions
     */
    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve all transactions from the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all transactions")
    public ResponseEntity<List<AccountTransaction>> getAllTransactions() {
        logger.info("GET /api/v1/transactions - Retrieving all transactions");
        List<AccountTransaction> transactions = accountTransactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Get transaction by ID
     * @param id the transaction ID
     * @return the transaction if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Retrieve a specific transaction by its ID")
    @ApiResponse(responseCode = "200", description = "Transaction found")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    public ResponseEntity<AccountTransaction> getTransactionById(
            @Parameter(description = "Transaction ID") @PathVariable Long id) {
        logger.info("GET /api/v1/transactions/{} - Retrieving transaction by ID", id);
        Optional<AccountTransaction> transaction = accountTransactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        } else {
            logger.warn("Transaction not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get transactions by account number
     * @param accountNo the account number
     * @return List of transactions for the account
     */
    @GetMapping("/account/{accountNo}")
    @Operation(summary = "Get transactions by account number", description = "Retrieve all transactions for a specific account")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions")
    public ResponseEntity<List<AccountTransaction>> getTransactionsByAccountNo(
            @Parameter(description = "Account Number") @PathVariable String accountNo) {
        logger.info("GET /api/v1/transactions/account/{} - Retrieving transactions for account", accountNo);
        List<AccountTransaction> transactions = accountTransactionService.getTransactionsByAccountNo(accountNo);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Get transactions by date range
     * @param accountNo the account number
     * @param startDate the start date
     * @param endDate the end date
     * @return List of transactions within the date range
     */
    @GetMapping("/account/{accountNo}/range")
    @Operation(summary = "Get transactions by date range", description = "Retrieve transactions for an account within a date range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions")
    public ResponseEntity<List<AccountTransaction>> getTransactionsByDateRange(
            @Parameter(description = "Account Number") @PathVariable String accountNo,
            @Parameter(description = "Start Date (yyyy-MM-dd)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End Date (yyyy-MM-dd)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        logger.info("GET /api/v1/transactions/account/{}/range - Retrieving transactions between {} and {}", accountNo, startDate, endDate);
        List<AccountTransaction> transactions = accountTransactionService.getTransactionsByDateRange(accountNo, startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Get credit transactions for an account
     * @param accountNo the account number
     * @return List of credit transactions
     */
    @GetMapping("/account/{accountNo}/credit")
    @Operation(summary = "Get credit transactions", description = "Retrieve all credit transactions for an account")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved credit transactions")
    public ResponseEntity<List<AccountTransaction>> getCreditTransactions(
            @Parameter(description = "Account Number") @PathVariable String accountNo) {
        logger.info("GET /api/v1/transactions/account/{}/credit - Retrieving credit transactions", accountNo);
        List<AccountTransaction> transactions = accountTransactionService.getCreditTransactions(accountNo);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Get debit transactions for an account
     * @param accountNo the account number
     * @return List of debit transactions
     */
    @GetMapping("/account/{accountNo}/debit")
    @Operation(summary = "Get debit transactions", description = "Retrieve all debit transactions for an account")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved debit transactions")
    public ResponseEntity<List<AccountTransaction>> getDebitTransactions(
            @Parameter(description = "Account Number") @PathVariable String accountNo) {
        logger.info("GET /api/v1/transactions/account/{}/debit - Retrieving debit transactions", accountNo);
        List<AccountTransaction> transactions = accountTransactionService.getDebitTransactions(accountNo);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Get transactions by currency
     * @param currency the currency code
     * @return List of transactions in the specified currency
     */
    @GetMapping("/currency/{currency}")
    @Operation(summary = "Get transactions by currency", description = "Retrieve all transactions in a specific currency")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions by currency")
    public ResponseEntity<List<AccountTransaction>> getTransactionsByCurrency(
            @Parameter(description = "Currency Code") @PathVariable String currency) {
        logger.info("GET /api/v1/transactions/currency/{} - Retrieving transactions by currency", currency);
        List<AccountTransaction> transactions = accountTransactionService.getTransactionsByCurrency(currency);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Create a new transaction
     * @param transaction the transaction to create
     * @return the created transaction
     */
    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Create a new transaction in the database")
    @ApiResponse(responseCode = "201", description = "Transaction created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid transaction data")
    public ResponseEntity<AccountTransaction> createTransaction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Transaction data")
            @RequestBody AccountTransaction transaction) {
        logger.info("POST /api/v1/transactions - Creating new transaction for account: {}", transaction.getAccountNo());
        AccountTransaction createdTransaction = accountTransactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * Update an existing transaction
     * @param id the transaction ID
     * @param transaction the updated transaction data
     * @return the updated transaction
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction", description = "Update an existing transaction")
    @ApiResponse(responseCode = "200", description = "Transaction updated successfully")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    public ResponseEntity<AccountTransaction> updateTransaction(
            @Parameter(description = "Transaction ID") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated transaction data")
            @RequestBody AccountTransaction transaction) {
        logger.info("PUT /api/v1/transactions/{} - Updating transaction", id);
        transaction.setId(id);
        try {
            AccountTransaction updatedTransaction = accountTransactionService.updateTransaction(transaction);
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error updating transaction: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a transaction
     * @param id the transaction ID
     * @return response entity
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction", description = "Delete a transaction from the database")
    @ApiResponse(responseCode = "204", description = "Transaction deleted successfully")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "Transaction ID") @PathVariable Long id) {
        logger.info("DELETE /api/v1/transactions/{} - Deleting transaction", id);
        try {
            accountTransactionService.deleteTransaction(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error deleting transaction: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

