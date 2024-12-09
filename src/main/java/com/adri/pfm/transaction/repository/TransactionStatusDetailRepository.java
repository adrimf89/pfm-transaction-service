package com.adri.pfm.transaction.repository;

import com.adri.pfm.transaction.model.transaction.TransactionStatusDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionStatusDetailRepository extends JpaRepository<TransactionStatusDetail, Long> {

    Optional<TransactionStatusDetail> findFirstByTransactionTransactionIdOrderByStatusUpdateDateDesc(@Param("transactionId") Long transactionId);
}
