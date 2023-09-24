package com.bank.simplebank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid pin!")
public class InvalidPinException extends RuntimeException {

    public InvalidPinException() {
        super("Invalid pin!");
    }

}
