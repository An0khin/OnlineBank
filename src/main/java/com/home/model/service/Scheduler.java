package com.home.model.service;

import com.home.model.CreditLoan;
import com.home.model.card.CreditCard;
import com.home.model.card.Saving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Scheduler {
    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24) //every day
    public void accrueMoneyToSaving() {
        List<Saving> savings = cardDAO.findAllSavings();
        savings.stream().filter(saving -> LocalDate.now().getDayOfMonth() == saving.getDate().toLocalDate().getDayOfMonth())
                .forEach(saving -> {
                    saving.accrueMoney(saving.getMoney() * (saving.getPercent() / 100));
                    cardDAO.saveSaving(saving);
                });
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24) //every day
    public void percentToCreditCard() {
        List<CreditLoan> creditLoans = transactionDAO.findAllNotClosedCreditLoans();

        creditLoans.stream().filter(loan -> LocalDate.now().getDayOfMonth() == loan.getDate().toLocalDate().getDayOfMonth())
                .map(loan -> loan.getCreditCard())
                .forEach(card -> {
                    card.setReturnMoney(card.getReturnMoney() + (card.getReturnMoney() * (card.getPercent() / 100)));
                    cardDAO.saveCreditCard(card);
                });
    }
}
