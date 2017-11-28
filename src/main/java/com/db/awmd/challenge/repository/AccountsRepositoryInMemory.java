package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.OverDraftException;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	/**
	 * debit functionality added-Souradip
	 */
	public boolean debit(Account fromAccount, BigDecimal amount) throws OverDraftException {

		if (fromAccount.getAccountId() != null && fromAccount.getBalance().doubleValue() > amount.doubleValue()) {

			fromAccount = accounts.get(fromAccount.getAccountId());

			if (fromAccount != null) {
				BigDecimal baln = fromAccount.getBalance().subtract(amount);

				if (baln.doubleValue() > 0) {
					fromAccount.setBalance(baln);
					return true;
				} else
					return false;
			} else
				throw new NullPointerException("Non Existant Account Id");

		} else
			throw new OverDraftException("Account does not have sufficient Balance");
	}

	/**
	 * credit functionality added-Souradip
	 */
	public boolean credit(Account toAccount, BigDecimal amount) throws NullPointerException {
		if (amount.doubleValue() > 0 && toAccount.getAccountId() != null) {
			toAccount = accounts.get(toAccount.getAccountId());
			BigDecimal baln = toAccount.getBalance().add(amount);
			toAccount.setBalance(baln);
			return true;
		} else
			throw new NullPointerException("Account ID cannot be null");
	}

}
