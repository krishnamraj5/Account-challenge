package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
@SpringBootApplication


public class AccountChallengeApplication {


	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {

		synchronized (AccountChallengeApplication.class) {
			ctx = SpringApplication.run(AccountChallengeApplication.class, args);
		}


	}

}
