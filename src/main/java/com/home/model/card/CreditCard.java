package com.home.model.card;

import com.home.model.Account;
import javax.persistence.*;

@Entity
@Table(name = "creditCards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double moneyLimit;

    @Column(nullable = false)
    private Double currentMoney;

    @Column(nullable = false)
    private Double percent;

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
}
