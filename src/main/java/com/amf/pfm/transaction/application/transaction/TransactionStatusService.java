package com.amf.pfm.transaction.application.transaction;

import com.amf.pfm.transaction.domain.event.DomainEventPublisher;
import com.amf.pfm.transaction.domain.event.transaction.TransactionStatusEvent;
import com.amf.pfm.transaction.domain.exception.TransactionException;
import com.amf.pfm.transaction.domain.model.Account;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import com.amf.pfm.transaction.domain.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.TRANSACTION_STATUS_NOT_EQUIVALENT;

public class TransactionStatusService {

    private final AccountRepository accountRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final TransactionRepository transactionRepository;

    public TransactionStatusService(AccountRepository accountRepository,
                                    DomainEventPublisher domainEventPublisher,
                                    TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.domainEventPublisher = domainEventPublisher;
        this.transactionRepository = transactionRepository;
    }

    public void handleTransactionStatusChange(UUID transactionId, UUID accountId, Transaction.TransactionStatus newStatus) {
        Transaction transaction = transactionRepository.findByIdAndAccountId(transactionId, accountId);

        if (!newStatus.equals(transaction.getStatus())) {
            throw new TransactionException(String.format(TRANSACTION_STATUS_NOT_EQUIVALENT, newStatus, transaction.getStatus()));
        }

        if (transaction.getStatus() == Transaction.TransactionStatus.CREATED) {
            processTransaction(transaction);
        }
    }

    private void processTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId());
        account.addOrSubtractAmount(transaction.getAmount());
        accountRepository.updateAccountBalance(account.getId(), account.getBalance());

        transaction = transactionRepository.updateTransactionStatus(transaction.getId(), transaction.getAccountId(), Transaction.TransactionStatus.PROCESSED);
        publishTransactionStatusEvent(transaction);
    }

    private void publishTransactionStatusEvent(Transaction transaction) {
        domainEventPublisher.publish(new TransactionStatusEvent(transaction.getId(), transaction.getAccountId(), transaction.getStatus(), LocalDateTime.now()));
    }
}
