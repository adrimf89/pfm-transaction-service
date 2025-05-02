package com.amf.pfm.transaction.domain.exception;

public class TransactionException extends TransactionServiceException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
