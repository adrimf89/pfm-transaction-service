package com.amf.pfm.transaction.domain.exception;

public class EntityConflictException extends TransactionServiceException {

    public EntityConflictException(String message) {
        super(message);
    }

    public EntityConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
