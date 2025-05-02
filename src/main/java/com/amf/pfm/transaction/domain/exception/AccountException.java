package com.amf.pfm.transaction.domain.exception;

public class AccountException extends TransactionServiceException {

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
