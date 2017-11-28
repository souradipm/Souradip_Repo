package com.db.awmd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DevChallengeApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Bean
	public AccountsRepository repository(){
		return new AccountsRepositoryInMemory();
	}

}
