package com.home.model.card;

public class Card {
    private Double money;

    public void transferTo(Card other, Double otherMoney) {
        if(takeMoney(otherMoney)) {
            accrueMoney(otherMoney);
        }
    }

    public void accrueMoney(Double money) {
        this.money += money;
    }

    public boolean takeMoney(Double takingMoney) {
        if(takingMoney <= money) {
            money -= takingMoney;
            return true;
        }
        return false;
    }
}
