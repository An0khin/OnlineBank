package com.home.dao;

import com.home.model.card.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    List<Saving> findAllByAccountId(Integer id);
}
