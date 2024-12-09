package com.adri.pfm.transaction.repository;

import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.model.transaction.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionIdAndAccountAccountIdAndAccountUserAccountId(
            long transactionId, long accountId, long userAccountId);

    List<Transaction> findByAccountAccountIdAndAccountUserAccountId(
            long accountId, long userAccountId);

    List<Transaction> findByAccountAccountIdAndAccountUserAccountIdAndCurrentStatus(
            long accountId, long userAccountId, TransactionStatus currentStatus);

    @Query("SELECT t FROM TransactionStatusDetail tsd " +
            "JOIN tsd.transaction t " +
            "WHERE tsd.status IN ('CREATED', 'PROCESSED') " +
            "AND t.concept = :concept AND t.amount = :amount " +
            "AND t.account.accountId = :accountId " +
            "AND t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findDuplicateTransactions(@Param("concept") String concept, @Param("amount") BigDecimal amount,
                                                @Param("accountId") Long accountId, @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);
}
