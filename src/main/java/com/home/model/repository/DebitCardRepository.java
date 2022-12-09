package com.home.model.repository;

import com.home.model.card.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
}
