package com.home.model.card;

import com.home.model.Account;
import com.home.model.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "debitCards")
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double money;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id")
    private Account account;

    @Override
    public String toString() {
        return "DebitCard{" +
                "id=" + id +
                ", money=" + money +
                ", account=" + account +
                '}';
    }

    public DebitCard() {
    }

    public DebitCard(Account account) {
        this.money = Double.valueOf(0);
        this.account = account;
    }

    //    public void transferTo(Card other, Double otherMoney) {
//        if(takeMoney(otherMoney)) {
//            accrueMoney(otherMoney);
//        }
//        debitCardRepository.save(this);
//    }
//
//    public void accrueMoney(Double money) {
//        this.money += money;
//        debitCardRepository.save(this);
//    }
//
//    public boolean takeMoney(Double takingMoney) {
//        if(takingMoney <= money) {
//            money -= takingMoney;
//            debitCardRepository.save(this);
//            return true;
//        }
//        return false;
//    }
}
