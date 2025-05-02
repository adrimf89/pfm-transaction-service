package com.amf.pfm.transaction.infrastructure.rest.transaction;

import com.amf.pfm.transaction.application.transaction.CreateTransactionRequest;
import com.amf.pfm.transaction.application.transaction.TransactionService;
import com.amf.pfm.transaction.domain.model.Transaction;
import com.amf.pfm.transaction.infrastructure.rest.transaction.request.TransactionRestRequest;
import com.amf.pfm.transaction.infrastructure.rest.transaction.response.TransactionRestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionRestResponse> createTransaction(@PathVariable UUID accountId, @RequestBody TransactionRestRequest request) {
        CreateTransactionRequest createTransactionRequest = toCreateTransactionRequest(request, accountId);
        Transaction transaction = transactionService.createTransaction(createTransactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toTransactionRestResponse(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionRestResponse> getTransactionById(@PathVariable UUID accountId, @PathVariable UUID id) {
        Transaction transaction = transactionService.getTransactionById(id, accountId);
        return ResponseEntity.ok(toTransactionRestResponse(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionRestResponse>> getAllTransactionsByAccountId(@PathVariable UUID accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions.stream()
                .map(this::toTransactionRestResponse)
                .collect(Collectors.toList()));
    }

    private CreateTransactionRequest toCreateTransactionRequest(TransactionRestRequest request, UUID accountId) {
        return new CreateTransactionRequest(request.concept(), request.amount(), request.date(), accountId);
    }

    private TransactionRestResponse toTransactionRestResponse(Transaction transaction) {
        return new TransactionRestResponse(transaction.getId(),
                transaction.getConcept(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getAccountId(),
                transaction.getStatus().toString());
    }
}
