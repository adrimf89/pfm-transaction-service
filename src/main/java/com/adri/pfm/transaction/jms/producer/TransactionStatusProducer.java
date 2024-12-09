package com.adri.pfm.transaction.jms.producer;

import com.adri.pfm.commons.jms.BaseProducer;
import com.adri.pfm.commons.jms.message.TransactionMessage;
import com.adri.pfm.transaction.config.PfmTransactionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionStatusProducer extends BaseProducer<TransactionMessage> {

    @Autowired
    private PfmTransactionConfig pfmTransactionConfig;

    @Override
    protected void validateMessage(TransactionMessage transactionMessage) throws IllegalArgumentException {

    }

    @Override
    protected String getProducerQueue() {
        return pfmTransactionConfig.getTransactionStatusQueue();
    }
}
