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
    List<CreditLoan> findByClosedFalse();

    List<CreditLoan> findByCreditCard_Account_Id(Integer id);

    long countDistinctByClosedFalseAndCreditCard_Id(Integer id);

    @Modifying
    @Query("update CreditLoan c set c.wasClosed = ?1, c.closed = ?2 where c.creditCard = ?3 and c.closed = false")
    int updateWasClosedAndClosedByCreditCardAndClosedFalse(Date wasClosed, Boolean closed, CreditCard creditCard);
}
