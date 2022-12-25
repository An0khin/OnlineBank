package com.home.controller;

import com.home.model.Account;
import com.home.model.Passport;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;
import com.home.model.primitive.Flag;
import com.home.model.primitive.Text;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    PassportDAO passportDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    CardDAO cardDAO;

    Map<Integer, Account> accountMap = new HashMap<>();


    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("name_surname", new Text());

        return "admin/search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("name_surname") Text name_surname,
                         BindingResult result,
                         Model model,
                         HttpServletRequest request) {
        String[] ns = name_surname.getText().split(",");

        Passport passport = passportDAO.findByNameAndSurname(ns[0], ns[1]);

        if(passport == null) {
            result.addError(new FieldError("name_surname", "text", "Account with this name and surname doesn't exist"));
            return "admin/search";
        } else {
            Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();
            accountMap.put(id, passport.getAccount());
//            System.out.println(accountMap.get(id));

            model.addAttribute("account", accountMap.get(id));
            return "admin/facilities";
        }
    }

    @GetMapping("/change")
    public String changePage(Model model,
                             HttpServletRequest request) {
        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();
        model.addAttribute("account", accountMap.get(id));
//        System.out.println(accountMap.get(id));
        return "update";
    }

    @PostMapping("/change")
    public String change(@ModelAttribute("account") Account account,
                         HttpServletRequest request) {
        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();
        accountDAO.update(accountMap.get(id).getId(), account);
        passportDAO.update(accountMap.get(id).getPassport().getId(), account.getPassport());

        return "redirect:/";
    }

    @GetMapping("/orderNewDC")
    public String newDCPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "debitCards/createNew";
    }

    @PostMapping("/orderNewDC")
    public String newDC(@ModelAttribute("agree") Flag flag,
                        BindingResult result,
                        HttpServletRequest request) {
        if(flag.getFlag()) {
            Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();

            cardDAO.saveDebitCard(new DebitCard(accountMap.get(id)));

            return "redirect:/";
        } else {
            result.addError(new FieldError("agree", "flag", "You haven't read the agreement"));
            return "debitCards/createNew";
        }
    }

    @GetMapping("/orderNewS")
    public String newSPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "debitCards/createNew";
    }

    @PostMapping("/orderNewS")
    public String newS(@ModelAttribute("agree") Flag flag,
                        BindingResult result,
                        HttpServletRequest request) {
        if(flag.getFlag()) {
            Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();

            cardDAO.saveSaving(new Saving(accountMap.get(id)));

            return "redirect:/";
        } else {
            result.addError(new FieldError("agree", "flag", "You haven't read the agreement"));
            return "debitCards/createNew";
        }
    }
}
