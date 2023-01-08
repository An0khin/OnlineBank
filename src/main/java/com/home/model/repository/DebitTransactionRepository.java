package com.home.model.repository;

import com.home.model.DebitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DebitTransactionRepository extends JpaRepository<DebitTransaction, Integer> {
    List<DebitTransaction> findByFrom_Account_Id(Integer id);

    List<DebitTransaction> findByTo_Account_Id(Integer id);
}
