package net.medrag.account_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AccountService {

	public static void main(String[] args) {
		SpringApplication.run(AccountService.class, args);
	}

}
