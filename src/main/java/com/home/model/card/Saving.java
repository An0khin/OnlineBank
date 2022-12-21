package com.home.model.card;

import com.home.model.Account;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "savings")
public class Saving extends Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private Double percent;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Override
    public String toString() {
        return "Saving{" +
                "id=" + id +
                ", money=" + money +
                ", percent=" + percent +
                ", account=" + account +
                '}';
    }

    public Saving() {
    }

    public Saving(Account account) {
        this.money = 0.;
        this.percent = 3.;
        this.date = Date.valueOf(LocalDate.now());
        this.account = account;
    }

    @Override
    public void accrueMoney(Double money) {
        this.money += money;
    }

    @Override
    public boolean takeMoney(Double takingMoney) {
        if(this.money >= takingMoney) {
            this.money -= takingMoney;
            return true;
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public Double getPercent() {
        return percent;
    }
}
