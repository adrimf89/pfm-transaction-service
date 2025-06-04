package com.amf.pfm.transaction.domain.exception;

public class TransactionCategoryException extends TransactionServiceException {

    public TransactionCategoryException(String message) {
        super(message);
    }

    public TransactionCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
