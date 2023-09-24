package com.bank.simplebank;

import com.bank.simplebank.entity.Account;
import com.bank.simplebank.exception.AccountNotFoundException;
import com.bank.simplebank.model.CreateAccountRequest;
import com.bank.simplebank.model.CreateAccountResponse;
import com.bank.simplebank.model.TransferMoneyRequest;
import com.bank.simplebank.repository.AccountRepository;
import com.bank.simplebank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SimpleBankApplicationTests {

	private final AccountService accountService;

	@Autowired
	SimpleBankApplicationTests(AccountService accountService) {
		this.accountService = accountService;
	}

	@Test
	void contextLoads() {
	}


	@Test
	void createAccount() {
		var account = new CreateAccountRequest(
				"test",
				"1234"
		);

		var response = accountService.createAccount(account);

		assertEquals(response, new CreateAccountResponse(3L));
	}

}
