package com.home.controller;

import com.home.model.*;
import com.home.model.card.DebitCard;
import com.home.model.primitive.Flag;
import com.home.model.primitive.Text;
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
import javax.validation.Valid;

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
    public String registerNewAccount(@Valid @ModelAttribute("newPassport") Passport passport,
                                     @Valid @ModelAttribute("newAccount") Account account,
                                     BindingResult result) {
        if(result.hasErrors()) {
            return "register";
        }

        if (accountDAO.findAccountByLogin(account.getLogin()) != null) {
            result.addError(new FieldError("newAccount", "login", "Account with this login exists"));
            return "register";
        }

        passport.setAccount(account);
        accountDAO.save(account);
        passportDAO.save(passport);
        cardDAO.saveDebitCard(new DebitCard(account));
        return "redirect:/";
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
    public String update(@Valid @ModelAttribute("account") Account account,
                         BindingResult result,
                         HttpServletRequest request) {
        if(result.hasErrors()) {
            return "update";
        }

        String login = request.getUserPrincipal().getName();

        Account beforeAccount = accountDAO.findAccountByLogin(login);

        Passport beforePassport = beforeAccount.getPassport();
        passportDAO.update(beforePassport.getId(), account.getPassport());

        accountDAO.update(beforeAccount.getId(), account);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deletePage(Model model) {
        model.addAttribute("agree", new Flag());
        model.addAttribute("password", new Text());

        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("agree") Flag agree,
                         BindingResult resultAgree,
                         @ModelAttribute("password") Text password,
                         BindingResult resultPassword,
                         HttpServletRequest request) {
        if(agree.getFlag() == false) {
            resultAgree.addError(new FieldError("agree", "flag", "You need to be sure"));

            return "delete";
        } else if (!password.getText().equals(accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getPassword())) {
            resultPassword.addError(new FieldError("password", "text", "Password is incorrect"));
            System.out.println(accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getPassword());

            return "delete";
        } else {
            accountDAO.deleteById(accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId());

            return "redirect:/logout";
        }
    }
}