package com.home.model.repository;

import com.home.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Transactional
    @Modifying
    @Query("update accounts a set a.login = ?1, a.password = ?2, a.role = ?3 where a.id = ?4")
    int updateLoginAndPasswordAndRoleById(String login, String password, String role, Integer id);

    Optional<Account> findByLogin(String login);
}
