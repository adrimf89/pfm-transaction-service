package com.amf.pfm.transaction.domain.model;

import com.amf.pfm.transaction.domain.exception.AccountException;
import com.amf.pfm.transaction.domain.validator.IbanValidator;
import io.micrometer.common.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.ACCOUNT_WITH_INVALID_IBAN;

public class Account {

    private final UUID id;
    private final String iban;
    private BigDecimal balance;

    public Account(String iban, BigDecimal balance) {
        this.id = UUID.randomUUID();
        this.iban = iban;
        this.balance = balance;
        validateAccount();
    }

    public Account(UUID id, String iban, BigDecimal balance) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
        validateAccount();
    }

    public UUID getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addOrSubtractAmount(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    private void validateAccount() {
        if (StringUtils.isBlank(iban) || !IbanValidator.isValidIban(iban)) {
            throw new AccountException(ACCOUNT_WITH_INVALID_IBAN);
        }
    }
}
