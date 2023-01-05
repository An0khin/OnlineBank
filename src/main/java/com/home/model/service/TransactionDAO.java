package com.home.model.service;

import com.home.model.CreditLoan;
import com.home.model.card.CreditCard;
import com.home.model.repository.CreditLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDAO {
    @Autowired
    private CreditLoanRepository creditLoanRepository;

    public void saveCreditLoan(CreditLoan creditLoan) {
        creditLoanRepository.save(creditLoan);
    }

    public void setClosedToCreditLoanByCreditCard(CreditCard creditCard) {
        creditLoanRepository.updateIsClosedByCreditCard(true, creditCard);
    }

}
