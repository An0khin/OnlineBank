package com.home.model.service;

import com.home.model.card.Saving;
import com.home.model.repository.SavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.List;

@Service
public class Scheduler {
    @Autowired
    private CardDAO cardDAO;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void accrueMoneyToSaving() {
        List<Saving> savings = cardDAO.findAllSavings();
        savings.stream().filter(saving -> LocalDate.now().getDayOfMonth() == saving.getDate().toLocalDate().getDayOfMonth())
                .forEach(saving -> {
                    saving.accrueMoney(saving.getMoney() * (saving.getPercent() / 100));
                    cardDAO.saveSaving(saving);
                });
    }
}
