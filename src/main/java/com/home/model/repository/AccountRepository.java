package com.home.model.repository;

import com.home.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByLogin(String login);

    @Modifying
    @Query("update accounts a set a.login = ?1, a.password = ?2 where a.id = ?3")
    int updateLoginAndPasswordAndRoleById(String login, String password, Integer id);
}
