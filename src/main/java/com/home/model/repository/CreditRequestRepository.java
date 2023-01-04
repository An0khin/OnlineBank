package com.home.model.repository;

import com.home.model.Account;
import com.home.model.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Integer> {
    List<CreditRequest> findAllByBorrowerId(Integer id);

    List<CreditRequest> findByViewedFalse();

    @Modifying
    @Query("""
            update CreditRequest c set c.desiredLimit = ?1, c.percent = ?2, c.creditor = ?3, c.accepted = ?4, c.viewed = TRUE
            where c.id = ?5""")
    int updateDesiredLimitAndPercentAndCreditorAndAcceptedAndViewedById(Double desiredLimit, Double percent, Account creditor, Boolean accepted, Integer id);

    List<CreditRequest> findByViewedTrueAndAcceptedTrueByBorrowerId(Integer id);

    List<CreditRequest> findByViewedTrueAndAcceptedFalseByBorrowerId(Integer id);

    List<CreditRequest> findByViewedFalseByBorrowerId(Integer id);

}
