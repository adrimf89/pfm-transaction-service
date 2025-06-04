package com.amf.pfm.transaction.infrastructure.persistence;

import com.amf.pfm.transaction.domain.model.Account;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.domain.model.TransactionCategory;
import com.amf.pfm.transaction.infrastructure.persistence.account.InMemoryAccountRepository;
import com.amf.pfm.transaction.infrastructure.persistence.transaction.InMemoryTransactionCategoryRepository;
import com.amf.pfm.transaction.infrastructure.persistence.transaction.InMemoryTransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class InMemoryDataInitializer {

    private final InMemoryTransactionCategoryRepository transactionCategoryRepository;
    private final InMemoryTransactionRepository transactionRepository;
    private final InMemoryAccountRepository accountRepository;

    public InMemoryDataInitializer(InMemoryTransactionCategoryRepository transactionCategoryRepository,
                                   InMemoryTransactionRepository transactionRepository,
                                   InMemoryAccountRepository accountRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public void initializeData() {
        // Initialize transaction categories
        TransactionCategory expenseCategory = new TransactionCategory("Expenses", "General expenses category", null);
        TransactionCategory incomeCategory = new TransactionCategory("Income", "General income category", null);

        TransactionCategory foodCategory = new TransactionCategory("Food", "Expenses related to food", expenseCategory);
        TransactionCategory transportCategory = new TransactionCategory("Transport", "Expenses related to transport", expenseCategory);
        TransactionCategory entertainmentCategory = new TransactionCategory("Entertainment", "Expenses related to entertainment", expenseCategory);
        TransactionCategory salaryCategory = new TransactionCategory("Salary", "Income from salary", incomeCategory);

        transactionCategoryRepository.save(expenseCategory);
        transactionCategoryRepository.save(incomeCategory);
        transactionCategoryRepository.save(foodCategory);
        transactionCategoryRepository.save(transportCategory);
        transactionCategoryRepository.save(entertainmentCategory);
        transactionCategoryRepository.save(salaryCategory);

        // Initialize accounts
        Account account = new Account("DE89370400440532013000", BigDecimal.valueOf(2472.00));
        accountRepository.save(account);

        // Initialize transactions
        Transaction transaction1 = new Transaction("Lunch at restaurant", BigDecimal.valueOf(-15.50), LocalDateTime.now(), account.getId(), foodCategory.getId());
        Transaction transaction2 = new Transaction("Bus ticket", BigDecimal.valueOf(-2.50), LocalDateTime.now(), account.getId(), transportCategory.getId());
        Transaction transaction3 = new Transaction("Movie ticket", BigDecimal.valueOf(-10.00), LocalDateTime.now(), account.getId(), entertainmentCategory.getId());
        Transaction transaction4 = new Transaction("Monthly salary", BigDecimal.valueOf(2500.00), LocalDateTime.now(), account.getId(), salaryCategory.getId());
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);
        transactionRepository.save(transaction4);
    }

    @PostConstruct
    public void onStartup() {
        initializeData();
    }
}
