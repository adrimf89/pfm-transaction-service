package com.amf.pfm.transaction.infrastructure.config;

import com.amf.pfm.transaction.application.account.AccountService;
import com.amf.pfm.transaction.application.transaction.TransactionService;
import com.amf.pfm.transaction.application.transaction.TransactionStatusService;
import com.amf.pfm.transaction.domain.event.DomainEventPublisher;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import com.amf.pfm.transaction.domain.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountService(accountRepository);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository,
                                                 DomainEventPublisher domainEventPublisher,
                                                 AccountRepository accountRepository) {
        return new TransactionService(transactionRepository, accountRepository, domainEventPublisher);
    }

    @Bean
    public TransactionStatusService transactionStatusService(AccountRepository accountRepository,
                                                             DomainEventPublisher domainEventPublisher,
                                                             TransactionRepository transactionRepository) {
        return new TransactionStatusService(accountRepository, domainEventPublisher, transactionRepository);
    }

}
