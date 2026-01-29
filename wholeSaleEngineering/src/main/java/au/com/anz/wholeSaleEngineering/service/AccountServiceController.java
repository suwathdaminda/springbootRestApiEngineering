package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.Account;
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
 * REST Controller for Account operations
 * Refactored to use PostgreSQL database via Spring Data JPA
 * 
 * @author Suwath Mihindukulasooriya
 * @author Refactored for Java 21 with PostgreSQL
 * @date 2024
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Account Management", description = "APIs for managing customer accounts")
public class AccountServiceController {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceController.class);
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * Get all accounts
	 */
	@Operation(summary = "Get all accounts", description = "Retrieve all customer accounts from database")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved accounts",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping("/accountDetail")
	public ResponseEntity<List<Account>> getAllAccounts() {
		logger.info("REST request to get all accounts");
		try {
			List<Account> accounts = accountService.getAllAccounts();
			logger.info("Successfully retrieved {} accounts", accounts.size());
			return ResponseEntity.ok(accounts);
		} catch (Exception e) {
			logger.error("Error retrieving accounts", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Get account by ID
	 */
	@Operation(summary = "Get account by ID", description = "Retrieve a specific account by its ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Account found",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
		@ApiResponse(responseCode = "404", description = "Account not found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping("/accounts/{id}")
	public ResponseEntity<Account> getAccountById(
			@Parameter(description = "Account ID", required = true) @PathVariable Long id) {
		logger.info("REST request to get account with ID: {}", id);
		try {
			return accountService.getAccountById(id)
					.map(account -> {
						logger.info("Account found: {}", account.getAccountNo());
						return ResponseEntity.ok(account);
					})
					.orElseGet(() -> {
						logger.warn("Account with ID {} not found", id);
						return ResponseEntity.notFound().build();
					});
		} catch (Exception e) {
			logger.error("Error retrieving account with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Get account by account number
	 */
	@Operation(summary = "Get account by account number", description = "Retrieve a specific account by its account number")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Account found",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
		@ApiResponse(responseCode = "404", description = "Account not found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping("/accounts/byNumber/{accountNo}")
	public ResponseEntity<Account> getAccountByAccountNo(
			@Parameter(description = "Account Number", required = true) @PathVariable String accountNo) {
		logger.info("REST request to get account with number: {}", accountNo);
		try {
			return accountService.getAccountByAccountNo(accountNo)
					.map(account -> {
						logger.info("Account found: {}", account.getAccountName());
						return ResponseEntity.ok(account);
					})
					.orElseGet(() -> {
						logger.warn("Account with number {} not found", accountNo);
						return ResponseEntity.notFound().build();
					});
		} catch (Exception e) {
			logger.error("Error retrieving account with number: {}", accountNo, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Create new account
	 */
	@Operation(summary = "Create account", description = "Create a new customer account")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Account created successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
		@ApiResponse(responseCode = "400", description = "Invalid input"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PostMapping("/accounts")
	public ResponseEntity<Account> createAccount(
			@Parameter(description = "Account object to create", required = true)
			@Valid @RequestBody Account account) {
		logger.info("REST request to create account: {}", account.getAccountNo());
		try {
			Account createdAccount = accountService.createAccount(account);
			logger.info("Successfully created account with ID: {}", createdAccount.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
		} catch (IllegalArgumentException e) {
			logger.error("Invalid account data: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			logger.error("Error creating account", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Update existing account
	 */
	@Operation(summary = "Update account", description = "Update an existing customer account")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Account updated successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),
		@ApiResponse(responseCode = "404", description = "Account not found"),
		@ApiResponse(responseCode = "400", description = "Invalid input"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PutMapping("/accounts/{id}")
	public ResponseEntity<Account> updateAccount(
			@Parameter(description = "Account ID", required = true) @PathVariable Long id,
			@Parameter(description = "Updated account object", required = true)
			@Valid @RequestBody Account account) {
		logger.info("REST request to update account with ID: {}", id);
		try {
			Account updatedAccount = accountService.updateAccount(id, account);
			logger.info("Successfully updated account: {}", updatedAccount.getAccountNo());
			return ResponseEntity.ok(updatedAccount);
		} catch (IllegalArgumentException e) {
			logger.error("Account not found or invalid data: {}", e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("Error updating account with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Delete account
	 */
	@Operation(summary = "Delete account", description = "Delete an existing customer account")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Account deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Account not found"),
		@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@DeleteMapping("/accounts/{id}")
	public ResponseEntity<Void> deleteAccount(
			@Parameter(description = "Account ID", required = true) @PathVariable Long id) {
		logger.info("REST request to delete account with ID: {}", id);
		try {
			accountService.deleteAccount(id);
			logger.info("Successfully deleted account with ID: {}", id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			logger.error("Account not found: {}", e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error("Error deleting account with ID: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
