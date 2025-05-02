package com.amf.pfm.transaction.application.transaction;

import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import com.amf.pfm.transaction.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.ACCOUNT_NOT_FOUND;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction(request.concept(), request.amount(), request.date(), request.accountId());
        if (!accountRepository.existsById(request.accountId())) {
            throw new EntityNotFoundException(String.format(ACCOUNT_NOT_FOUND, request.accountId()));
        }
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(UUID id, UUID accountId) {
        return transactionRepository.findByIdAndAccountId(id, accountId);
    }

    public List<Transaction> getTransactionsByAccountId(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

}
