package com.home.model;

import com.home.model.card.CreditCard;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

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
    private Set<DebitCard> debitCards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<CreditCard> creditCards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Saving> savings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borrower", fetch = FetchType.EAGER)
    private Set<CreditRequest> creditRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditor", fetch = FetchType.EAGER)
    private Set<CreditRequest> acceptedCreditRequests;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Set<DebitCard> getDebitCards() {
        return debitCards;
    }

    public void setDebitCards(Set<DebitCard> debitCards) {
        this.debitCards = debitCards;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Set<Saving> getSavings() {
        return savings;
    }

    public void setSavings(Set<Saving> savings) {
        this.savings = savings;
    }

    public Set<CreditRequest> getCreditRequests() {
        return creditRequests;
    }

    public void setCreditRequests(Set<CreditRequest> creditRequests) {
        this.creditRequests = creditRequests;
    }

    public Set<CreditRequest> getAcceptedCreditRequests() {
        return acceptedCreditRequests;
    }

    public void setAcceptedCreditRequests(Set<CreditRequest> acceptedCreditRequests) {
        this.acceptedCreditRequests = acceptedCreditRequests;
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
