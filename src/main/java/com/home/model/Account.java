package com.home.model;

//import com.home.model.card.DebitCard;
//import com.home.model.tables.Cards;
import javax.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column
    private String login;
    @Column
    private String password;
//    @Column
//    private String name;
//    @Column
//    private String surname;
//    @Column
//    private int phoneNumber;

//    @OneToOne(mappedBy = "account")
//    private Cards cards;


    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account() {}

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Id=" + Id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
