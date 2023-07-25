package com.spring.serviceimpl;

import com.spring.entities.Account;
import com.spring.repos.AccountRepository;
import com.spring.exceptions.AccountException;
import com.spring.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import static com.spring.constants.Constants.MIN_CREDIT;
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository repository;

    public Account createAccount(Account account) {
        try {
            log.info("Creating account: " + account);
            if(validateCredit(account) && validateEmail(account))
            {
                account = repository.save(account);
                log.info("Account created successfully " + account);
                return account;
            }
        } catch (Exception e) {
            log.error("the error is ", e);
            throw new AccountException.AccountCreationFailedException("Couldn't create account");
        }
        return null;
    }
    public Account getAccountByEmail(String email) throws AccountNotFoundException {
        Account account = repository.findByEmail(email);
            try {
                if(account.getEmail()==null)
                {
                    return null;
                }
            } catch (Exception e) {
                throw new AccountNotFoundException("Account not found with email: " + email);
            }
            return account;
    }

    public List<Account> listAllAccounts() {
        try {
            return (List<Account>) repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error retrieving accounts", e);
        }
    }
    public static boolean validateCredit(Account account)
    {
        double availableCredit = account.getmonthlySalary() - account.getmonthlyExpenses();
        if (availableCredit < MIN_CREDIT) {
            log.error("Insufficient credit "+ availableCredit);
            return false;
        }
        return true;
    }
    public boolean validateEmail(Account account)
    {
        if (repository.existsById(account.getEmail())) {
            log.error("Account already exits with the email ",account.getEmail());
            return false;
        }
        return true;
    }
}