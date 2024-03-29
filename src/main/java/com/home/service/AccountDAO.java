package com.home.service;

import com.home.model.Account;
import com.home.dao.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAO {
    private final AccountRepository accountRepository;

    public AccountDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        accountRepository.findAll();
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account findAccountByLogin(String login) {
        return accountRepository.findByLogin(login).orElse(null);
    }

    public void update(Integer id, Account account) {
        accountRepository.updateLoginAndPasswordAndRoleById(
                account.getLogin(),
                account.getPassword().equals("") ? findById(id).getPassword() : account.getPassword(),
                id);
    }

    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }
}
