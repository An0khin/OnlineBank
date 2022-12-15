package com.home.model;

import com.home.model.card.Card;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;
import com.home.model.repository.DebitCardRepository;
import com.home.model.repository.PassportRepository;
import com.home.model.repository.SavingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDAO {
    private final DebitCardRepository debitCardRepository;
    private final SavingRepository savingRepository;

    public CardDAO(DebitCardRepository debitCardRepository, SavingRepository savingRepository, PassportRepository passportRepository) {
        this.debitCardRepository = debitCardRepository;
        this.savingRepository = savingRepository;
//        debitCardRepository.findAll();
    }

    public DebitCard findById(Integer id) {
        return debitCardRepository.findById(id).orElse(null);
    }
    public Saving findSavingById(Integer id) {
        return savingRepository.findById(id).orElse(null);
    }

    public List<DebitCard> findAllDebitCards() {
        return debitCardRepository.findAll();
    }

    public List<DebitCard> findAllDebitCardsByAccountId(Integer id) {
        return debitCardRepository.findAllByAccountId(id);
    }

    public List<Saving> findAllSavingsByAccountId(Integer id) {
        return savingRepository.findAllByAccountId(id);
    }

    public void saveDebitCard(DebitCard card) {
        debitCardRepository.save(card);
    }

    public void transferMoneyFromTo(Card from, Card to, Double money) {
        from.transferTo(to, money);

        if(from instanceof DebitCard)  //Defining repository to save
            debitCardRepository.save((DebitCard) from);
        else if(from instanceof Saving)
            savingRepository.save((Saving) from);


        if(to instanceof DebitCard)
            debitCardRepository.save((DebitCard) to);
        else if(to instanceof Saving)
            savingRepository.save((Saving) to);
//        if(from.getMoney() >= money) {
//            from.setMoney(from.getMoney() - money);
//            to.setMoney(to.getMoney() + money);
//
//            debitCardRepository.save(from);
//            debitCardRepository.save(to);
//        } else {
//            throw new Exception("Not enough money");
//        }
    }
}
