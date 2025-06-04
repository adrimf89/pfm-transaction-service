package com.amf.pfm.transaction.application.transaction.category;

import com.amf.pfm.transaction.domain.exception.EntityConflictException;
import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.exception.TransactionCategoryException;
import com.amf.pfm.transaction.domain.model.TransactionCategory;
import com.amf.pfm.transaction.domain.repository.TransactionCategoryRepository;

import java.util.List;
import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.*;

public class TransactionCategoryService {

    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    public TransactionCategory createCategory(CreateTransactionCategoryRequest request) {
        if (transactionCategoryRepository.existsByName(request.name())) {
            throw new EntityConflictException(String.format(TRANSACTION_CATEGORY_NAME_ALREADY_EXISTS, request.name()));
        }

        TransactionCategory parentCategory = getParentCategory(request.parentId());
        TransactionCategory transactionCategory = new TransactionCategory(request.name(), request.description(), parentCategory);

        return transactionCategoryRepository.save(transactionCategory);
    }

    public TransactionCategory getCategoryById(UUID id) {
        return transactionCategoryRepository.findById(id);
    }

    public List<TransactionCategory> findCategoriesByParentId(UUID parentId) {
        if (parentId == null) {
            return transactionCategoryRepository.findAllRootCategories();
        }
        return transactionCategoryRepository.findByParentId(parentId);
    }

    private TransactionCategory getParentCategory(UUID parentId) {
        if (parentId == null) {
            return null;
        }

        try {
            TransactionCategory parentCategory = transactionCategoryRepository.findById(parentId);

            if (parentCategory.getParentCategory() != null) {
                throw new TransactionCategoryException(TRANSACTION_CATEGORY_MAXIMUM_DEPTH);
            }

            return parentCategory;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(String.format(TRANSACTION_CATEGORY_PARENT_NOT_FOUND, parentId));
        }
    }
}
