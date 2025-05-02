package com.amf.pfm.transaction.domain.repository;

import com.amf.pfm.transaction.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Transaction findByIdAndAccountId(UUID id, UUID accountId);
    List<Transaction> findByAccountId(UUID accountId);
}
