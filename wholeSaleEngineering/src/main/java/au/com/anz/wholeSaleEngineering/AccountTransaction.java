package au.com.anz.wholeSaleEngineering;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity for Account Transaction Details with PostgreSQL persistence
 * 
 * @author Suwath Mihindukulasooriya
 * @author Refactored for Java 21 with JPA
 * @date 2024
 */
@Entity
@Table(name = "account_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "account_no", nullable = false, length = 20)
	@NotBlank(message = "Account number is required")
	private String accountNo;
	
	@Column(name = "account_name", nullable = false, length = 100)
	@NotBlank(message = "Account name is required")
	private String accountName;
	
	@Column(name = "value_date", nullable = false)
	@NotNull(message = "Value date is required")
	private LocalDate valueDate;
	
	@Column(name = "currency", nullable = false, length = 3)
	@NotBlank(message = "Currency is required")
	private String currency;
	
	@Column(name = "debit_amt", precision = 15, scale = 2)
	private BigDecimal debitAmt;
	
	@Column(name = "credit_amt", precision = 15, scale = 2)
	private BigDecimal creditAmt;
	
	@Column(name = "tx_type", nullable = false, length = 50)
	@NotBlank(message = "Transaction type is required")
	private String txType;
	
	@Column(name = "tx_narrative", length = 500)
	private String txNarrative;
}
