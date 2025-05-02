package com.amf.pfm.transaction.infrastructure.persistence.account;

import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.model.Account;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.amf.pfm.transaction.domain.exception.Messages.ACCOUNT_NOT_FOUND;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Account findById(UUID id) {
        if (!accounts.containsKey(id)) {
            throw new EntityNotFoundException(String.format(ACCOUNT_NOT_FOUND, id));
        }
        return accounts.get(id);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public boolean existsByIban(String iban) {
        return accounts.values().stream()
                .anyMatch(account -> account.getIban().equals(iban));
    }

    @Override
    public boolean existsById(UUID id) {
        return accounts.containsKey(id);
    }
}
