package com.amf.pfm.transaction.infrastructure.rest.transaction.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRestRequest(String concept, BigDecimal amount, LocalDate date) {
}
