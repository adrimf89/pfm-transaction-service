package com.amf.pfm.transaction.infrastructure.rest.transaction.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionRestResponse(UUID id, String concept, BigDecimal amount, LocalDateTime date, UUID accountId, String status) {
}
