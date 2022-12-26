package com.home.model.service;

import com.home.model.Account;
import com.home.model.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAO {
    private AccountRepository accountRepository;

    public AccountDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        accountRepository.findAll();
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id).get();
    }

    public void save(Account account) {
        accountRepository.save(account);
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
}
