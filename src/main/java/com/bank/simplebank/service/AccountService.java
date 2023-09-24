package com.bank.simplebank.service;

import com.bank.simplebank.entity.Account;
import com.bank.simplebank.exception.AccountNotFoundException;
import com.bank.simplebank.exception.InvalidPinException;
import com.bank.simplebank.model.AccountResponse;
import com.bank.simplebank.model.CreateAccountRequest;
import com.bank.simplebank.model.CreateAccountResponse;
import com.bank.simplebank.model.ReplenishAccountBalanceRequest;
import com.bank.simplebank.model.TransferMoneyRequest;
import com.bank.simplebank.model.WithdrawAccountBalanceRequest;
import com.bank.simplebank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public CreateAccountResponse createAccount(CreateAccountRequest account) {
        var newAccount = accountRepository.save(
                new Account(
                      account.name(),
                      account.pin(),
                      BigDecimal.ZERO
                )
        );

        return new CreateAccountResponse(newAccount.getId());
    }

    @Transactional
    public void depositAccountBalance(
            ReplenishAccountBalanceRequest request
    ) {
        var account = accountRepository.findById(request.accountNumber())
                .orElseThrow(() -> new AccountNotFoundException(
                        request.accountNumber().toString()
                ));

        var newBalance = account.getBalance().add(request.amount());

        accountRepository.changeBalance(request.accountNumber(), newBalance);
    }

    @Transactional
    public void withdrawAccountBalance(
            WithdrawAccountBalanceRequest request
    ) {
        var account = accountRepository.findById(request.accountNumber())
                .orElseThrow(() -> new AccountNotFoundException(
                        request.accountNumber().toString()
                ));

        if (!request.pin().equals(account.getPin())) {
            throw new InvalidPinException();
        }

        var newBalance = account.getBalance().subtract(request.amount());

        accountRepository.changeBalance(request.accountNumber(), newBalance);
    }

    @Transactional
    public void transferMoney(
            TransferMoneyRequest request
    ) {
        Account sender = accountRepository
                .findById(request.senderAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(
                        request.senderAccountNumber().toString()
                ));
        Account receiver = accountRepository
                .findById(request.receiverNumberAccount())
                .orElseThrow(() -> new AccountNotFoundException(
                        request.senderAccountNumber().toString()
                ));

        BigDecimal senderNewAmount =
                sender.getBalance().subtract(request.amount());
        BigDecimal receiverNewAmount =
                receiver.getBalance().add(request.amount());

        accountRepository
                .changeBalance(request.senderAccountNumber(), senderNewAmount);
        accountRepository
                .changeBalance(request.receiverNumberAccount(), receiverNewAmount);
    }

    public List<AccountResponse> getAllAccounts() {
        var accounts = accountRepository.findAll();

        return StreamSupport.stream(accounts.spliterator(), false)
                .map(account -> new AccountResponse(account.getName(), account.getBalance()))
                .toList();
    }

    public AccountResponse getAccount(Long accountNumber) {
        var account = accountRepository
                .findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        accountNumber.toString()
                ));

        return new AccountResponse(account.getName(), account.getBalance());
    }

}
