//package com.home.model.tables;
//
//import com.home.model.Account;
//import com.home.model.card.CreditCard;
//import com.home.model.card.DebitCard;
//import com.home.model.card.Saving;
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//
//@Entity
//@Table(name = "Cards")
//public class Cards {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    @OneToOne
//    private Account account;
//
//    @OneToMany(mappedBy = "cards")
//    private ArrayList<DebitCard> debitCards;
//    @OneToMany(mappedBy = "cards")
//    private ArrayList<CreditCard> creditCards;
//    @OneToMany(mappedBy = "cards")
//    private ArrayList<Saving> savings;
//}
