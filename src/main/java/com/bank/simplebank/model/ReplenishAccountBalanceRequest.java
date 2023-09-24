package com.bank.simplebank.model;

import java.math.BigDecimal;

public record ReplenishAccountBalanceRequest(
        Long accountNumber,
        BigDecimal amount
) {
}
