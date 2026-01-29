package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Account Transaction operations
 * Refactored to use PostgreSQL database via Spring Data JPA
 * 
 * @author Suwath Mihindukulasooriya
 * @author Refactored for Java 21 with PostgreSQL
 * @date 2024
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Account Transaction Management", description = "APIs for managing account transactions")
public class AccountTransactionServiceController {

	private static final Logger logger = LoggerFactory.getLogger(AccountTransactionServiceController.class);
	
	@Autowired
	private AccountTransactionService accountTransactionService;
	
	/**
	 * Get all transactions
	 */
	@Operation(summary = "Get all transactions", description = "Retrieve all account transactions from database")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved transactions",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping("/transactions")
	public ResponseEntity<List<AccountTransaction>> getAllTransactions() {
		logger.info("REST request to get all transactions");
		try {
			List<AccountTransaction> transactions = accountTransactionService.getAllTransactions();
			logger.info("Successfully retrieved {} transactions", transactions.size());
			return ResponseEntity.ok(transactions);
		} catch (Exception e) {
			logger.error("Error retrieving transactions", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Get transactions by account number (Legacy endpoint)
	 */
	@Operation(summary = "Get transactions by account number", description = "Retrieve all transactions for a specific account")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved transactions",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))),
		@ApiResponse(responseCode = "404", description = "No transactions found for account"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping("/accountTransaction/{accountNo}")
	public ResponseEntity<List<AccountTransaction>> getAccountTransaction(
			@Parameter(description = "Account Number", required = true) @PathVariable("accountNo") String accountNo) {
		logger.info("REST request to get transactions for account: {}", accountNo);
		try {
			List<AccountTransaction> transactions = accountTransactionService.getTransactionsByAccountNo(accountNo);
			if (transactions.isEmpty()) {
				logger.warn("No transactions found for account: {}", accountNo);
				return ResponseEntity.notFound().build();
			}
			logger.info("Successfully retrieved {} transactions for account: {}", transactions.size(), accountNo);
			return ResponseEntity.ok(transactions);
		} catch (Exception e) {
			logger.error("Error retrieving transactions for account: {}", accountNo, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Get transaction by ID
	 */
	@Operation(summary = "Get transaction by ID", description = "Retrieve a specific transaction by its ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Transaction found",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))),
		@ApiResponse(responseCode = "404", description = "Transaction not found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping("/transactions/{id}")
	public ResponseEntity<AccountTransaction> getTransactionById(
			@Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
		logger.info("REST request to get transaction with ID: {}", id);
		try {
			return accountTransactionService.getTransactionById(id)
					.map(transaction -> {
						logger.info("Transaction found for account: {}", transaction.getAccountNo());
						return ResponseEntity.ok(transaction);
					})
					.orElseGet(() -> {
						logger.warn("Transaction with ID {} not found", id);
						return ResponseEntity.notFound().build();
					});
		} catch (Exception e) {
			logger.error("Error retrieving transaction with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Create new transaction
	 */
	@Operation(summary = "Create transaction", description = "Create a new account transaction")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Transaction created successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))),
		@ApiResponse(responseCode = "400", description = "Invalid input"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PostMapping("/transactions")
	public ResponseEntity<AccountTransaction> createTransaction(
			@Parameter(description = "Transaction object to create", required = true)
			@Valid @RequestBody AccountTransaction transaction) {
		logger.info("REST request to create transaction for account: {}", transaction.getAccountNo());
		try {
			AccountTransaction createdTransaction = accountTransactionService.createTransaction(transaction);
			logger.info("Successfully created transaction with ID: {}", createdTransaction.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
		} catch (Exception e) {
			logger.error("Error creating transaction", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Update existing transaction
	 */
	@Operation(summary = "Update transaction", description = "Update an existing account transaction")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Transaction updated successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransaction.class))),
		@ApiResponse(responseCode = "404", description = "Transaction not found"),
		@ApiResponse(responseCode = "400", description = "Invalid input"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PutMapping("/transactions/{id}")
	public ResponseEntity<AccountTransaction> updateTransaction(
			@Parameter(description = "Transaction ID", required = true) @PathVariable Long id,
			@Parameter(description = "Updated transaction object", required = true)
			@Valid @RequestBody AccountTransaction transaction) {
		logger.info("REST request to update transaction with ID: {}", id);
		try {
			AccountTransaction updatedTransaction = accountTransactionService.updateTransaction(id, transaction);
			logger.info("Successfully updated transaction for account: {}", updatedTransaction.getAccountNo());
			return ResponseEntity.ok(updatedTransaction);
		} catch (IllegalArgumentException e) {
			logger.error("Transaction not found or invalid data: {}", e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("Error updating transaction with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Delete transaction
	 */
	@Operation(summary = "Delete transaction", description = "Delete an existing account transaction")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Transaction not found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@DeleteMapping("/transactions/{id}")
	public ResponseEntity<Void> deleteTransaction(
			@Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
		logger.info("REST request to delete transaction with ID: {}", id);
		try {
			accountTransactionService.deleteTransaction(id);
			logger.info("Successfully deleted transaction with ID: {}", id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			logger.error("Transaction not found: {}", e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("Error deleting transaction with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
