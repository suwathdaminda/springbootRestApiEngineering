package au.com.anz.wholeSaleEngineering.repository;

import au.com.anz.wholeSaleEngineering.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA Repository for AccountTransaction Entity
 * @author Suwath Mihindukulasooriya
 */
@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    /**
     * Find all transactions for a specific account
     * @param accountNo the account number
     * @return List of transactions for the account
     */
    List<AccountTransaction> findByAccountNo(String accountNo);

    /**
     * Find transactions for an account within a date range
     * @param accountNo the account number
     * @param startDate the start date
     * @param endDate the end date
     * @return List of transactions within the date range
     */
    List<AccountTransaction> findByAccountNoAndValueDateBetween(String accountNo, LocalDate startDate, LocalDate endDate);

    /**
     * Find all credit transactions for an account
     * @param accountNo the account number
     * @param txType the transaction type ("Credit")
     * @return List of credit transactions
     */
    List<AccountTransaction> findByAccountNoAndTxType(String accountNo, String txType);

    /**
     * Find all transactions by currency
     * @param currency the currency code
     * @return List of transactions in the specified currency
     */
    List<AccountTransaction> findByCurrency(String currency);
}
