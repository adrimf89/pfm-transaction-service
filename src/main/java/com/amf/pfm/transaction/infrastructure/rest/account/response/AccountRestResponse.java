package com.amf.pfm.transaction.infrastructure.rest.account.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountRestResponse(UUID id, String iban, BigDecimal balance) {
}
