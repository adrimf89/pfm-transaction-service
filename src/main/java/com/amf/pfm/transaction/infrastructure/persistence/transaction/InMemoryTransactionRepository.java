package com.amf.pfm.transaction.infrastructure.persistence.transaction;

import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.domain.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.amf.pfm.transaction.domain.exception.Messages.TRANSACTION_NOT_FOUND;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final Map<UUID, Map<UUID, Transaction>> transactionsByAccount = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        if (transactionsByAccount.containsKey(transaction.getAccountId())) {
            transactionsByAccount.get(transaction.getAccountId()).put(transaction.getId(), transaction);
        } else {
            Map<UUID, Transaction> accountTransactions = new ConcurrentHashMap<>();
            accountTransactions.put(transaction.getId(), transaction);
            transactionsByAccount.put(transaction.getAccountId(), accountTransactions);
        }
        return transaction;
    }

    @Override
    public Transaction findByIdAndAccountId(UUID id, UUID accountId) {
        if (transactionsByAccount.containsKey(accountId)) {
            Map<UUID, Transaction> accountTransactions = transactionsByAccount.get(accountId);
            if (accountTransactions.containsKey(id)) {
                return accountTransactions.get(id);
            }
        }
        throw new EntityNotFoundException(String.format(TRANSACTION_NOT_FOUND, id, accountId));
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return new ArrayList<>(transactionsByAccount
                .getOrDefault(accountId, Collections.emptyMap())
                .values());
    }

    @Override
    public Transaction updateTransactionStatus(UUID id, UUID accountId, Transaction.TransactionStatus status) {
        if (transactionsByAccount.containsKey(accountId)) {
            Map<UUID, Transaction> accountTransactions = transactionsByAccount.get(accountId);
            if (accountTransactions.containsKey(id)) {
                accountTransactions.get(id).setStatus(status);
                return accountTransactions.get(id);
            }
        }
        throw new EntityNotFoundException(String.format(TRANSACTION_NOT_FOUND, id, accountId));
    }
}
