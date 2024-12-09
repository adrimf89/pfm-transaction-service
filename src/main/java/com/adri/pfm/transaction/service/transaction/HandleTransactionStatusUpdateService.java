package com.adri.pfm.transaction.service.transaction;

import com.adri.pfm.commons.jms.message.TransactionMessage;
import com.adri.pfm.transaction.exception.TransactionStatusTransitionException;
import com.adri.pfm.transaction.jms.producer.TransactionStatusProducer;
import com.adri.pfm.transaction.mapper.transaction.TransactionMapper;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.model.transaction.TransactionStatus;
import com.adri.pfm.transaction.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HandleTransactionStatusUpdateService {

    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;
    private final TransactionStatusProducer transactionStatusProducer;
    private final TransactionStatusDetailService transactionStatusDetailService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void handleTransactionStatusUpdate(long transactionId, TransactionStatus status) {
        log.info("Handle transaction id '{}' status changed to '{}'", transactionId, status);

        try {
            Transaction transaction = updateTransactionCurrentStatus(transactionId, status);
            if (TransactionStatus.CREATED.equals(status)) {
                handleCreatedTransaction(transaction);
            }

            notifyTransactionUpdate(transaction);
        } catch (TransactionStatusTransitionException e) {
            log.error("Invalid transaction status transition. Status change will be reverted", e);
        } catch (Exception e) {
            log.error("Error handling transaction status", e);
        }
    }

    private Transaction updateTransactionCurrentStatus(long transactionId, TransactionStatus status) {
        Transaction transaction = transactionService.findTransaction(transactionId);
        transaction.setCurrentStatus(status);
        return transaction;
    }

    private void handleCreatedTransaction(Transaction transaction) throws TransactionStatusTransitionException {
        List<Transaction> duplicatedTransactions = transactionService.findMatchingDuplicatedTransaction(transaction);
        if (duplicatedTransactions.isEmpty()){
            accountService.updateAccountBalance(transaction.getAccount().getAccountId(), transaction.getAmount());
            transactionStatusDetailService.changeTransactionStatus(transaction, TransactionStatus.PROCESSED);
        } else {
            log.warn("Possible transaction duplicated with id '{}' with following transaction ids [{}]",
                    transaction.getTransactionId(), duplicatedTransactions.stream().map(Transaction::getTransactionId).toList());
            transactionStatusDetailService.changeTransactionStatus(transaction, TransactionStatus.DUPLICATED);
        }
    }

    private void notifyTransactionUpdate(final Transaction transaction){
        log.info("Notifying status update for transaction with id '{}' updated to status '{}'",
                transaction.getTransactionId(), transaction.getCurrentStatus());
        try {
            TransactionMessage transactionMessage = transactionMapper.toTransactionMessage(transaction);
            transactionStatusProducer.send(transactionMessage);
        } catch (Exception e){
            log.error("Transaction message cannot be sent for transaction id {}", transaction.getTransactionId(), e);
        }
    }
}
