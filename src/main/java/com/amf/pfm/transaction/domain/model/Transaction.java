package com.amf.pfm.transaction.domain.model;

import com.amf.pfm.transaction.domain.exception.TransactionException;
import io.micrometer.common.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.*;
import static java.util.Objects.isNull;

public class Transaction {

    public enum TransactionStatus { CREATED, PROCESSED, DISCARDED }

    private final UUID id;
    private final String concept;
    private final BigDecimal amount;
    private final LocalDate date;
    private final UUID accountId;
    private TransactionStatus status;

    public Transaction(String concept, BigDecimal amount, LocalDate date, UUID accountId) {
        this.id = UUID.randomUUID();
        this.concept = concept;
        this.amount = amount;
        this.date = date;
        this.accountId = accountId;
        this.status = TransactionStatus.CREATED;
        validateTransaction();
    }

    public Transaction(UUID id, String concept, BigDecimal amount, LocalDate date, UUID accountId, TransactionStatus status) {
        this.id = id;
        this.concept = concept;
        this.amount = amount;
        this.date = date;
        this.accountId = accountId;
        this.status = status;
        validateTransaction();
    }

    public UUID getId() {
        return id;
    }

    public String getConcept() {
        return concept;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    private void validateTransaction() {
        if (StringUtils.isBlank(concept)) {
            throw new TransactionException(TRANSACTION_WITH_INVALID_CONCEPT);
        }
        if (isNull(amount)) {
            throw new TransactionException(TRANSACTION_WITH_INVALID_AMOUNT);
        }
        if (isNull(date)) {
            throw new TransactionException(TRANSACTION_WITH_INVALID_DATE);
        }
        if (isNull(accountId)) {
            throw new TransactionException(TRANSACTION_WITH_INVALID_ACCOUNT_ID);
        }
    }
}
