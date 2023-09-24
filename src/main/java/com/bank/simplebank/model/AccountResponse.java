package com.bank.simplebank.model;

import java.math.BigDecimal;

public record AccountResponse(String name, BigDecimal balance) {
}
