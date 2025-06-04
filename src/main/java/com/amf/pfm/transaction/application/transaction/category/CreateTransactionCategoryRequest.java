package com.amf.pfm.transaction.application.transaction.category;

import java.util.UUID;

public record CreateTransactionCategoryRequest(String name, String description, UUID parentId) {
}
