package com.adri.pfm.transaction.dto.transaction;

import java.math.BigDecimal;
import java.util.Date;

public record TransactionDTO(long transactionId, String concept, Date date, BigDecimal amount, long accountId, String status) {
}
