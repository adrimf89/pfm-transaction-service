package com.amf.pfm.transaction.domain.repository;

import com.amf.pfm.transaction.domain.model.TransactionCategory;

import java.util.List;
import java.util.UUID;

public interface TransactionCategoryRepository {
    TransactionCategory save(TransactionCategory transactionCategory);
    TransactionCategory findById(UUID id);
    List<TransactionCategory> findAllRootCategories();
    List<TransactionCategory> findByParentId(UUID parentId);
    boolean existsById(UUID id);
    boolean existsByName(String name);
}
