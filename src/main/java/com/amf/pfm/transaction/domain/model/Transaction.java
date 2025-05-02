package com.amf.pfm.transaction.domain.model;

import com.amf.pfm.transaction.domain.exception.TransactionException;
import com.amf.pfm.transaction.domain.exception.TransactionStatusTransitionException;
import io.micrometer.common.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.*;
import static java.util.Objects.isNull;

public class Transaction {

    public enum TransactionStatus { CREATED, PROCESSED, DISCARDED }

    private static final Map<TransactionStatus, TransactionStatus[]> validTransitions = new HashMap<>();

    static {
        validTransitions.put(TransactionStatus.CREATED, new TransactionStatus[]{TransactionStatus.PROCESSED, TransactionStatus.DISCARDED});
        validTransitions.put(TransactionStatus.PROCESSED, new TransactionStatus[]{});
        validTransitions.put(TransactionStatus.DISCARDED, new TransactionStatus[]{});
    }

    private final UUID id;
    private final String concept;
    private final BigDecimal amount;
    private final LocalDateTime date;
    private final UUID accountId;
    private TransactionStatus status;

    public Transaction(String concept, BigDecimal amount, LocalDateTime date, UUID accountId) {
        this.id = UUID.randomUUID();
        this.concept = concept;
        this.amount = amount;
        this.date = date;
        this.accountId = accountId;
        this.status = TransactionStatus.CREATED;
        validateTransaction();
    }

    public Transaction(UUID id, String concept, BigDecimal amount, LocalDateTime date, UUID accountId, TransactionStatus status) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus newStatus) {
        if (!isValidTransition(this.status, newStatus)) {
            throw new TransactionStatusTransitionException(String.format(TRANSACTION_INVALID_STATUS_TRANSITION, this.status, newStatus));
        }
        this.status = newStatus;
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

    private boolean isValidTransition(TransactionStatus fromStatus, TransactionStatus toStatus) {
        TransactionStatus[] allowedTransitions = validTransitions.get(fromStatus);
        if (allowedTransitions == null) {
            return false;
        }
        for (TransactionStatus allowedStatus : allowedTransitions) {
            if (allowedStatus == toStatus) {
                return true;
            }
        }
        return false;
    }
}
