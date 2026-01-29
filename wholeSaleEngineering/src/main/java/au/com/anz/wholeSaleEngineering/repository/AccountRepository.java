package au.com.anz.wholeSaleEngineering.repository;

import au.com.anz.wholeSaleEngineering.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Account Entity
 * @author Suwath Mihindukulasooriya
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find account by account number
     * @param accountNo the account number
     * @return Optional containing the account if found
     */
    Optional<Account> findByAccountNo(String accountNo);

    /**
     * Find all accounts by account type
     * @param accountType the account type (e.g., "Savings", "Current")
     * @return List of accounts matching the type
     */
    List<Account> findByAccountType(String accountType);

    /**
     * Find all accounts by currency
     * @param currency the currency code (e.g., "AUD", "SGD")
     * @return List of accounts matching the currency
     */
    List<Account> findByCurrency(String currency);
}
