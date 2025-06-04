package com.amf.pfm.transaction.infrastructure.rest.transaction.category;

import com.amf.pfm.transaction.application.transaction.category.CreateTransactionCategoryRequest;
import com.amf.pfm.transaction.application.transaction.category.TransactionCategoryService;
import com.amf.pfm.transaction.domain.model.TransactionCategory;
import com.amf.pfm.transaction.infrastructure.rest.transaction.category.request.TransactionCategoryRestRequest;
import com.amf.pfm.transaction.infrastructure.rest.transaction.category.response.TransactionCategoryRestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction-categories")
public class TransactionCategoryController {

    private final TransactionCategoryService transactionCategoryService;

    public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @PostMapping
    public ResponseEntity<TransactionCategoryRestResponse> createCategory(@RequestBody TransactionCategoryRestRequest request) {
        CreateTransactionCategoryRequest createTransactionCategoryRequest = toCreateTransactionCategoryRequest(request);
        TransactionCategory category = transactionCategoryService.createCategory(createTransactionCategoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toTransactionCategoryRestResponse(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCategoryRestResponse> getCategoryById(@PathVariable UUID id) {
        TransactionCategory category = transactionCategoryService.getCategoryById(id);
        return ResponseEntity.ok(toTransactionCategoryRestResponse(category));
    }

    @GetMapping
    public ResponseEntity<List<TransactionCategoryRestResponse>> getCategories(@RequestParam(required = false) UUID parentId) {
        List<TransactionCategory> categories = transactionCategoryService.findCategoriesByParentId(parentId);
        return ResponseEntity.ok(categories.stream()
                .map(this::toTransactionCategoryRestResponse)
                .toList());
    }

    private CreateTransactionCategoryRequest toCreateTransactionCategoryRequest(TransactionCategoryRestRequest request) {
        return new CreateTransactionCategoryRequest(request.name(), request.description(), request.parentId());
    }

    private TransactionCategoryRestResponse toTransactionCategoryRestResponse(TransactionCategory transactionCategory) {
        return new TransactionCategoryRestResponse(
                transactionCategory.getId(),
                transactionCategory.getName(),
                transactionCategory.getDescription(),
                transactionCategory.getParentCategory() != null ? toTransactionCategoryRestResponse(transactionCategory.getParentCategory()) : null
        );
    }
}
