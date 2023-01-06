package com.home.model.repository;


import com.home.model.CreditLoan;
import com.home.model.card.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public interface CreditLoanRepository extends JpaRepository<CreditLoan, Integer> {
    List<CreditLoan> findByIsClosedFalse();

    @Modifying
    @Query("update CreditLoan c set c.wasClosed = ?1, c.isClosed = ?2 where c.creditCard = ?3 and c.isClosed = false")
    int updateWasClosedAndIsClosedByCreditCardAndIsClosedFalse(Date wasClosed, Boolean isClosed, CreditCard creditCard);

    long countDistinctByIsClosedFalseAndCreditCard_Id(Integer id);

}
