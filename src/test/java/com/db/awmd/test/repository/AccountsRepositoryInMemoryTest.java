/**
 * 
 */
package com.db.awmd.test.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.OverDraftException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.test.DevChallengeApplicationTests;

/**
 * 
 * @author Souradip
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DevChallengeApplicationTests.class, loader = AnnotationConfigContextLoader.class)
public class AccountsRepositoryInMemoryTest {

	/**
	 * @throws java.lang.Exception
	 */

	@Autowired
	private AccountsRepository repository;

	Account account;

	Account fromAccount;

	Account toAccount;

	List<Account> accountList;

	@Before
	@PostConstruct
	public void setUp() throws Exception {

		account = new Account();

		account.setBalance(new BigDecimal(123.1));

		account.setAccountId("SO342090");

		fromAccount = account;

		toAccount = new Account();

		toAccount.setAccountId("AL342089");

		toAccount.setBalance(new BigDecimal(111.1));

		accountList = new ArrayList<>();
		accountList.add(fromAccount);
		accountList.add(toAccount);

	}

	/**
	 * Test method for
	 * {@link com.db.awmd.challenge.repository.AccountsRepositoryInMemory#createAccount(com.db.awmd.challenge.dto.AccountDTO)}.
	 */
	@Test
	public void testCreateAccount() {

		repository.createAccount(account);

	}

	/**
	 * Test method for
	 * {@link com.db.awmd.challenge.repository.AccountsRepositoryInMemory#getAccount(java.lang.String)}.
	 */
	@Test
	public void testGetAccount() {
		repository.getAccount("SO342090");
	}

	/**
	 * Test method for
	 * {@link com.db.awmd.challenge.repository.AccountsRepositoryInMemory#clearAccounts()}.
	 */
	@Test
	public void testClearAccounts() {

		repository.clearAccounts();

	}

	/**
	 * Test method for
	 * {@link com.db.awmd.challenge.repository.AccountsRepositoryInMemory#transferMoney(com.db.awmd.challenge.dto.AccountDTO, com.db.awmd.challenge.dto.AccountDTO, java.math.BigDecimal)}.
	 */
	@Test
	public void testTransferMoneyBetweenTwoAccounts() {

		TransferBetweenAccounts tr1 = new TransferBetweenAccounts(fromAccount, toAccount, new BigDecimal(50.48));

		TransferBetweenAccounts tr2 = new TransferBetweenAccounts(toAccount, fromAccount, new BigDecimal(50.49));

		Thread th1 = new Thread(tr1);

		Thread th2 = new Thread(tr2);

		th1.start();

		th2.start();

	}

	private class TransferBetweenAccounts implements Runnable {

		private BigDecimal amount;

		private Account account1;

		private Account account2;

		TransferBetweenAccounts(Account account1, Account account2, BigDecimal amount) {
			this.amount = amount;
			this.account1 = account1;
			this.account2 = account2;
		}

		@Override
		public void run() {

			try {

				repository.debit(account1, amount);
				repository.credit(account2, amount);

			} catch (OverDraftException e) {
				e.printStackTrace();
			}

		}

	}

}
