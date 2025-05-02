package com.amf.pfm.transaction.domain.exception;

public class EntityNotFoundException extends TransactionServiceException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
