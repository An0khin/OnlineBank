package com.home.model.repository;

import com.home.model.card.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardsRepository extends JpaRepository<CreditCard, Long> {
}
