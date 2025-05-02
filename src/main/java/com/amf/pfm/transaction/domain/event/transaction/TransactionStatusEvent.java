package com.amf.pfm.transaction.domain.event.transaction;

import com.amf.pfm.transaction.domain.event.DomainEvent;
import com.amf.pfm.transaction.domain.model.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionStatusEvent(UUID transactionId,
                                     UUID accountId,
                                     Transaction.TransactionStatus status,
                                     LocalDateTime eventTime) implements DomainEvent {
    @Override
    public LocalDateTime getEventTime() {
        return eventTime;
    }
}
