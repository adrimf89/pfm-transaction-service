package com.adri.pfm.transaction.dto.transaction;

import com.adri.pfm.transaction.dto.account.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;

public record CreateTransactionDTO(String concept, Date date, BigDecimal amount, AccountDTO account) {
}
