package au.com.anz.wholeSaleEngineering;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * JPA Entity for Account Transaction Details
 * @author Suwath Mihindukulasooriya
 *
 */
@Entity
@Table(name = "account_transactions", schema = "public")
public class AccountTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "account_no", nullable = false, length = 50)
	private String accountNo;

	@Column(name = "account_name", length = 100)
	private String accountName;

	@Column(name = "value_date")
	private LocalDate valueDate;

	@Column(name = "currency", length = 3)
	private String currency;

	@Column(name = "debit_amt", precision = 19, scale = 2)
	private BigDecimal debitAmt;

	@Column(name = "credit_amt", precision = 19, scale = 2)
	private BigDecimal creditAmt;

	@Column(name = "tx_type", length = 50)
	private String txType;

	@Column(name = "tx_narrative", columnDefinition = "TEXT")
	private String txNarrative;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at")
	private LocalDate updatedAt;

	// Constructors
	public AccountTransaction() {
	}

	public AccountTransaction(String accountNo, String accountName, LocalDate valueDate, 
							 String currency, BigDecimal debitAmt, BigDecimal creditAmt, 
							 String txType, String txNarrative) {
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.valueDate = valueDate;
		this.currency = currency;
		this.debitAmt = debitAmt;
		this.creditAmt = creditAmt;
		this.txType = txType;
		this.txNarrative = txNarrative;
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

	public LocalDate getValueDate() {
		return valueDate;
	}

	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getDebitAmt() {
		return debitAmt;
	}

	public void setDebitAmt(BigDecimal debitAmt) {
		this.debitAmt = debitAmt;
	}

	public BigDecimal getCreditAmt() {
		return creditAmt;
	}

	public void setCreditAmt(BigDecimal creditAmt) {
		this.creditAmt = creditAmt;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTxNarrative() {
		return txNarrative;
	}

	public void setTxNarrative(String txNarrative) {
		this.txNarrative = txNarrative;
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
		if (!(o instanceof AccountTransaction)) return false;
		AccountTransaction that = (AccountTransaction) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "AccountTransaction{" +
				"id=" + id +
				", accountNo='" + accountNo + '\'' +
				", accountName='" + accountName + '\'' +
				", valueDate=" + valueDate +
				", currency='" + currency + '\'' +
				", debitAmt=" + debitAmt +
				", creditAmt=" + creditAmt +
				", txType='" + txType + '\'' +
				", txNarrative='" + txNarrative + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}
