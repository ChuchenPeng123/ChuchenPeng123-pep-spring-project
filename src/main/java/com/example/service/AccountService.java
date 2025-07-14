//package com.example.service;

//public class AccountService {
//}
package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class to handle business logic for Accounts.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Registers a new account after performing validation.
     *
     * @param account The account to register.
     * @return The registered account if successful, otherwise null.
     */
    public Account registerAccount(Account account) {
        // Username must not be blank and password must be 4+ characters
        if (account.getUsername() != null && !account.getUsername().isBlank() &&
            account.getPassword() != null && account.getPassword().length() >= 4 &&
            accountRepository.findAccountByUsername(account.getUsername()) == null) {
            return accountRepository.save(account);
        }
        return null;
    }

    /**
     * Logs in a user by verifying their credentials.
     *
     * @param credentials The account containing login credentials.
     * @return The logged-in account if credentials are valid, otherwise null.
     */
    public Account loginAccount(Account credentials) {
        Account accountFromDb = accountRepository.findAccountByUsername(credentials.getUsername());
        // Check if account exists and if the password matches
        if (accountFromDb != null && accountFromDb.getPassword().equals(credentials.getPassword())) {
            return accountFromDb;
        }
        return null;
    }
}
