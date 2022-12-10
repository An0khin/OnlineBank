package com.home.model;

import com.home.model.card.DebitCard;
import com.home.model.repository.DebitCardRepository;
import com.home.model.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDAO {
    private DebitCardRepository debitCardRepository;

    public CardDAO(DebitCardRepository debitCardRepository, PassportRepository passportRepository) {
        this.debitCardRepository = debitCardRepository;
        debitCardRepository.findAll();
    }

    public DebitCard findById(Integer id) {
        return debitCardRepository.findById(id).orElse(null);
    }

    public List<DebitCard> findAllDebitCards() {
        return debitCardRepository.findAll();
    }

    public List<DebitCard> findAllDebitCardsByAccountId(Integer id) {
        return debitCardRepository.findAllByAccountId(id);
    }

    public void saveDebitCard(DebitCard card) {
        debitCardRepository.save(card);
    }

    public void transferMoneyFromTo(DebitCard from, DebitCard to, Double money) throws Exception {
        if(from.getMoney() >= money) {
            from.setMoney(from.getMoney() - money);
            to.setMoney(to.getMoney() + money);

            debitCardRepository.save(from);
            debitCardRepository.save(to);
        } else {
            throw new Exception("Not enough money");
        }
    }
}
