package com.bank.simplebank;

import com.bank.simplebank.entity.Account;
import com.bank.simplebank.exception.AccountNotFoundException;
import com.bank.simplebank.exception.InvalidPinException;
import com.bank.simplebank.model.TransferMoneyRequest;
import com.bank.simplebank.model.WithdrawAccountBalanceRequest;
import com.bank.simplebank.repository.AccountRepository;
import com.bank.simplebank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OperationsTests {

    @Test
    public void withdrawAccountBalance() {
        var repository = mock(AccountRepository.class);
        var service = new AccountService(repository);

        var account = new Account();
        account.setId(1L);
        account.setPin("1234");
        account.setBalance(BigDecimal.valueOf(500));

        var request = new WithdrawAccountBalanceRequest(
                account.getId(),
                account.getPin(),
                BigDecimal.valueOf(200)
        );

        given(repository.findById(account.getId()))
                .willReturn(Optional.of(account));

        service.withdrawAccountBalance(request);

        verify(repository)
                .changeBalance(anyLong(), any());
    }

    @Test
    public void withdrawAccountBalanceInvalidPin() {
        var repository = mock(AccountRepository.class);
        var service = new AccountService(repository);

        var account = new Account();
        account.setId(1L);
        account.setPin("1234");
        account.setBalance(BigDecimal.valueOf(500));

        var request = new WithdrawAccountBalanceRequest(
                account.getId(),
                "4321",
                BigDecimal.valueOf(200)
        );


        given(repository.findById(1L))
                .willReturn(Optional.of(account));

        assertThrows(
                InvalidPinException.class,
                () -> service.withdrawAccountBalance(request)
        );

        verify(repository, never())
                .changeBalance(anyLong(), any());
    }

    @Test
    public void withdrawAccountBalanceAccountNotFound() {
        var repository = mock(AccountRepository.class);
        var service = new AccountService(repository);

        var request = new WithdrawAccountBalanceRequest(
                1L,
                "1234",
                BigDecimal.valueOf(200)
        );

        given(repository.findById(1L))
                .willReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> service.withdrawAccountBalance(request)
        );

        verify(repository, never())
                .changeBalance(anyLong(), any());
    }

    @Test
    public void moneyTransfer() {
        var repository = mock(AccountRepository.class);
        var service = new AccountService(repository);

        var sender = new Account();
        sender.setId(1L);
        sender.setBalance(BigDecimal.valueOf(500L));

        var destination = new Account();
        destination.setId(2L);
        destination.setBalance(BigDecimal.valueOf(500L));

        var request = new TransferMoneyRequest(
                sender.getId(),
                destination.getId(),
                BigDecimal.valueOf(200L)
        );

        given(repository.findById(sender.getId()))
                .willReturn(Optional.of(sender));
        given(repository.findById(destination.getId()))
                .willReturn(Optional.of(destination));

        service.transferMoney(
                request
        );

        verify(repository)
                .changeBalance(1L, new BigDecimal(300));
        verify(repository)
                .changeBalance(2L, new BigDecimal(700));
    }

    @Test
    public void moneyTransferDestinationAccountNotFound() {
        var repository = mock(AccountRepository.class);
        var service = new AccountService(repository);

        var sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal(1000));

        var destination = new Account();
        destination.setId(2L);

        var request = new TransferMoneyRequest(
                sender.getId(),
                destination.getId(),
                BigDecimal.valueOf(200L)
        );

        given(repository.findById(1L))
                .willReturn(Optional.of(sender));
        given(repository.findById(2L))
                .willReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> service.transferMoney(
                        request
                )
        );

        verify(repository, never())
                .changeBalance(anyLong(), any());
    }


}
