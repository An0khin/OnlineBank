package com.home.model;

import com.home.model.card.DebitCard;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "debitTransactions")
public class DebitTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    private DebitCard from;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private DebitCard to;

    @Column(nullable = false)
    private Double money;

    public DebitTransaction() {
    }

    public DebitTransaction(DebitCard from, DebitCard to, Double money) {
        this.from = from;
        this.to = to;
        this.money = money;
        this.date = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DebitCard getFrom() {
        return from;
    }

    public void setFrom(DebitCard from) {
        this.from = from;
    }

    public DebitCard getTo() {
        return to;
    }

    public void setTo(DebitCard to) {
        this.to = to;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
