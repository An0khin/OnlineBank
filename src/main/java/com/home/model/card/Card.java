package com.home.model.card;

public abstract class Card {
    public void transferTo(Card other, Double otherMoney) {
        if(takeMoney(otherMoney)) {
            other.accrueMoney(otherMoney);
        }
    }

    public abstract void accrueMoney(Double money);

    public abstract boolean takeMoney(Double takingMoney);
}
