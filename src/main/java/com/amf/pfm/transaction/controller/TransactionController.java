package com.amf.pfm.transaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final Map<UUID, Transaction> transactions = new HashMap<>();

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequest request) {
        UUID id = UUID.randomUUID();
        Transaction transaction = new Transaction(id, request.concept(), request.date(), request.amount());
        transactions.put(id, transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) {
        Transaction transaction = transactions.get(id);
        return transaction != null
                ? ResponseEntity.ok(transaction)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(new ArrayList<>(transactions.values()));
    }

    public record Transaction(UUID id, String concept, LocalDate date, BigDecimal amount) {}

    // We use a separate record for POST input to exclude the ID
    public record CreateTransactionRequest(String concept, LocalDate date, BigDecimal amount) {}
}
