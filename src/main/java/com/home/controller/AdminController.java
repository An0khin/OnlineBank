package com.home.controller;

import com.home.model.Account;
import com.home.model.CreditRequest;
import com.home.model.Passport;
import com.home.model.card.CreditCard;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public String change(@Valid @ModelAttribute("account") Account account,
                         BindingResult result,
                         HttpServletRequest request) {
        if(result.hasErrors()) {
            return "update";
        }

        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();
        accountDAO.update(accountMap.get(id).getId(), account);
        passportDAO.update(accountMap.get(id).getPassport().getId(), account.getPassport());

        return "redirect:/";
    }

    ////////////////////////////
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

    ///////////////////////

    @GetMapping("/allCreditRequests")
    public String allCreditRequests(Model model) {

        model.addAttribute("allRequests", cardDAO.findNotViewedCreditRequests());

        return "admin/usersCreditRequests";
    }

    @GetMapping("/creditRequest")
    public String creditRequestPage(@RequestParam(name = "requestId") Integer requestId,
                                    Model model) {

        model.addAttribute("request", cardDAO.findCreditRequestById(requestId));

        return "admin/creditRequest";
    }

    @PostMapping(value = "/creditRequest", params = "accept")
    public String creditRequestAccept(@ModelAttribute("request") CreditRequest request,
                                      @RequestParam("requestId") Integer requestId,
                                      HttpServletRequest servletRequest) {

        CreditRequest creditRequest = cardDAO.findCreditRequestById(requestId);
        creditRequest.setDesiredLimit(request.getDesiredLimit());
        creditRequest.setPercent(request.getPercent());
        creditRequest.setCreditor(accountDAO.findAccountByLogin(servletRequest.getUserPrincipal().getName()));
        creditRequest.setAccepted(true);

        cardDAO.updateCreditRequest(requestId, creditRequest);

        CreditCard creditCard = new CreditCard(creditRequest.getBorrower());
        creditCard.setMoneyLimit(creditRequest.getDesiredLimit());
        creditCard.setPercent(creditRequest.getPercent());

        cardDAO.saveCreditCard(creditCard);

        return "redirect:/";
    }

    @PostMapping(value = "/creditRequest", params = "decline")
    public String creditRequestDecline(@ModelAttribute("request") CreditRequest request,
                                       @RequestParam("requestId") Integer requestId,
                                       HttpServletRequest servletRequest) {

        CreditRequest creditRequest = cardDAO.findCreditRequestById(requestId);
        creditRequest.setDesiredLimit(request.getDesiredLimit());
        creditRequest.setPercent(request.getPercent());
        creditRequest.setCreditor(accountDAO.findAccountByLogin(servletRequest.getUserPrincipal().getName()));
        creditRequest.setAccepted(false);

        cardDAO.updateCreditRequest(requestId, creditRequest);

        return "redirect:/";
    }

    @GetMapping("/allCards")
    public String allCardsPage(Model model,
                               HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Account user = accountMap.get(account.getId());

        Integer id = user.getId();

        model.addAttribute("debits", cardDAO.findAllDebitCardsByAccountId(id));
        model.addAttribute("savings", cardDAO.findAllSavingsByAccountId(id));
        model.addAttribute("credits", cardDAO.findAllCreditCardsByAccountId(id));

        return "allCards";
    }

    @GetMapping("/allUserCreditRequests")
    public String allUserCreditRequests(Model model,
                                        HttpServletRequest request) {

        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Integer id = accountMap.get(account.getId()).getId(); //User's id

        model.addAttribute("accepted", cardDAO.findAcceptedCreditRequestsByAccountId(id));
        model.addAttribute("declined", cardDAO.findDeclinedCreditRequestsByAccountId(id));
        model.addAttribute("notViewed", cardDAO.findNotViewedCreditRequestsByAccountId(id));

        return "admin/allUserCreditRequests";
    }

    @GetMapping("/debitHistory")
    public String debitTransactionsPage(Model model,
                                        HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Integer id = accountMap.get(account.getId()).getId();

        model.addAttribute("admission", cardDAO.findAllDebitTransactionsByToCardAccountId(id));
        model.addAttribute("deduction", cardDAO.findAllDebitTransactionsByFromCardAccountId(id));

        return "debitCards/debitTransactions";
    }
}
