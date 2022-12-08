package com.home.model.card;

import com.home.model.Account;

import javax.persistence.*;

@Entity
@Table(name = "savings")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double money;

    @Column
    private Double percent;

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
}
