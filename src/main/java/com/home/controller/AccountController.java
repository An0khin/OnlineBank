package com.home.controller;

import com.home.model.*;
import com.home.model.card.DebitCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private PassportDAO passportDAO;
    @Autowired
    private CardDAO cardDAO;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newAccount", new Account());
        model.addAttribute("newPassport", new Passport());
        return "register";
    }
    @PostMapping("/register")
    public String registerNewAccount(@ModelAttribute("newAccount") Account account,
                                     @ModelAttribute("newPassport") Passport passport) {
        passport.setAccount(account);
        accountDAO.save(account);
        passportDAO.save(passport);
        cardDAO.saveDebitCard(new DebitCard(account));
        return "redirect:/";
    }
}
