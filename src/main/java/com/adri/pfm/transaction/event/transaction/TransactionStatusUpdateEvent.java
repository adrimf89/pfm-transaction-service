package com.adri.pfm.transaction.event.transaction;

import com.adri.pfm.transaction.model.transaction.TransactionStatusDetail;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

@Getter
public class TransactionStatusUpdateEvent extends ApplicationEvent {

    private transient TransactionStatusDetail transactionStatusDetail;

    public TransactionStatusUpdateEvent(Object source) {
        super(source);
    }

    public TransactionStatusUpdateEvent withTransactionStatusDetail(TransactionStatusDetail transactionStatusDetail) {
        this.transactionStatusDetail = transactionStatusDetail;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransactionStatusUpdateEvent that = (TransactionStatusUpdateEvent) obj;
        return Objects.equals(transactionStatusDetail, that.transactionStatusDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionStatusDetail);
    }
}
