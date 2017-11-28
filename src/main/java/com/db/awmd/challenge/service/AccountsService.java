package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.OverDraftException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	private Account fromLock, toLock;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	/**
	 * 
	 * @param fromAccount
	 * @param toAccount
	 * @param amount
	 * @return
	 * @throws OverDraftException
	 */
	public boolean transfer(Account fromAccount, Account toAccount, BigDecimal amount) throws OverDraftException {

		Account fromAcc = this.accountsRepository.getAccount(fromAccount.getAccountId());

		Account toAcc = this.accountsRepository.getAccount(toAccount.getAccountId());

		if (fromAccount.getAccountId().compareTo(toAccount.getAccountId()) > 0) {

			fromLock = fromAccount;
			toLock = toAccount;

		} else {
			fromLock = toAccount;
			toLock = fromAccount;
		}

		synchronized (fromLock) {
			synchronized (toLock) {
				if (this.accountsRepository.debit(fromAcc, amount) && this.accountsRepository.credit(toAcc, amount))
					return true;
			}

		}

		return false;
	}

}
