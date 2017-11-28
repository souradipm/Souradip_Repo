package com.db.awmd.test.service;

import java.math.BigDecimal;

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
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.test.DevChallengeApplicationTests;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DevChallengeApplicationTests.class, loader = AnnotationConfigContextLoader.class)
public class AccountsServiceTest {

	@Autowired
	private AccountsRepository repository;

	private AccountsService service;

	private Account fromAccount;

	private Account toAccount;

	@Before
	public void setUp() throws Exception {

		service = new AccountsService(repository);

		fromAccount = new Account("SO342090");

		fromAccount.setBalance(new BigDecimal(123.1));

		toAccount = new Account("AL342089");

		toAccount.setBalance(new BigDecimal(111.1));

	}

	@Test
	public void testCreateAccount() {
		service.createAccount(fromAccount);
		service.createAccount(toAccount);
	}

	@Test
	public void testGetAccount() {
		service.getAccount("B34568");
	}

	@Test
	public void testTransferMoney() throws OverDraftException {
		try {
			service.transfer(fromAccount, toAccount, new BigDecimal(20.17));
		} catch (OverDraftException e) {
			throw e;
		}
	}

}
