package com.home.model.repository;


import com.home.model.CreditLoan;
import com.home.model.card.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CreditLoanRepository extends JpaRepository<CreditLoan, Integer> {
    List<CreditLoan> findByIsClosedFalse();
    @Modifying
    @Query("update CreditLoan c set c.isClosed = ?1 where c.creditCard = ?2")
    int updateIsClosedByCreditCard(Boolean isClosed, CreditCard creditCard);

}
