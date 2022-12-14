package com.home.controller;

import com.home.model.Account;
import com.home.model.AccountDAO;
import com.home.model.CardDAO;
import com.home.model.PassportDAO;
import com.home.model.card.DebitCard;
import com.home.model.primitive.Flag;
import com.home.model.primitive.Number;
import com.home.model.primitive.Text;
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
public class CardController {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private PassportDAO passportDAO;
    @Autowired
    private CardDAO cardDAO;

    @GetMapping("/transfer")
    public String transferDebitTo(Model model, HttpServletRequest request) {
        Account account =  accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        cardDAO.findAllDebitCardsByAccountId(account.getId()).forEach(System.out::println);
        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));

        model.addAttribute("ids", new Text());
        model.addAttribute("money", new Number());

        System.out.println("It's time");
        model.asMap().forEach((str, obj) -> System.out.println(str + " --- " + obj));

        return "debitCards/transfer";
    }

    @PostMapping("/transfer")
    public String transferDebitToPost(@ModelAttribute("ids") Text ids,
                                      @ModelAttribute("money") Number number) {

        String[] strings = ids.getText().split(",");

        DebitCard from = cardDAO.findById(Integer.valueOf(strings[0]));
        DebitCard to = cardDAO.findById(Integer.valueOf(strings[1]));

        try {
            cardDAO.transferMoneyFromTo(from, to, number.getNumber().doubleValue());
        } catch(Exception e) {
            e.printStackTrace();
        }


        return "redirect:/";
    }

    @GetMapping("/orderNewDC")
    public String createNewDebitCardPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "debitCards/createNew";
    }

    @PostMapping("/orderNewDC")
    public String createNewDebitCard(@ModelAttribute("agree") Flag flag, BindingResult result,
                                     HttpServletRequest request) {
        if(flag.getFlag()) {
            Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

            cardDAO.saveDebitCard(new DebitCard(account));

            return "redirect:/";
        } else {
            result.addError(new FieldError("agree", "flag", "You haven't read the agreement"));
            return "debitCards/createNew";
        }
    }
}
