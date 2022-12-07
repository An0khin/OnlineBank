package com.home.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    AccountDAO accountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountDAO.findAccountByLogin(username);

        System.out.println(account);
        account.getDebitCards().forEach(System.out::println);

        if(account != null) {
            return new AccountDetails(account, passwordEncoder);
        } else {
            throw new UsernameNotFoundException("Account with this username not found");
        }
    }
}
