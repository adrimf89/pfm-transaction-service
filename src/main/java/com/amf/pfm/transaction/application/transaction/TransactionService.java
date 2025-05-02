package com.amf.pfm.transaction.application.transaction;

import com.amf.pfm.transaction.domain.event.DomainEventPublisher;
import com.amf.pfm.transaction.domain.event.transaction.TransactionStatusEvent;
import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import com.amf.pfm.transaction.domain.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.ACCOUNT_NOT_FOUND;

public class TransactionService {

    private final AccountRepository accountRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              DomainEventPublisher domainEventPublisher) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Transaction createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction(request.concept(), request.amount(), request.date(), request.accountId());
        if (!accountRepository.existsById(request.accountId())) {
            throw new EntityNotFoundException(String.format(ACCOUNT_NOT_FOUND, request.accountId()));
        }
        transaction = transactionRepository.save(transaction);
        publishTransactionStatusEvent(transaction);
        return transaction;
    }

    public Transaction getTransactionById(UUID id, UUID accountId) {
        return transactionRepository.findByIdAndAccountId(id, accountId);
    }

    public List<Transaction> getTransactionsByAccountId(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    private void publishTransactionStatusEvent(Transaction transaction) {
        domainEventPublisher.publish(new TransactionStatusEvent(transaction.getId(), transaction.getAccountId(), transaction.getStatus(), LocalDateTime.now()));
    }
}
