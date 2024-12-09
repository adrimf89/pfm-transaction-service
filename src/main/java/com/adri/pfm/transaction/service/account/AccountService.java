package com.adri.pfm.transaction.service.account;

import com.adri.pfm.commons.exception.RepositoryException;
import com.adri.pfm.commons.exception.ResourceNotFoundException;
import com.adri.pfm.transaction.model.account.Account;
import com.adri.pfm.transaction.repository.AccountRepository;
import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Account newAccount) {
        Preconditions.checkArgument(nonNull(newAccount), "Account cannot be null");
        Preconditions.checkArgument(isNotEmpty(newAccount.getIban()), "Account IBAN cannot be null");
        log.info("Creating new account with IBAN '{}'", newAccount.getIban());
        newAccount.setBalance(BigDecimal.ZERO);
        return save(newAccount);
    }

    public Account findByAccountId(final long accountId) {
        log.info("Find account by id {}", accountId);
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("User account not found for id "+accountId));
    }

    public List<Account> findByUserAccountId(final long userAccountId) {
        log.info("Find accounts by user account id {}", userAccountId);
        return accountRepository.findByUserAccountId(userAccountId);
    }

    @Transactional
    public void updateAccountBalance(long accountId, BigDecimal amount) {
        log.info("Updating balance of account '{}' with amount '{}'", accountId, amount);
        Account account = accountRepository.findByIdForUpdate(accountId);
        log.debug("Current balance is '{}'", account.getBalance());
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        log.debug("New balance is '{}'", account.getBalance());
    }

    private Account save(Account account) {
        Account savedAccount = null;
        try {
            savedAccount = accountRepository.save(account);
            log.info("Account saved with id '{}'", savedAccount.getAccountId());
            log.debug("Account: {}", account);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving Account", e);
            throw new IllegalArgumentException("Data integrity violation while saving Account");
        } catch (Exception e) {
            log.error("Exception while saving Account", e);
            throw new RepositoryException("Exception while saving Account");
        }
        return savedAccount;
    }
}
