package com.db.awmd.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.db.awmd.challenge.service.EmailNotificationService;
import com.db.awmd.challenge.service.NotificationService;

/**
 * 
 * @author Souradip
 *
 */

@SpringBootApplication
public class DevChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevChallengeApplication.class, args);
	}

	@Bean
	public NotificationService notificationService() {

		return new EmailNotificationService();
	}
}
