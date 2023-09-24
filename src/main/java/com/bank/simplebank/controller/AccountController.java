package com.bank.simplebank.controller;

import com.bank.simplebank.model.AccountResponse;
import com.bank.simplebank.model.CreateAccountRequest;
import com.bank.simplebank.model.CreateAccountResponse;
import com.bank.simplebank.model.ReplenishAccountBalanceRequest;
import com.bank.simplebank.model.TransferMoneyRequest;
import com.bank.simplebank.model.WithdrawAccountBalanceRequest;
import com.bank.simplebank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/account", produces = "application/json")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse getAccount(@PathVariable Long accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @PostMapping("/create")
    public CreateAccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest account
    ) {
        return accountService.createAccount(account);
    }

    @PatchMapping("/balance/replenish")
    @ResponseStatus(HttpStatus.OK)
    public void replenishAccountBalance(
            @RequestBody ReplenishAccountBalanceRequest request
    ) {
        accountService.depositAccountBalance(request);
    }

    @PatchMapping("/balance/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public void replenishAccountBalance(
            @Valid @RequestBody WithdrawAccountBalanceRequest request
    ) {
        accountService.withdrawAccountBalance(request);
    }

    @PatchMapping("/balance/transfer")
    @ResponseStatus(HttpStatus.OK)
    public void transferMoneyBetweenAccounts(
            @RequestBody TransferMoneyRequest request
    ) {
        accountService.transferMoney(request);
    }

}
