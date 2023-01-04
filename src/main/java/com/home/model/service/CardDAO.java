package com.home.model.service;

import com.home.model.CreditRequest;
import com.home.model.card.Card;
import com.home.model.card.CreditCard;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;
import com.home.model.repository.CreditCardsRepository;
import com.home.model.repository.CreditRequestRepository;
import com.home.model.repository.DebitCardRepository;
import com.home.model.repository.SavingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDAO {
    private final DebitCardRepository debitCardRepository;
    private final SavingRepository savingRepository;

    private final CreditRequestRepository creditRequestRepository;

    private final CreditCardsRepository creditCardsRepository;

    public CardDAO(DebitCardRepository debitCardRepository,
                   SavingRepository savingRepository,
                   CreditRequestRepository creditRequestRepository,
                   CreditCardsRepository creditCardsRepository) {
        this.debitCardRepository = debitCardRepository;
        this.savingRepository = savingRepository;
        this.creditRequestRepository = creditRequestRepository;
        this.creditCardsRepository = creditCardsRepository;
    }

    public DebitCard findById(Integer id) {
        return debitCardRepository.findById(id).orElse(null);
    }
    public Saving findSavingById(Integer id) {
        return savingRepository.findById(id).orElse(null);
    }

    public List<Saving> findAllSavings() {
        return savingRepository.findAll();
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
    public void saveSaving(Saving saving) {
        savingRepository.save(saving);
    }

    public void saveCreditRequest(CreditRequest request) {
        creditRequestRepository.save(request);
    }

    public void saveCreditCard(CreditCard creditCard) {
        creditCardsRepository.save(creditCard);
    }

    public List<CreditRequest> findAllCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findAllByBorrowerId(id);
    }

    public List<CreditRequest> findAcceptedCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findByViewedTrueAndAcceptedTrueByBorrowerId(id);
    }

    public List<CreditRequest> findDeclinedCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findByViewedTrueAndAcceptedFalseByBorrowerId(id);
    }

    public List<CreditRequest> findNotViewedCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findByViewedFalseByBorrowerId(id);
    }

    public CreditRequest findCreditRequestById(Integer id) {
        return creditRequestRepository.findById(id).orElse(null);
    }

    public List<CreditRequest> findAllCreditRequests() {
        return creditRequestRepository.findAll();
    }

    public void updateCreditRequest(Integer id, CreditRequest creditRequest) {
        creditRequestRepository.updateDesiredLimitAndPercentAndCreditorAndAcceptedAndViewedById(
                creditRequest.getDesiredLimit(),
                creditRequest.getPercent(),
                creditRequest.getCreditor(),
                creditRequest.isAccepted(),
                id);
    }

    public void transferMoneyFromTo(Card from, Card to, Double money) {
        from.transferTo(to, money);

        if(from.getClass() == DebitCard.class)  //Defining repository to save
            debitCardRepository.save((DebitCard) from);
        else if(from.getClass() == Saving.class)
            savingRepository.save((Saving) from);


        if(to.getClass() == DebitCard.class)
            debitCardRepository.save((DebitCard) to);
        else if(to.getClass() == Saving.class)
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
