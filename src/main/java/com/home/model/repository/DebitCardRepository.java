package com.home.model.repository;

import com.home.model.card.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DebitCardRepository extends JpaRepository<DebitCard, Integer> {
    List<DebitCard> findAllByAccountId(Integer id);
}
