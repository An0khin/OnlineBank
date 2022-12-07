package com.home.model.card;

import com.home.model.Account;
import javax.persistence.*;

@Entity
@Table(name = "debitCards")
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
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
}
