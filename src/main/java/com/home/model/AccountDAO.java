package com.home.model;

import com.home.model.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void save(Account account) {
        accountRepository.save(account);
    }

    public Account findAccountByLogin(String login) {
        return accountRepository.findByLogin(login).orElse(null);
    }
    public void update(Integer id, Account account) {
        accountRepository.updateLoginAndPasswordAndRoleById(
                account.getLogin(),
                account.getPassword(),
                account.getRole(),
                id);
    }
}
