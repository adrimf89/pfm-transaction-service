package com.amf.pfm.transaction.infrastructure.rest.transaction.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionRestRequest(String concept, BigDecimal amount, LocalDateTime date, UUID categoryId) {
}
