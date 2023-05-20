package com.home.dao;

import com.home.model.card.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findByAccount_Id(Integer id);
}
