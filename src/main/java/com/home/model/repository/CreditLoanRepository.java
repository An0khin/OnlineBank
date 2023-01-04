package com.home.model.repository;


import com.home.model.CreditLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CreditLoanRepository extends JpaRepository<CreditLoan, Integer> {
}
