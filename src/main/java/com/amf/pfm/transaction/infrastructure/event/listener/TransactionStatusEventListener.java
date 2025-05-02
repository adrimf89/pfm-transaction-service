package com.amf.pfm.transaction.infrastructure.event.listener;

import com.amf.pfm.transaction.application.transaction.TransactionStatusService;
import com.amf.pfm.transaction.domain.event.DomainEventListener;
import com.amf.pfm.transaction.domain.event.transaction.TransactionStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionStatusEventListener implements DomainEventListener<TransactionStatusEvent> {

    private final TransactionStatusService transactionStatusService;

    public TransactionStatusEventListener(TransactionStatusService transactionStatusService) {
        this.transactionStatusService = transactionStatusService;
    }

    @EventListener
    public void onTransactionStatusEvent(TransactionStatusEvent event) {
        log.info("New transaction status event received for ID {} and status {} and time {}", event.transactionId(), event.status(), event.eventTime());
        onEvent(event);
    }

    @Override
    public void processEvent(TransactionStatusEvent event) {
        transactionStatusService.handleTransactionStatusChange(event.transactionId(), event.accountId(), event.status());
    }

    @Override
    public void processError(TransactionStatusEvent event, Exception e) {
        log.error("Error processing transaction status event: {}", e.getMessage());
    }
}
