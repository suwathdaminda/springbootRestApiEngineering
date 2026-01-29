package au.com.anz.wholeSaleEngineering.service;

import au.com.anz.wholeSaleEngineering.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Account operations
 * Provides REST endpoints for account management
 * @author Suwath Mihindukulasooriya
 */
@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Management", description = "Endpoints for managing accounts")
public class AccountServiceController {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceController.class);

    @Autowired
    private AccountService accountService;

    /**
     * Get all accounts
     * @return List of all accounts
     */
    @GetMapping
    @Operation(summary = "Get all accounts", description = "Retrieve all accounts from the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        logger.info("GET /api/v1/accounts - Retrieving all accounts");
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Get account by ID
     * @param id the account ID
     * @return the account if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieve a specific account by its ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Account> getAccountById(
            @Parameter(description = "Account ID") @PathVariable Long id) {
        logger.info("GET /api/v1/accounts/{} - Retrieving account by ID", id);
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            logger.warn("Account not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get account by account number
     * @param accountNo the account number
     * @return the account if found
     */
    @GetMapping("/number/{accountNo}")
    @Operation(summary = "Get account by account number", description = "Retrieve a specific account by its account number")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Account> getAccountByAccountNo(
            @Parameter(description = "Account Number") @PathVariable String accountNo) {
        logger.info("GET /api/v1/accounts/number/{} - Retrieving account by account number", accountNo);
        Optional<Account> account = accountService.getAccountByAccountNo(accountNo);
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            logger.warn("Account not found with account number: {}", accountNo);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get accounts by type
     * @param type the account type
     * @return List of accounts of the specified type
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "Get accounts by type", description = "Retrieve all accounts of a specific type")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved accounts by type")
    public ResponseEntity<List<Account>> getAccountsByType(
            @Parameter(description = "Account Type") @PathVariable String type) {
        logger.info("GET /api/v1/accounts/type/{} - Retrieving accounts by type", type);
        List<Account> accounts = accountService.getAccountsByType(type);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Get accounts by currency
     * @param currency the currency code
     * @return List of accounts in the specified currency
     */
    @GetMapping("/currency/{currency}")
    @Operation(summary = "Get accounts by currency", description = "Retrieve all accounts in a specific currency")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved accounts by currency")
    public ResponseEntity<List<Account>> getAccountsByCurrency(
            @Parameter(description = "Currency Code") @PathVariable String currency) {
        logger.info("GET /api/v1/accounts/currency/{} - Retrieving accounts by currency", currency);
        List<Account> accounts = accountService.getAccountsByCurrency(currency);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Create a new account
     * @param account the account to create
     * @return the created account
     */
    @PostMapping
    @Operation(summary = "Create a new account", description = "Create a new account in the database")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid account data")
    public ResponseEntity<Account> createAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account data")
            @RequestBody Account account) {
        logger.info("POST /api/v1/accounts - Creating new account: {}", account.getAccountNo());
        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    /**
     * Update an existing account
     * @param id the account ID
     * @param account the updated account data
     * @return the updated account
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an account", description = "Update an existing account")
    @ApiResponse(responseCode = "200", description = "Account updated successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Account> updateAccount(
            @Parameter(description = "Account ID") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated account data")
            @RequestBody Account account) {
        logger.info("PUT /api/v1/accounts/{} - Updating account", id);
        account.setId(id);
        try {
            Account updatedAccount = accountService.updateAccount(account);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error updating account: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete an account
     * @param id the account ID
     * @return response entity
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account", description = "Delete an account from the database")
    @ApiResponse(responseCode = "204", description = "Account deleted successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "Account ID") @PathVariable Long id) {
        logger.info("DELETE /api/v1/accounts/{} - Deleting account", id);
        try {
            accountService.deleteAccount(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error deleting account: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

