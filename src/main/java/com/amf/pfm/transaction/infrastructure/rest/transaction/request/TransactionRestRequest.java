package com.amf.pfm.transaction.infrastructure.rest.transaction.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRestRequest(String concept, BigDecimal amount, LocalDateTime date) {
}
