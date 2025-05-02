package com.amf.pfm.transaction.infrastructure.rest.account.request;

import java.math.BigDecimal;

public record AccountRestRequest(String iban, BigDecimal initialBalance) {
}
