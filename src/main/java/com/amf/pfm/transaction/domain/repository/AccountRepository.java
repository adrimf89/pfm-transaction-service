package com.amf.pfm.transaction.domain.repository;

import com.amf.pfm.transaction.domain.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountRepository {
    Account save(Account account);
    Account findById(UUID id);
    List<Account> findAll();
    boolean existsByIban(String iban);
    boolean existsById(UUID id);
}
