package com.home.model.repository;

import com.home.model.card.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CreditCardsRepository extends JpaRepository<CreditCard, Long> {
}
