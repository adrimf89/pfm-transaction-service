package com.adri.pfm.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TransactionImportException extends RuntimeException {
    public TransactionImportException(String message) {
        super(message);
    }
}
