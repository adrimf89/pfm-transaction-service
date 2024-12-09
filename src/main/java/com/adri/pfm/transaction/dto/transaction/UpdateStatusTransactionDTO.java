package com.adri.pfm.transaction.dto.transaction;

import com.adri.pfm.transaction.model.transaction.TransactionStatus;

public record UpdateStatusTransactionDTO(TransactionStatus status) {
}
