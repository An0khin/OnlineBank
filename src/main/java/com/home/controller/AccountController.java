package com.home.controller;

import com.home.model.*;
import com.home.model.card.DebitCard;
import com.home.model.repository.AccountRepository;
import com.home.model.service.AccountDAO;
import com.home.model.service.CardDAO;
import com.home.model.service.PassportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private PassportDAO passportDAO;
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newAccount", new Account());
        model.addAttribute("newPassport", new Passport());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewAccount(@ModelAttribute("newPassport") Passport passport,
                                     @ModelAttribute("newAccount") Account account,
                                     BindingResult result) {
        if (accountDAO.findAccountByLogin(account.getLogin()) == null) {
            passport.setAccount(account);
            accountDAO.save(account);
            passportDAO.save(passport);
            cardDAO.saveDebitCard(new DebitCard(account));
            return "redirect:/";
        } else {
            result.addError(new FieldError("newAccount", "login", "Account with this login exists"));
            return "register";
        }
    }

    @GetMapping("/update")
    public String updatePage(Model model, HttpServletRequest request) {
        String login = request.getUserPrincipal().getName();

        Account account = accountDAO.findAccountByLogin(login);
        model.addAttribute("account", account);
        model.addAttribute("login", login);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("account") Account account, HttpServletRequest request) {
        String login = request.getUserPrincipal().getName();

        Account beforeAccount = accountDAO.findAccountByLogin(login);

        accountDAO.update(beforeAccount.getId(), account);
        return "redirect:/";
    }
}