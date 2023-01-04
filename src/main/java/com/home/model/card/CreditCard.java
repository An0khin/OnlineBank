package com.home.model.card;

import com.home.model.Account;
import com.home.model.CreditLoan;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

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

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditCard")
    private Set<CreditLoan> creditLoans;

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

    public CreditCard() {
    }

    public CreditCard(Account account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(Double moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(Double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
