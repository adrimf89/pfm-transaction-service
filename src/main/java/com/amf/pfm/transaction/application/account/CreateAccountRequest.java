package com.amf.pfm.transaction.application.account;

import java.math.BigDecimal;

public record CreateAccountRequest(String iban, BigDecimal initialBalance) {
}
