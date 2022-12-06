package com.home.model;

import com.home.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDAO {
    @Autowired
    private AccountRepository accountRepository;

    public Account findAccountByLogin(String login) {
        return accountRepository.findByLogin(login).orElse(null);
    }
}
