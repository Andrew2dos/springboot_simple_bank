package com.andrew2dos.simplebank.controller;

import com.andrew2dos.simplebank.entity.Balance;
import com.andrew2dos.simplebank.exception.AccountNotFoundException;
import com.andrew2dos.simplebank.model.TransferRequest;
import com.andrew2dos.simplebank.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j // Lombok сам поднимает логгер и подключает экземпляр логгера
//@RestController("/balance")
@Controller
//@AllArgsConstructor используется вместо @Autowired и создает сам конструктор
//@RequiredArgsConstructor
public class BalanceController {

    private static final String ATTRIBUTE_ACCOUNT = "account";
    private static final String PAGE_ACCOUNT_LIST = "/accountList";
    private static final String REDIRECT = "redirect:";

    private final BankService bankService;
    private final String welcomeMessage;

    public BalanceController(BankService bankService, @Value("${welcome.message}") String welcomeMessage) {
        this.bankService = bankService;
        this.welcomeMessage = welcomeMessage;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", welcomeMessage);
        return "index";
    }

    // Show account list
    @RequestMapping(value = {PAGE_ACCOUNT_LIST}, method = RequestMethod.GET)
    public String accountList(Model model) {
        List<Balance> allAccounts = bankService.getAllAccounts();
        model.addAttribute("accounts", allAccounts);

        return "accountList";
    }

    // Show AddAccount Page
    @RequestMapping(value = {"/addAccount"}, method = RequestMethod.GET)
    public String showAddAccountPage(Model model) {
        Balance account = new Balance();
        model.addAttribute(ATTRIBUTE_ACCOUNT, account);

        return "addAccount";
    }

    // Save new Account
    @RequestMapping(value = {"/addAccount"}, method = RequestMethod.POST)
    public String saveAccount(@Valid @ModelAttribute("account") Balance account, BindingResult result) {
        if (result.hasErrors()) {
            return "/addAccount";
        }
        bankService.createAccount(account);
        return REDIRECT + PAGE_ACCOUNT_LIST;
    }

    @RequestMapping(value = {"/deposit"}, method = RequestMethod.GET)
    public String showAddDepositPage(Model model) {

        Balance account = new Balance();
        model.addAttribute("deposit", account);

        return "addDeposit";
    }

    @RequestMapping(value = {"/addDeposit"}, method = RequestMethod.POST)
    public String addDeposit(@Valid @ModelAttribute("deposit") Balance account, BindingResult result) {
        if (result.hasErrors()) {
            return "/addDeposit";
        }
        bankService.addMoney(account.getId(), account.getAmount());
        return REDIRECT + PAGE_ACCOUNT_LIST;
    }

    @RequestMapping(value = {"/makeWithdrawal"}, method = RequestMethod.GET)
    public String showWithdrawalPage(Model model) {

        Balance account = new Balance();
        model.addAttribute("withdrawal", account);

        return "makeWithdrawal";
    }

    @RequestMapping(value = {"/makeWithdrawal"}, method = RequestMethod.POST)
    public String makeWithdrawal(@Valid @ModelAttribute("withdrawal") Balance account, BindingResult result) {
        if (result.hasErrors()) {
            return "/makeWithdrawal";
        }
        bankService.resumeMoney(account.getId(), account.getAmount());
        return REDIRECT + PAGE_ACCOUNT_LIST;
    }

    @RequestMapping(value = {"/transfer"}, method = RequestMethod.GET)
    public String showTransferPage(Model model) {

        TransferRequest request = new TransferRequest();
        model.addAttribute("request", request);

        return "transfer";
    }

    // Перевести деньги
    @RequestMapping(value = {"/transfer"}, method = RequestMethod.POST)
    public String transfer(@Valid @ModelAttribute("request") TransferRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "/transfer";
        }
        bankService.makeTransfer(request);
        return REDIRECT + PAGE_ACCOUNT_LIST;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
    public String delete(@Valid @ModelAttribute("accountId") Long id) {
        bankService.deleteById(id);
        return REDIRECT + PAGE_ACCOUNT_LIST;
    }


    @ExceptionHandler(Exception.class)
    public String handle(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", "ERROR. Something goes wrong. " + e.getMessage());
        return "error";
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public String handle(AccountNotFoundException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("errorMessage", "Account not found");
        return "error";
    }
}
