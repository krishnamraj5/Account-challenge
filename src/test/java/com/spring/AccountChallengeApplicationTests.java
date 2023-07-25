package com.spring;

import com.spring.controllers.AccountController;
import com.spring.entities.Account;
import com.spring.exceptions.AccountException;
import com.spring.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ApiChallengeApplicationTests {

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountController accountController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateAccount() {
		Account account = new Account();
		account.setEmail("kumar.rajeev@gmail.com");
		when(accountService.createAccount(any(Account.class))).thenReturn(account);

		ResponseEntity<Account> response = accountController.createAccount(account);

		verify(accountService, times(1)).createAccount(account);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(account, response.getBody());
	}

	@Test
	void testGetAccountByEmail() throws AccountNotFoundException {
		Account account = new Account();
		account.setEmail("kumar.rajeev@gmail.com");
		when(accountService.getAccountByEmail("kumar.rajeev@gmail.com")).thenReturn(account);
		when(accountService.getAccountByEmail("krishnn@gmail.com")).thenThrow(new AccountNotFoundException());

		ResponseEntity<Account> response1 = accountController.getAccountByEmail("kumar.rajeev@gmail.com");
		ResponseEntity<Account> response2 = accountController.getAccountByEmail("krishnn@gmail.com");

		verify(accountService, times(1)).getAccountByEmail("kumar.rajeev@gmail.com");
		verify(accountService, times(1)).getAccountByEmail("krishnn@gmail.com");
		assertEquals(HttpStatus.OK, response1.getStatusCode());
		assertEquals(account, response1.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
		assertNull(response2.getBody());
	}

	@Test
	void testListAllAccounts() {
		Account account1 = new Account();
		account1.setEmail("kumar.rajeev@gmail.com");
		Account account2 = new Account();
		account2.setEmail("springrajukumar@gmail.com");
		List<Account> accounts = Arrays.asList(account1, account2);
		when(accountService.listAllAccounts()).thenReturn(accounts);

		ResponseEntity<List<Account>> response = accountController.listAllAccounts();

		verify(accountService, times(1)).listAllAccounts();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(accounts, response.getBody());
	}

	@Test
	void testCreateAccount_AccountAlreadyExists() {
		Account account = new Account();
		account.setEmail("chandramahendra@gmail.com");

		when(accountService.createAccount(account)).thenThrow(new AccountException.AccountAlreadyExistsException("The Account already exists for the email "+ account.getEmail()));

		ResponseEntity<Account> response = accountController.createAccount(account);

		verify(accountService, times(1)).createAccount(account);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void testCreateAccount_InvalidCredit() {
		Account account = new Account();
		account.setEmail("chandramahendra@gmail.com");
		account.setmonthlyExpenses(-100.0); // Set an invalid credit amount

		when(accountService.createAccount(account)).thenThrow(new AccountException.InvalidCreditException("That's an Invalid credit " + account.getmonthlyExpenses()));

		ResponseEntity<Account> response = accountController.createAccount(account);

		verify(accountService, times(1)).createAccount(account);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void testGetAccountById_AccountNotFound() throws AccountNotFoundException {
		String email = "springrajukumar@gmail.com";
		// Mock the behavior when an account is not found
		when(accountService.getAccountByEmail(email)).thenThrow(new AccountNotFoundException());

		ResponseEntity<Account> response = accountController.getAccountByEmail(email);

		verify(accountService, times(1)).getAccountByEmail(email);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
	@Test
	void testCreateAccount_InsufficientCredit() {

		Account accountRequest = new Account();
		accountRequest.setmonthlySalary(5000);
		accountRequest.setmonthlyExpenses(4500);

		when(accountService.createAccount(any(Account.class))).thenReturn(null);

		ResponseEntity<Account> response = accountController.createAccount(accountRequest);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertNull(response.getBody());
	}
}
