package com.home.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dateBirth;
    @Column(nullable = false)
    private Integer series;
    @Column(nullable = false)
    private Integer number;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateBirth=" + dateBirth +
                ", series=" + series +
                ", number=" + number +
                '}';
    }

    public Passport() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public Integer getSeries() {
        return series;
    }

    public Integer getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
