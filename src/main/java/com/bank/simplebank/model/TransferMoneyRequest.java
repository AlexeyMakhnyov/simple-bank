package com.bank.simplebank.model;

import java.math.BigDecimal;

public record TransferMoneyRequest(
        Long senderAccountNumber,
        Long receiverNumberAccount,
        BigDecimal amount
) {
}
