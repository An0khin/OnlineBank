package com.home.web;

import com.home.model.Account;
import com.home.model.CreditRequest;
import com.home.model.Passport;
import com.home.model.card.CreditCard;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;
import com.home.model.primitive.Flag;
import com.home.model.primitive.Text;
import com.home.service.AccountDAO;
import com.home.service.CardDAO;
import com.home.service.PassportDAO;
import com.home.service.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    PassportDAO passportDAO;
    @Autowired
    CardDAO cardDAO;
    @Autowired
    TransactionDAO transactionDAO;
    private Map<Integer, Account> accountMap = new HashMap<>();


//Create new cards
    @GetMapping("/order_new_credit")
    public String orderNewCCPage(Model model) {

        model.addAttribute("limit_percent", new Text());

        return "admin/newCreditCardForUser";
    }

    @PostMapping("/order_new_credit")
    public String orderNewCC(@ModelAttribute("limit_percent") Text limitPercent,
                             HttpServletRequest request) {

        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Account user = accountMap.get(account.getId());

        String[] strings = limitPercent.getText().split(",");

        Double limit = Double.parseDouble(strings[0]);
        Double percent = Double.parseDouble(strings[1]);

        CreditCard creditCard = new CreditCard(user);
        creditCard.setMoneyLimit(limit);
        creditCard.setPercent(percent);

        cardDAO.saveCreditCard(creditCard);

        return "redirect:/";
    }

    @GetMapping("/order_new_debit")
    public String newDCPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "debitCards/createNew";
    }

    @PostMapping("/order_new_debit")
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

    @GetMapping("/order_new_saving")
    public String newSPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "debitCards/createNew";
    }

    @PostMapping("/order_new_saving")
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


//Create new credit request
    @GetMapping("/credit_request")
    public String creditRequestPage(@RequestParam(name = "requestId") Integer requestId,
                                Model model) {

        CreditRequest creditRequest = cardDAO.findCreditRequestById(requestId);

        model.addAttribute("request", creditRequest);
        model.addAttribute("loans", transactionDAO.findAllCreditLoansByAccountId(creditRequest.getBorrower().getId()));

        return "admin/creditRequest";
    }

    @PostMapping(value = "/credit_request", params = "accept")
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

    @PostMapping(value = "/credit_request", params = "decline")
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


//Read the data
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

            model.addAttribute("account", accountMap.get(id));
            return "admin/facilities";
        }
    }

    @GetMapping("/all_credit_requests")
    public String allCreditRequests(Model model) {

        model.addAttribute("allRequests", cardDAO.findNotViewedCreditRequests());

        return "admin/usersCreditRequests";
    }

    @GetMapping("/all_cards")
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

    @GetMapping("/all_user_credit_requests")
    public String allUserCreditRequests(Model model,
                                        HttpServletRequest request) {

        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Integer id = accountMap.get(account.getId()).getId(); //User's id

        model.addAttribute("accepted", cardDAO.findAcceptedCreditRequestsByAccountId(id));
        model.addAttribute("declined", cardDAO.findDeclinedCreditRequestsByAccountId(id));
        model.addAttribute("notViewed", cardDAO.findNotViewedCreditRequestsByAccountId(id));

        return "admin/allUserCreditRequests";
    }

    @GetMapping("/debit_history")
    public String debitTransactionsPage(Model model,
                                        HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Integer id = accountMap.get(account.getId()).getId();

        model.addAttribute("admission", cardDAO.findAllDebitTransactionsByToCardAccountId(id));
        model.addAttribute("deduction", cardDAO.findAllDebitTransactionsByFromCardAccountId(id));

        return "debitCards/debitTransactions";
    }


//Update the data
    @GetMapping("/change")
    public String changePage(Model model,
                             HttpServletRequest request) {
        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();
        model.addAttribute("account", accountMap.get(id));
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

//Delete account
    @GetMapping("/delete")
    public String deletePage(Model model) {
        model.addAttribute("agree", new Flag());

        return "admin/deleteUser";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("agree") Flag agree,
                         BindingResult result,
                         HttpServletRequest request) {

        if(agree.getFlag() == false) {
            result.addError(new FieldError("agree", "flag", "You need to be sure"));

            return "delete";
        }

        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        Integer id = accountMap.get(account.getId()).getId();

        accountDAO.deleteById(id);

        return "redirect:/";
    }
}
