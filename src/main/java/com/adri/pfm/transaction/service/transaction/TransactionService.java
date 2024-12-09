package com.adri.pfm.transaction.service.transaction;

import com.adri.pfm.commons.exception.RepositoryException;
import com.adri.pfm.commons.exception.ResourceNotFoundException;
import com.adri.pfm.transaction.exception.TransactionException;
import com.adri.pfm.transaction.exception.TransactionStatusTransitionException;
import com.adri.pfm.transaction.model.account.Account;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.model.transaction.TransactionStatus;
import com.adri.pfm.transaction.repository.TransactionRepository;
import com.adri.pfm.transaction.service.account.AccountService;
import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusDetailService transactionStatusDetailService;

    @Transactional
    public Transaction createTransaction(Transaction newTransaction) {
        Preconditions.checkArgument(nonNull(newTransaction), "Transaction cannot be null");
        Preconditions.checkArgument(nonNull(newTransaction.getAccount()), "Account cannot be null");
        Preconditions.checkArgument(nonNull(newTransaction.getAmount()), "Transaction amount cannot be null");
        Preconditions.checkArgument(nonNull(newTransaction.getDate()), "Transaction date cannot be null");
        Preconditions.checkArgument(isNotEmpty(newTransaction.getConcept()), "Transaction concept cannot be null");
        log.info("Creating new transaction for account id '{}'", newTransaction.getAccount().getAccountId());

        Account account = accountService.findByAccountId(newTransaction.getAccount().getAccountId());
        newTransaction.setAccount(account);
        newTransaction = save(newTransaction);
        changeTransactionStatus(newTransaction, TransactionStatus.CREATED);
        return newTransaction;
    }

    @Transactional
    public void updateTransactionStatus(long transactionId, TransactionStatus newStatus) {
        Transaction transaction = findTransaction(transactionId);
        changeTransactionStatus(transaction, newStatus);
    }

    public Transaction findTransaction(final long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for transaction id "+transactionId));
    }

    public Transaction findTransaction(final long transactionId,
                                        final long accountId,
                                        final long userAccountId) {
        return transactionRepository.findByTransactionIdAndAccountAccountIdAndAccountUserAccountId(transactionId, accountId, userAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for transaction id '"+transactionId+"', account id '"
                        +accountId+"' and user account id '"+userAccountId+"'"));
    }

    public List<Transaction> findTransactions(final long accountId,
                                              final long userAccountId,
                                              final TransactionStatus status) {
        List<Transaction> result;
        if (isNull(status)) {
            result = transactionRepository.findByAccountAccountIdAndAccountUserAccountId(accountId, userAccountId);
        } else {
            result = transactionRepository.findByAccountAccountIdAndAccountUserAccountIdAndCurrentStatus(accountId, userAccountId, status);
        }
        return result;
    }

    public List<Transaction> findMatchingDuplicatedTransaction(final Transaction transaction) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(transaction.getDate());
        calendarStart.set(Calendar.SECOND, -1);
        Date startDate = calendarStart.getTime();

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(transaction.getDate());
        calendarEnd.set(Calendar.MILLISECOND, -1);
        Date endDate = calendarEnd.getTime();

        log.debug("Looking for duplicated transactions matching transaction id '{}' between dates ({}, {})",
                transaction.getTransactionId(), startDate, endDate);
        return transactionRepository.findDuplicateTransactions(transaction.getConcept(), transaction.getAmount(),
                transaction.getAccount().getAccountId(), startDate, endDate);
    }

    private void changeTransactionStatus(final Transaction transaction, TransactionStatus newStatus) {
        try {
            transactionStatusDetailService.changeTransactionStatus(transaction, newStatus);
        } catch (TransactionStatusTransitionException e) {
            throw new TransactionException(e.getMessage());
        }
    }

    private Transaction save(Transaction transaction) {
        Transaction savedTransaction;
        try {
            savedTransaction = transactionRepository.save(transaction);
            log.info("Transaction saved with id '{}'", transaction.getTransactionId());
            log.debug("Transaction: {}", transaction);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving Transaction", e);
            throw new IllegalArgumentException("Data integrity violation while saving Transaction");
        } catch (Exception e) {
            log.error("Exception while saving Transaction", e);
            throw new RepositoryException("Exception while saving Transaction");
        }
        return savedTransaction;
    }
}
