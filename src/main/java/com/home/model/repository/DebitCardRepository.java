package com.home.model.repository;

import com.home.model.card.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
}
