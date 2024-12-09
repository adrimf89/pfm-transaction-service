package com.adri.pfm.transaction.service.transaction;

import com.adri.pfm.commons.exception.RepositoryException;
import com.adri.pfm.transaction.event.transaction.TransactionStatusUpdateEvent;
import com.adri.pfm.transaction.exception.TransactionStatusTransitionException;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.model.transaction.TransactionStatus;
import com.adri.pfm.transaction.model.transaction.TransactionStatusDetail;
import com.adri.pfm.transaction.repository.TransactionStatusDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

import static com.adri.pfm.transaction.model.transaction.TransactionStatus.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionStatusDetailService {

    private final static String TRANSACTION_STATUS_TRANSITION_ERROR = "Transaction with id '%s' cannot be changed from status '%s' to '%s'";

    private final ApplicationEventPublisher publisher;
    private final TransactionStatusDetailRepository transactionStatusDetailRepository;

    @Transactional
    public void changeTransactionStatus(final Transaction transaction, final TransactionStatus status) throws TransactionStatusTransitionException {
        log.info("Changing status for transaction id '{}' to status '{}'",
                transaction.getTransactionId(), status);
        validateTransactionStatusChange(transaction.getTransactionId(), status);
        TransactionStatusDetail newTransactionStatusDetail = new TransactionStatusDetail();
        newTransactionStatusDetail.setTransaction(transaction);
        newTransactionStatusDetail.setStatus(status);
        newTransactionStatusDetail.setStatusUpdateDate(new Date());
        save(newTransactionStatusDetail);
    }

    private void validateTransactionStatusChange(long transactionId, TransactionStatus newTransactionStatus) throws TransactionStatusTransitionException {
        TransactionStatusDetail lastTransactionStatus =
                transactionStatusDetailRepository.findFirstByTransactionTransactionIdOrderByStatusUpdateDateDesc(transactionId)
                        .orElse(null);

        if (isNull(lastTransactionStatus)) {
            if (!CREATED.equals(newTransactionStatus)) {
                String errorMessage = String.format(TRANSACTION_STATUS_TRANSITION_ERROR,
                        transactionId, "NO STATUS", newTransactionStatus);
                log.error(errorMessage);
                throw new TransactionStatusTransitionException(errorMessage);
            }
        } else if (lastTransactionStatus.getStatus().equals(newTransactionStatus)) {
            String errorMessage = String.format(TRANSACTION_STATUS_TRANSITION_ERROR,
                    transactionId, lastTransactionStatus.getStatus(), newTransactionStatus);
            log.error(errorMessage);
            throw new TransactionStatusTransitionException(errorMessage);
        } else if (PROCESSED.equals(lastTransactionStatus.getStatus())) {
            String errorMessage = String.format(TRANSACTION_STATUS_TRANSITION_ERROR,
                    transactionId, lastTransactionStatus.getStatus(), newTransactionStatus);
            log.error(errorMessage);
            throw new TransactionStatusTransitionException(errorMessage);
        } else if (DUPLICATED.equals(newTransactionStatus)
                && !CREATED.equals(lastTransactionStatus.getStatus())) {
            String errorMessage = String.format(TRANSACTION_STATUS_TRANSITION_ERROR,
                    transactionId, lastTransactionStatus.getStatus(), newTransactionStatus);
            log.error(errorMessage);
            throw new TransactionStatusTransitionException(errorMessage);
        } else if (DISCARDED.equals(newTransactionStatus)
                && !Arrays.asList(CREATED, DUPLICATED).contains(lastTransactionStatus.getStatus())) {
            String errorMessage = String.format(TRANSACTION_STATUS_TRANSITION_ERROR,
                    transactionId, lastTransactionStatus.getStatus(), newTransactionStatus);
            log.error(errorMessage);
            throw new TransactionStatusTransitionException(errorMessage);
        }
    }

    private void save(TransactionStatusDetail transactionStatusDetail) {
        try {
            transactionStatusDetail = transactionStatusDetailRepository.save(transactionStatusDetail);
            publishStatusUpdateEvent(transactionStatusDetail);
            log.info("Transaction status detail saved for transaction id '{}' and status '{}'",
                    transactionStatusDetail.getTransaction().getTransactionId(), transactionStatusDetail.getStatus());
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving Transaction Status Detail", e);
            throw new IllegalArgumentException("Data integrity violation while saving Transaction Status Detail");
        } catch (Exception e) {
            log.error("Exception while saving Transaction Status Detail", e);
            throw new RepositoryException("Exception while saving Transaction Status Detail");
        }
    }

    private void publishStatusUpdateEvent(final TransactionStatusDetail transactionStatusDetail) {
        log.info("Publishing Transaction Status Update event for transaction status detail id '{}'", transactionStatusDetail.getTransactionStatusId());
        publisher.publishEvent(new TransactionStatusUpdateEvent(this).withTransactionStatusDetail(transactionStatusDetail));
    }
}
