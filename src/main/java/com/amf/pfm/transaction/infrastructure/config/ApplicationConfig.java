package com.amf.pfm.transaction.infrastructure.config;

import com.amf.pfm.transaction.application.account.AccountService;
import com.amf.pfm.transaction.application.transaction.TransactionService;
import com.amf.pfm.transaction.application.transaction.TransactionStatusService;
import com.amf.pfm.transaction.application.transaction.category.TransactionCategoryService;
import com.amf.pfm.transaction.domain.UnitOfWork;
import com.amf.pfm.transaction.domain.event.DomainEventPublisher;
import com.amf.pfm.transaction.domain.repository.AccountRepository;
import com.amf.pfm.transaction.domain.repository.TransactionCategoryRepository;
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
                                                 AccountRepository accountRepository,
                                                 TransactionCategoryRepository transactionCategoryRepository) {
        return new TransactionService(transactionRepository, accountRepository, domainEventPublisher, transactionCategoryRepository);
    }

    @Bean
    public TransactionStatusService transactionStatusService(UnitOfWork unitOfWork,
                                                             AccountRepository accountRepository,
                                                             DomainEventPublisher domainEventPublisher,
                                                             TransactionRepository transactionRepository) {
        return new TransactionStatusService(unitOfWork, accountRepository, domainEventPublisher, transactionRepository);
    }

    @Bean
    public TransactionCategoryService transactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
        return new TransactionCategoryService(transactionCategoryRepository);
    }

}
