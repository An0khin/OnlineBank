package com.home.model;

import com.home.model.card.CreditCard;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name = "accounts")
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    private String login;
    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    @Size(min = 8, message = "Need to have 8 or more characters")
    private String password;
    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    private String role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Phone> phones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<DebitCard> debitCards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<CreditCard> creditCards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Saving> savings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borrower", fetch = FetchType.EAGER)
    private Set<CreditRequest> creditRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditor", fetch = FetchType.EAGER)
    private Set<CreditRequest> acceptedCreditRequests;

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

    public Passport getPassport() {
        return passport;
    }

    public Set<DebitCard> getDebitCards() {
        return new HashSet<>(debitCards);
    }

    public Set<CreditCard> getCreditCards() {
        return new HashSet<>(creditCards);
    }

    public Set<Saving> getSavings() {
        return new HashSet<>(savings);
    }

    public String getRole() {
        return role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", passport=" + passport +
                '}';
    }
}
