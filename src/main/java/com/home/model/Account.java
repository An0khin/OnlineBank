package com.home.model;

import com.home.model.card.DebitCard;
import com.home.model.repository.DebitCardRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private Integer phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private List<DebitCard> debitCards;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account() {}

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<DebitCard> getDebitCards() {
        return new ArrayList<>(debitCards);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
