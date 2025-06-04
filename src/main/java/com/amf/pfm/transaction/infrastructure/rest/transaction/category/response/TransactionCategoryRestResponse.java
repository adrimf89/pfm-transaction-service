package com.amf.pfm.transaction.infrastructure.rest.transaction.category.response;

import java.util.UUID;

public record TransactionCategoryRestResponse(UUID id, String name, String description, TransactionCategoryRestResponse parentCategory) {
}
