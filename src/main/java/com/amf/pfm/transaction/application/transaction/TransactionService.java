package com.amf.pfm.transaction.application.transaction;

import com.amf.pfm.transaction.domain.event.DomainEventPublisher;
import com.amf.pfm.transaction.domain.event.transaction.TransactionStatusEvent;
import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import com.amf.pfm.transaction.domain.repository.TransactionCategoryRepository;
import com.amf.pfm.transaction.domain.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.ACCOUNT_NOT_FOUND;
import static com.amf.pfm.transaction.domain.exception.Messages.TRANSACTION_CATEGORY_NOT_FOUND;

public class TransactionService {

    private final AccountRepository accountRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final TransactionRepository transactionRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              DomainEventPublisher domainEventPublisher, TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.domainEventPublisher = domainEventPublisher;
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public Transaction createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction(request.concept(), request.amount(), request.date(), request.accountId(), request.categoryId());
        if (!accountRepository.existsById(request.accountId())) {
            throw new EntityNotFoundException(String.format(ACCOUNT_NOT_FOUND, request.accountId()));
        }
        if (request.categoryId() != null && !transactionCategoryRepository.existsById(request.categoryId())) {
            throw new EntityNotFoundException(String.format(TRANSACTION_CATEGORY_NOT_FOUND, request.categoryId()));
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
