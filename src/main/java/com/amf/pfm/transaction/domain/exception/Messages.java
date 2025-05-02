package com.amf.pfm.transaction.domain.exception;

public class Messages {

    public static final String ACCOUNT_WITH_INVALID_IBAN = "Account with invalid IBAN";
    public static final String ACCOUNT_IBAN_ALREADY_EXISTS = "Account with IBAN '%s' already exists";
    public static final String ACCOUNT_NOT_FOUND = "Account with ID '%s' not found";

    public static final String TRANSACTION_WITH_INVALID_CONCEPT = "Transaction concept cannot be empty";
    public static final String TRANSACTION_WITH_INVALID_DATE = "Transaction date cannot be empty";
    public static final String TRANSACTION_WITH_INVALID_AMOUNT = "Transaction amount cannot be empty";
    public static final String TRANSACTION_WITH_INVALID_ACCOUNT_ID = "Transaction account ID cannot be empty";
    public static final String TRANSACTION_NOT_FOUND = "Transaction with ID '%s' and account ID '%s' not found";
    public static final String TRANSACTION_STATUS_NOT_EQUIVALENT = "Received transaction status '%s' not equal to the current transaction status '%s'";
    public static final String TRANSACTION_INVALID_STATUS_TRANSITION = "Invalid transaction status transition from '%s' to '%s'";
}
