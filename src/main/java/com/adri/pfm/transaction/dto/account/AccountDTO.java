package com.adri.pfm.transaction.dto.account;

import java.math.BigDecimal;

public record AccountDTO(long accountId, long userAccountId, String iban, BigDecimal balance) {
}
