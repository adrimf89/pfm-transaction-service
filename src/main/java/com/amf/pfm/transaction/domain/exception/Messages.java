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

    public static final String TRANSACTION_CATEGORY_WITH_INVALID_NAME = "Transaction category name cannot be empty";
    public static final String TRANSACTION_CATEGORY_WITH_INVALID_DESCRIPTION = "Transaction category description cannot be empty";
    public static final String TRANSACTION_CATEGORY_NAME_ALREADY_EXISTS = "Transaction category with name '%s' already exists";
    public static final String TRANSACTION_CATEGORY_PARENT_NOT_FOUND = "Parent transaction category with ID '%s' not found";
    public static final String TRANSACTION_CATEGORY_MAXIMUM_DEPTH = "Maximum depth of the category tree is 2";
    public static final String TRANSACTION_CATEGORY_NOT_FOUND = "Transaction category with ID '%s' not found";
}
