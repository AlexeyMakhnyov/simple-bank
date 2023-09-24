package com.bank.simplebank.model;

import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record WithdrawAccountBalanceRequest(
        Long accountNumber,
        @Pattern(regexp = "[0-9]{4}", message = "The PIN code must consist of 4 digits")
        String pin,
        BigDecimal amount
) {
}
