package com.home.model.card;

import com.home.model.Account;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "creditCards")
public class CreditCard extends Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double moneyLimit;

    @Column(nullable = false)
    private Double returnMoney;

    @Column(nullable = false)
    private Double currentMoney;

    @Column(nullable = false)
    private Double percent;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id")
    private Account account;

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", moneyLimit=" + moneyLimit +
                ", currentMoney=" + currentMoney +
                ", percent=" + percent +
                ", account=" + account +
                '}';
    }

    @Override
    public void accrueMoney(Double money) {
        if(this.returnMoney <= money)
            this.returnMoney -= money;
        else {
            this.currentMoney += money - this.returnMoney;
            this.returnMoney = 0.;
        }
    }

    @Override
    public boolean takeMoney(Double takingMoney) {
        if(this.currentMoney >= takingMoney) {
            this.currentMoney -= takingMoney;

            return true;
        }

        return false;
    }

    public boolean takeCreditMoney(Double money) {
        if(this.moneyLimit >= money) {
            this.currentMoney += money;
            this.returnMoney += money + (money * percent / 100);

            return true;
        }

        return false;
    }
}
