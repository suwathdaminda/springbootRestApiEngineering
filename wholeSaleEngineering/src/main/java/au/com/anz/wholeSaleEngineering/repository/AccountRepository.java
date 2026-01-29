package au.com.anz.wholeSaleEngineering.repository;

import au.com.anz.wholeSaleEngineering.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Account entity
 * 
 * @author Refactored for Java 21 with Spring Data JPA
 * @date 2024
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    /**
     * Find account by account number
     * 
     * @param accountNo the account number
     * @return Optional containing the account if found
     */
    Optional<Account> findByAccountNo(String accountNo);
    
    /**
     * Check if account exists by account number
     * 
     * @param accountNo the account number
     * @return true if exists, false otherwise
     */
    boolean existsByAccountNo(String accountNo);
}
