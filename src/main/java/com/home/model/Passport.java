package com.home.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    private String name;
    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    private String surname;
    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Can't be null")
    private Date dateBirth;
    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    @Size(max = 4, min = 4)
    private Integer series;
    @Column(nullable = false)
    @NotNull(message = "Can't be null")
    @Size(max = 6, min = 6)
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

    public Passport(String name, String surname, Date dateBirth, Integer series, Integer number) {
        this.name = name;
        this.surname = surname;
        this.dateBirth = dateBirth;
        this.series = series;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public Integer getSeries() {
        return series;
    }

    public Integer getNumber() {
        return number;
    }

    public Account getAccount() {
        return account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateBirth(Date dateBirth) {
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
