package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Souradip
 *
 */

@Getter
@Setter
public class TransferRequest {

	@NotEmpty
	@NotNull
	private String fromAccountId;

	@NotEmpty
	@NotNull
	private String toAccountId;

	@NotEmpty
	@NotNull
	@Min(value = 0, message = "Transfer amount should be positve")
	private BigDecimal amount;

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
