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
 * Entity to hold account view with PostgreSQL persistence
 * 
 * @author Suwath Mihindukulasooriya
 * @author Refactored for Java 21 with JPA
 * @date 2024
 */
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "account_no", unique = true, nullable = false, length = 20)
	@NotBlank(message = "Account number is required")
	private String accountNo;
	
	@Column(name = "account_name", nullable = false, length = 100)
	@NotBlank(message = "Account name is required")
	private String accountName;
	
	@Column(name = "account_type", nullable = false, length = 50)
	@NotBlank(message = "Account type is required")
	private String accountType;
	
	@Column(name = "balance_date", nullable = false)
	@NotNull(message = "Balance date is required")
	private LocalDate balanceDate;
	
	@Column(name = "currency", nullable = false, length = 3)
	@NotBlank(message = "Currency is required")
	private String currency;
	
	@Column(name = "opening_avail_bal", precision = 15, scale = 2, nullable = false)
	@NotNull(message = "Opening available balance is required")
	private BigDecimal openingAvailBal;
}
