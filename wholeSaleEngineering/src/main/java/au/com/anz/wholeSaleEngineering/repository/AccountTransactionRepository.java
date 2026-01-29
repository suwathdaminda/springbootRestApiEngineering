package au.com.anz.wholeSaleEngineering.repository;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for AccountTransaction entity
 * 
 * @author Refactored for Java 21 with Spring Data JPA
 * @date 2024
 */
@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
    
    /**
     * Find all transactions for a specific account number
     * 
     * @param accountNo the account number
     * @return list of transactions for the account
     */
    List<AccountTransaction> findByAccountNo(String accountNo);
    
    /**
     * Find all transactions for a specific account number ordered by value date descending
     * 
     * @param accountNo the account number
     * @return list of transactions ordered by date
     */
    List<AccountTransaction> findByAccountNoOrderByValueDateDesc(String accountNo);
}
