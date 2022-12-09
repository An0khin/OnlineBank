package com.home.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AccountDetails implements UserDetails {
    private Account account;
    private PasswordEncoder passwordEncoder; //DELETE AFTER

    public AccountDetails(final Account account, final PasswordEncoder passwordEncoder) {
        this.account = account;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> set = new HashSet<>(); //CHANGE!!!!!
        set.add(new SimpleGrantedAuthority("ROLE_" + account.getRole()));
        return set;
    }

    @Override
    public String getPassword() {
        return passwordEncoder.encode(account.getPassword()); //CHANGE!!!!!
    }

    @Override
    public String getUsername() {
        return account.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
