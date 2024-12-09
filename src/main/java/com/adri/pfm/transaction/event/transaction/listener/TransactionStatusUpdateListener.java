package com.adri.pfm.transaction.event.transaction.listener;

import com.adri.pfm.transaction.event.transaction.TransactionStatusUpdateEvent;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.model.transaction.TransactionStatus;
import com.adri.pfm.transaction.service.transaction.HandleTransactionStatusUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionStatusUpdateListener {

    private final HandleTransactionStatusUpdateService handleTransactionStatusUpdateService;

    @TransactionalEventListener
    public void onTransactionStatusUpdate(TransactionStatusUpdateEvent event) {
        log.info("New transaction status update event: {}", event);
        Transaction transaction = event.getTransactionStatusDetail().getTransaction();
        TransactionStatus status = event.getTransactionStatusDetail().getStatus();
        handleTransactionStatusUpdateService.handleTransactionStatusUpdate(transaction.getTransactionId(), status);
    }

//    @TransactionalEventListener(condition = "#event.transactionStatusDetail instanceof T(com.adri.pfm.transaction.model.transaction.TransactionStatusDetail) " +
//            "and event.transactionStatusDetail.status == T(com.adri.pfm.transaction.model.TransactionStatus).CREATED")
//    public void onCreated(TransactionStatusUpdateEvent event) {
//        log.info("Handle CREATED transaction event");
//    }
//
}
