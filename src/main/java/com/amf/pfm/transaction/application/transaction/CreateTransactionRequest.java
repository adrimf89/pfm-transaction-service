package com.amf.pfm.transaction.application.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTransactionRequest(String concept, BigDecimal amount, LocalDateTime date, UUID accountId) {
}
