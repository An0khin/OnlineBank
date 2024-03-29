package com.home.web;

import com.home.model.Account;
import com.home.model.CreditLoan;
import com.home.model.CreditRequest;
import com.home.model.DebitTransaction;
import com.home.model.card.CreditCard;
import com.home.model.card.DebitCard;
import com.home.model.card.Saving;
import com.home.model.primitive.Flag;
import com.home.model.primitive.Number;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CardController {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private PassportDAO passportDAO;
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private TransactionDAO transactionDAO;


//Create new cards
    @GetMapping("/order_new_debit")
    public String createNewDebitCardPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "debitCards/createNew";
    }

    @PostMapping("/order_new_debit")
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

    @GetMapping("/order_new_saving")
    public String createNewSavingPage(Model model) {
        model.addAttribute("agree", new Flag());

        return "savings/createNew";
    }

    @PostMapping("/order_new_saving")
    public String createNewSaving(@ModelAttribute("agree") Flag flag, BindingResult result,
                                  HttpServletRequest request) {
        if(flag.getFlag()) {
            Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

            cardDAO.saveSaving(new Saving(account));

            return "redirect:/";
        } else {
            result.addError(new FieldError("agree", "flag", "You haven't read the agreement"));
            return "savings/createNew";
        }
    }

    @GetMapping("/new_credit_request")
    public String newCreditRequestPage(Model model) {

        model.addAttribute("creditRequest", new CreditRequest());

        return "creditCards/newCreditRequest";
    }

    @PostMapping("/new_credit_request")
    public String newCreditRequest(@ModelAttribute("creditRequest") CreditRequest creditRequest,
                                   HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
        creditRequest.setBorrower(account);

        cardDAO.saveCreditRequest(creditRequest);

        return "redirect:/";
    }

    @GetMapping("/take_credit")
    public String takeCreditPage(Model model,
                                 HttpServletRequest request) {

        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("creditCards", account.getCreditCards());
        model.addAttribute("id", new Text());
        model.addAttribute("money", new Number());

        return "creditCards/takeCredit";
    }

    @PostMapping("/take_credit")
    public String takeCredit(@ModelAttribute("id") Text id,
                             @ModelAttribute("money") Number money,
                             BindingResult result) {

        CreditCard creditCard = cardDAO.findCreditCardById(Integer.valueOf(id.getText()));

        if(transactionDAO.findAllNotClosedCreditLoansByCreditCardId(creditCard.getId()) != 0) {
            return "creditCards/alreadyHasCredit";
        }

        if(!creditCard.takeCreditMoney(money.getNumber())) {
            result.addError(new FieldError("money", "number", "Must be less or equals " + creditCard.getMoneyLimit()));
            return "creditCards/takeCredit";
        }

        transactionDAO.saveCreditLoan(new CreditLoan(creditCard, money.getNumber()));
        cardDAO.saveCreditCard(creditCard);

        return "redirect:/";
    }


//Read the data
    @GetMapping("/all_credit_cards")
    public String allCreditCardsPage(Model model,
                                     HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("creditCards", cardDAO.findAllCreditCardsByAccountId(account.getId()));

        return "creditCards/allCreditCards";
    }

    @GetMapping("/credit_card")
    public String viewCreditCard(@RequestParam("id") Integer id,
                                 Model model) {
        model.addAttribute("card", cardDAO.findCreditCardById(id));

        return "creditCards/view";
    }

    @GetMapping("/debit_card")
    public String viewDebitCard(@RequestParam("id") Integer id,
                                Model model) {
        model.addAttribute("card", cardDAO.findDebitCardById(id));

        return "debitCards/view";
    }

    @GetMapping("/saving")
    public String viewSaving(@RequestParam("id") Integer id,
                             Model model) {
        model.addAttribute("card", cardDAO.findSavingById(id));

        return "savings/view";
    }

    @GetMapping("/debit_transactions")
    public String debitTransactionsPage(Model model,
                                        HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("admission", cardDAO.findAllDebitTransactionsByToCardAccountId(account.getId()));
        model.addAttribute("deduction", cardDAO.findAllDebitTransactionsByFromCardAccountId(account.getId()));

        return "debitCards/debitTransactions";
    }

    @GetMapping("/all_cards")
    public String allCardsPage(Model model,
                               HttpServletRequest request) {
        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();

        model.addAttribute("debits", cardDAO.findAllDebitCardsByAccountId(id));
        model.addAttribute("savings", cardDAO.findAllSavingsByAccountId(id));
        model.addAttribute("credits", cardDAO.findAllCreditCardsByAccountId(id));

        return "allCards";
    }

    @GetMapping("/all_credit_requests")
    public String allCreditRequests(Model model,
                                    HttpServletRequest request) {

        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();

        model.addAttribute("accepted", cardDAO.findAcceptedCreditRequestsByAccountId(id));
        model.addAttribute("declined", cardDAO.findDeclinedCreditRequestsByAccountId(id));
        model.addAttribute("notViewed", cardDAO.findNotViewedCreditRequestsByAccountId(id));

        return "creditCards/allCreditRequests";
    }


//Transfers between cards
    @GetMapping("/transfer/debit_to_debit")
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

    @PostMapping("/transfer/debit_to_debit")
    public String transferDebitToPost(@ModelAttribute("ids") Text ids,
                                      @ModelAttribute("money") Number number) {

        String[] strings = ids.getText().split(",");

        DebitCard from = cardDAO.findDebitCardById(Integer.valueOf(strings[0]));
        DebitCard to = cardDAO.findDebitCardById(Integer.valueOf(strings[1]));

        System.out.println(ids + " --- " + number);

        if(cardDAO.transferMoneyFromTo(from, to, number.getNumber())) {
            transactionDAO.saveDebitTransaction(new DebitTransaction(from, to, number.getNumber()));
        }

        return "redirect:/";
    }

    @GetMapping("/transfer/saving_to_debit")
    public String transferSavingsToDebitPage(Model model, HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("savings", cardDAO.findAllSavingsByAccountId(account.getId()));
        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));

        model.addAttribute("ids", new Text());
        model.addAttribute("money", new Number());

        return "savings/transfer";
    }

    @PostMapping("/transfer/saving_to_debit")
    public String transferSavingsToDebit(@ModelAttribute("ids") Text ids, @ModelAttribute("money") Number money) {
        String[] strings = ids.getText().split(",");

        Saving from = cardDAO.findSavingById(Integer.valueOf(strings[0]));
        DebitCard to = cardDAO.findDebitCardById(Integer.valueOf(strings[1]));

        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());

        return "redirect:/";
    }

    @GetMapping("/transfer/debit_to_saving")
    public String transferDebitToSavingsPage(Model model, HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
        model.addAttribute("savings", cardDAO.findAllSavingsByAccountId(account.getId()));

        model.addAttribute("ids", new Text());
        model.addAttribute("money", new Number());

        return "debitCards/transferToSaving";
    }

    @PostMapping("/transfer/debit_to_saving")
    public String transferDebitToSavings(@ModelAttribute("ids") Text ids, @ModelAttribute("money") Number money) {
        String[] strings = ids.getText().split(",");

        DebitCard from = cardDAO.findDebitCardById(Integer.valueOf(strings[0]));
        Saving to = cardDAO.findSavingById(Integer.valueOf(strings[1]));

        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());

        return "redirect:/";
    }

    @GetMapping("/transfer/debit_to_credit")
    public String transferDebitToCreditPage(Model model, HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
        model.addAttribute("credits", cardDAO.findAllCreditCardsByAccountId(account.getId()));

        model.addAttribute("ids", new Text());
        model.addAttribute("money", new Number());

        return "debitCards/transferToCredit";
    }

    @PostMapping("/transfer/debit_to_credit")
    public String transferDebitToCredit(@ModelAttribute("ids") Text ids, @ModelAttribute("money") Number money) {
        String[] strings = ids.getText().split(",");

        DebitCard from = cardDAO.findDebitCardById(Integer.valueOf(strings[0]));
        CreditCard to = cardDAO.findCreditCardById(Integer.valueOf(strings[1]));

        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());

        if(to.getReturnMoney() == 0) {
            transactionDAO.setClosedToCreditLoanByCreditCard(to);
        }

        return "redirect:/";
    }

    @GetMapping("/transfer/credit_to_debit")
    public String transferCreditToDebitPage(Model model, HttpServletRequest request) {
        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
        model.addAttribute("credits", cardDAO.findAllCreditCardsByAccountId(account.getId()));

        model.addAttribute("ids", new Text());
        model.addAttribute("money", new Number());

        return "creditCards/transferToDebit";
    }

    @PostMapping("/transfer/credit_to_debit")
    public String transferCreditToDebit(@ModelAttribute("ids") Text ids, @ModelAttribute("money") Number money) {
        String[] strings = ids.getText().split(",");

        DebitCard to = cardDAO.findDebitCardById(Integer.valueOf(strings[1]));
        CreditCard from = cardDAO.findCreditCardById(Integer.valueOf(strings[0]));

        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());

        return "redirect:/";
    }
}
