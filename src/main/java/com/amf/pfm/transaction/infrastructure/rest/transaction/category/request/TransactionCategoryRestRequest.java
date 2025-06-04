package com.amf.pfm.transaction.infrastructure.rest.transaction.category.request;

import java.util.UUID;

public record TransactionCategoryRestRequest(String name, String description, UUID parentId) {
}
