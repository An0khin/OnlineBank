package com.home.service;

import com.home.model.CreditRequest;
import com.home.model.DebitTransaction;
import com.home.model.card.Card;
import com.home.model.card.CreditCard;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;
import com.home.dao.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDAO {
    private final DebitCardRepository debitCardRepository;
    private final SavingRepository savingRepository;
    private final CreditRequestRepository creditRequestRepository;
    private final CreditCardRepository creditCardRepository;
    private final DebitTransactionRepository debitTransactionRepository;

    public CardDAO(DebitCardRepository debitCardRepository,
                   SavingRepository savingRepository,
                   CreditRequestRepository creditRequestRepository,
                   CreditCardRepository creditCardRepository,
                   DebitTransactionRepository debitTransactionRepository) {
        this.debitCardRepository = debitCardRepository;
        this.savingRepository = savingRepository;
        this.creditRequestRepository = creditRequestRepository;
        this.creditCardRepository = creditCardRepository;
        this.debitTransactionRepository = debitTransactionRepository;
    }

    //Debit cards
    public void saveDebitCard(DebitCard card) {
        debitCardRepository.save(card);
    }

    public List<DebitCard> findAllDebitCardsByAccountId(Integer id) {
        return debitCardRepository.findAllByAccountId(id);
    }

    public DebitCard findDebitCardById(Integer id) {
        return debitCardRepository.findById(id).orElse(null);
    }


    //Saving
    public void saveSaving(Saving saving) {
        savingRepository.save(saving);
    }

    public List<Saving> findAllSavings() {
        return savingRepository.findAll();
    }

    public List<Saving> findAllSavingsByAccountId(Integer id) {
        return savingRepository.findAllByAccountId(id);
    }

    public Saving findSavingById(Integer id) {
        return savingRepository.findById(id).orElse(null);
    }


    //Credit request
    public void saveCreditRequest(CreditRequest request) {
        creditRequestRepository.save(request);
    }

    public List<CreditRequest> findNotViewedCreditRequests() {
        return creditRequestRepository.findByViewedFalse();
    }

    public List<CreditRequest> findAcceptedCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findByViewedTrueAndAcceptedTrueAndBorrower_Id(id);
    }

    public List<CreditRequest> findDeclinedCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findByViewedTrueAndAcceptedFalseAndBorrower_Id(id);
    }

    public List<CreditRequest> findNotViewedCreditRequestsByAccountId(Integer id) {
        return creditRequestRepository.findByViewedFalseAndBorrower_Id(id);
    }

    public CreditRequest findCreditRequestById(Integer id) {
        return creditRequestRepository.findById(id).orElse(null);
    }

    public void updateCreditRequest(Integer id, CreditRequest creditRequest) {
        creditRequestRepository.updateDesiredLimitAndPercentAndCreditorAndAcceptedAndViewedById(
                creditRequest.getDesiredLimit(),
                creditRequest.getPercent(),
                creditRequest.getCreditor(),
                creditRequest.isAccepted(),
                id);
    }


    //Credit card
    public void saveCreditCard(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    public List<CreditCard> findAllCreditCardsByAccountId(Integer id) {
        return creditCardRepository.findByAccount_Id(id);
    }

    public CreditCard findCreditCardById(Integer id) {
        return creditCardRepository.findById(id).orElse(null);
    }


    //Debit transactions
    public List<DebitTransaction> findAllDebitTransactionsByFromCardAccountId(Integer id) {
        return debitTransactionRepository.findByFrom_Account_Id(id);
    }

    public List<DebitTransaction> findAllDebitTransactionsByToCardAccountId(Integer id) {
        return debitTransactionRepository.findByTo_Account_Id(id);
    }


    public boolean transferMoneyFromTo(Card from, Card to, Double money) {
        if (from.transferTo(to, money)) {
            if (from.getClass() == DebitCard.class)  //Defining repository to save
                debitCardRepository.save((DebitCard) from);
            else if (from.getClass() == Saving.class)
                savingRepository.save((Saving) from);
            else if (from.getClass() == CreditCard.class)
                creditCardRepository.save((CreditCard) from);


            if (to.getClass() == DebitCard.class)
                debitCardRepository.save((DebitCard) to);
            else if (to.getClass() == Saving.class)
                savingRepository.save((Saving) to);
            else if (to.getClass() == CreditCard.class)
                creditCardRepository.save((CreditCard) to);

            return true;
        }
        return false;
    }
}
