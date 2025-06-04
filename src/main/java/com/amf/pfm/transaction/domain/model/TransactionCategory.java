package com.amf.pfm.transaction.domain.model;

import com.amf.pfm.transaction.domain.exception.TransactionCategoryException;
import io.micrometer.common.util.StringUtils;

import java.util.UUID;

import static com.amf.pfm.transaction.domain.exception.Messages.TRANSACTION_CATEGORY_WITH_INVALID_DESCRIPTION;
import static com.amf.pfm.transaction.domain.exception.Messages.TRANSACTION_CATEGORY_WITH_INVALID_NAME;

public class TransactionCategory extends Traceable {

    private final UUID id;
    private final String name;
    private final String description;
    private final TransactionCategory parentCategory;

    public TransactionCategory(String name, String description, TransactionCategory parentCategory) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        validateTransactionCategory();
    }

    public TransactionCategory(UUID id, String name, String description, TransactionCategory parentCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        validateTransactionCategory();
    }

    private void validateTransactionCategory() {
        if (StringUtils.isBlank(name)) {
            throw new TransactionCategoryException(TRANSACTION_CATEGORY_WITH_INVALID_NAME);
        }
        if (StringUtils.isBlank(description)) {
            throw new TransactionCategoryException(TRANSACTION_CATEGORY_WITH_INVALID_DESCRIPTION);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TransactionCategory getParentCategory() {
        return parentCategory;
    }
}
