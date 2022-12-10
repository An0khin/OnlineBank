package com.home.model;

import com.home.model.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountDAO {
    private AccountRepository accountRepository;

    public AccountDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        accountRepository.findAll();
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public Account findAccountByLogin(String login) {
        return accountRepository.findByLogin(login).orElse(null);
    }
}
