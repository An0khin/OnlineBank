package com.home.model.repository;

import com.home.model.card.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    List<Saving> findAllByAccountId(Integer id);
}
