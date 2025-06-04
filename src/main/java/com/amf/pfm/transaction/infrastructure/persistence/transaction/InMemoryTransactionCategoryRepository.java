package com.amf.pfm.transaction.infrastructure.persistence.transaction;

import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.model.TransactionCategory;
import com.amf.pfm.transaction.domain.repository.TransactionCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.amf.pfm.transaction.domain.exception.Messages.TRANSACTION_CATEGORY_NOT_FOUND;

@Repository
public class InMemoryTransactionCategoryRepository implements TransactionCategoryRepository {

    private final Map<UUID, TransactionCategory> categories = new ConcurrentHashMap<>();

    @Override
    public TransactionCategory save(TransactionCategory transactionCategory) {
        categories.put(transactionCategory.getId(), transactionCategory);
        return transactionCategory;
    }

    @Override
    public TransactionCategory findById(UUID id) {
        if (!categories.containsKey(id)) {
            throw new EntityNotFoundException(String.format(TRANSACTION_CATEGORY_NOT_FOUND, id));
        }
        return categories.get(id);
    }

    @Override
    public List<TransactionCategory> findAllRootCategories() {
        return categories.values().stream()
                .filter(category -> category.getParentCategory() == null)
                .toList();
    }

    @Override
    public List<TransactionCategory> findByParentId(UUID parentId) {
        return categories.values().stream()
                .filter(category -> category.getParentCategory() != null && category.getParentCategory().getId().equals(parentId))
                .toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return categories.values().stream()
                .anyMatch(category -> category.getId().equals(id));
    }

    @Override
    public boolean existsByName(String name) {
        return categories.values().stream()
                .anyMatch(category -> category.getName().equals(name));
    }
}
