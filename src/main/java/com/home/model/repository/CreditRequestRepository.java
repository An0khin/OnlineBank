package com.home.model.repository;

import com.home.model.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Integer> {
    List<CreditRequest> findAllByBorrowerId(Integer id);
}
