package com.home.model.service;

import com.home.model.CreditLoan;
import com.home.model.DebitTransaction;
import com.home.model.card.CreditCard;
import com.home.model.repository.CreditLoanRepository;
import com.home.model.repository.DebitTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionDAO {
    @Autowired
    private CreditLoanRepository creditLoanRepository;

    @Autowired
    private DebitTransactionRepository debitTransactionRepository;

    public void saveCreditLoan(CreditLoan creditLoan) {
        creditLoanRepository.save(creditLoan);
    }

    public void setClosedToCreditLoanByCreditCard(CreditCard creditCard) {
        creditLoanRepository.updateWasClosedAndIsClosedByCreditCardAndIsClosedFalse(Date.valueOf(LocalDate.now()), true, creditCard);
    }

    public List<CreditLoan> findAllNotClosedCreditLoans() {
        return creditLoanRepository.findByIsClosedFalse();
    }

    public int findAllNotClosedCreditLoansByCreditCardId(Integer id) {
        return (int) creditLoanRepository.countDistinctByIsClosedFalseAndCreditCard_Id(id);
    }

    public void saveDebitTransaction(DebitTransaction debitTransaction) {
        debitTransactionRepository.save(debitTransaction);
    }

}
