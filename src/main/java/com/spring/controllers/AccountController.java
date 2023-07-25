package com.spring.controllers;

import java.util.List;

import com.spring.entities.Account;
import com.spring.exceptions.AccountException;
import com.spring.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@Slf4j
@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            account = accountService.createAccount(account);
            if(account!=null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(account);
            }
        }
        catch (AccountException.InvalidCreditException e) {
            log.error("Account not created because of invalid credit : " + account.getmonthlyExpenses());
        }
        catch (AccountException.AccountAlreadyExistsException e) {
            log.error("Account already exists for the email: " + account.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/get")
    public ResponseEntity<Account> getAccountByEmail(@RequestParam String email) {
        try {
            Account account = accountService.getAccountByEmail(email);
            if(account!=null){
            log.info("Account retrieved successfully for the email: " + email);
            return ResponseEntity.ok(account);
            }
        } catch (AccountNotFoundException e) {
            log.error("Account not found for the email: " + email);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/listAccounts")
    public ResponseEntity<List<Account>> listAllAccounts() {
        List<Account> accounts = accountService.listAllAccounts();
        log.info("All accounts retrieved successfully");
        return ResponseEntity.ok(accounts);
    }
}