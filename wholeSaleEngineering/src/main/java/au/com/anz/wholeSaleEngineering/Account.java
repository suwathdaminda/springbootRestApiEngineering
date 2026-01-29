package au.com.anz.wholeSaleEngineering;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * JPA Entity for Account
 * @author Suwath Mihindukulasooriya
 *
 */
@Entity
@Table(name = "accounts", schema = "public")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "account_no", unique = true, nullable = false, length = 50)
	private String accountNo;

	@Column(name = "account_name", nullable = false, length = 100)
	private String accountName;

	@Column(name = "account_type", nullable = false, length = 50)
	private String accountType;

	@Column(name = "balance_date")
	private LocalDate balanceDate;

	@Column(name = "currency", length = 3)
	private String currency;

	@Column(name = "opening_avail_bal", precision = 19, scale = 2)
	private BigDecimal openingAvailBal;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at")
	private LocalDate updatedAt;

	// Constructors
	public Account() {
	}

	public Account(String accountNo, String accountName, String accountType, 
				   LocalDate balanceDate, String currency, BigDecimal openingAvailBal) {
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.accountType = accountType;
		this.balanceDate = balanceDate;
		this.currency = currency;
		this.openingAvailBal = openingAvailBal;
		this.createdAt = LocalDate.now();
	}

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDate.now();
		updatedAt = LocalDate.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDate.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public LocalDate getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(LocalDate balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getOpeningAvailBal() {
		return openingAvailBal;
	}

	public void setOpeningAvailBal(BigDecimal openingAvailBal) {
		this.openingAvailBal = openingAvailBal;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Account)) return false;
		Account account = (Account) o;
		return Objects.equals(id, account.id) &&
				Objects.equals(accountNo, account.accountNo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, accountNo);
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", accountNo='" + accountNo + '\'' +
				", accountName='" + accountName + '\'' +
				", accountType='" + accountType + '\'' +
				", balanceDate=" + balanceDate +
				", currency='" + currency + '\'' +
				", openingAvailBal=" + openingAvailBal +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}
