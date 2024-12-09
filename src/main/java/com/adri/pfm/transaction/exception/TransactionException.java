package com.adri.pfm.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
