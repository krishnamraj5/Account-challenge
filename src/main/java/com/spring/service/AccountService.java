package com.spring.service;

import com.spring.entities.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
public interface AccountService {
    Account createAccount(Account account);
    Account getAccountByEmail(String email) throws AccountNotFoundException;
   List<Account> listAllAccounts();
}