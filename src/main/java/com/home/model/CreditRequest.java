package com.home.model;

import javax.persistence.*;

@Entity
@Table(name = "creditRequest")
public class CreditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    @Column
    private Account borrower;

    @Column(name = "limit")
    private Double desiredLimit;

    @Column
    private Double percent;

    @JoinColumn(name = "creditor_id")
    private Account creditor;

    @Column
    private boolean accepted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getBorrower() {
        return borrower;
    }

    public void setBorrower(Account borrower) {
        this.borrower = borrower;
    }

    public Double getDesiredLimit() {
        return desiredLimit;
    }

    public void setDesiredLimit(Double desiredLimit) {
        this.desiredLimit = desiredLimit;
    }

    public Account getCreditor() {
        return creditor;
    }

    public void setCreditor(Account creditor) {
        this.creditor = creditor;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}