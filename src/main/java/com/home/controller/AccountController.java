package com.home.controller;

import com.home.model.Account;
import com.home.model.Passport;
import com.home.model.card.DebitCard;
import com.home.model.repository.AccountRepository;
import com.home.model.repository.DebitCardRepository;
import com.home.model.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DebitCardRepository debitCardRepository;
    @Autowired
    private PassportRepository passportRepository;

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

        accountRepository.save(account);
        passportRepository.save(passport);
        debitCardRepository.save(new DebitCard(account));
        return "redirect:/";
    }
}
