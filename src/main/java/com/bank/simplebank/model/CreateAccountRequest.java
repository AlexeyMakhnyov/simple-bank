package com.bank.simplebank.model;

import jakarta.validation.constraints.Pattern;

public record CreateAccountRequest(
        String name,
        @Pattern(regexp = "[0-9]{4}", message = "The PIN code must consist of 4 digits")
        String pin
) {}
