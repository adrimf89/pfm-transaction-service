package com.amf.pfm.transaction.application.account;

import com.amf.pfm.transaction.domain.exception.EntityConflictException;
import com.amf.pfm.transaction.domain.model.Account;
import com.amf.pfm.transaction.domain.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.ACCOUNT_IBAN_ALREADY_EXISTS;


public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(CreateAccountRequest request) {
        Account account = new Account(request.iban(), request.initialBalance());
        if (accountRepository.existsByIban(account.getIban())) {
            throw new EntityConflictException(String.format(ACCOUNT_IBAN_ALREADY_EXISTS, account.getIban()));
        }

        return accountRepository.save(account);
    }

    public Account getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
