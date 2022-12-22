package com.home.controller;

import com.home.model.Account;
import com.home.model.Passport;
import com.home.model.primitive.Text;
import com.home.model.service.AccountDAO;
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

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    PassportDAO passportDAO;

    @Autowired
    AccountDAO accountDAO;


    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("name_surname", new Text());

        return "admin/search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("name_surname") Text name_surname,
                         BindingResult result,
                         Model model) {
        String[] ns = name_surname.getText().split(",");

        Passport passport = passportDAO.findByNameAndSurname(ns[0], ns[1]);

        if(passport == null) {
            result.addError(new FieldError("name_surname", "text", "Account with this name and surname doesn't exist"));
            return "admin/search";
        } else {
            Account account = passport.getAccount();
            System.out.println(account);

            model.addAttribute("account", account);
            return "admin/facilities";
        }
    }
}
