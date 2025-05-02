package com.amf.pfm.transaction.domain.exception;

public class TransactionStatusTransitionException extends TransactionServiceException {

    public TransactionStatusTransitionException(String message) {
        super(message);
    }

    public TransactionStatusTransitionException(String message, Throwable cause) {
        super(message, cause);
    }

}
