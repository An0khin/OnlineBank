package com.home.model.card;

import com.home.model.Account;
import com.home.model.DebitTransaction;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "debitCards")
public class DebitCard extends Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double money;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id")
    private Account account;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "from")
    private Set<DebitTransaction> debitTransactionsFrom;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "to")
    private Set<DebitTransaction> debitTransactionsTo;

    public DebitCard() {
    }

    public DebitCard(Account account) {
        this.money = 0.;
        this.date = Date.valueOf(LocalDate.now());
        this.account = account;
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "id=" + id +
                ", money=" + money +
                ", account=" + account +
                '}';
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
        this.money += money;
    }

    @Override
    public boolean takeMoney(Double takingMoney) {
        if (this.money >= takingMoney) {
            this.money -= takingMoney;
            return true;
        }
        return false;
    }
}
