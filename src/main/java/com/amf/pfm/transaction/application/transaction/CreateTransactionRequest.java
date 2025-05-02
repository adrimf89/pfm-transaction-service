package com.amf.pfm.transaction.application.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateTransactionRequest(String concept, BigDecimal amount, LocalDate date, UUID accountId) {
}
