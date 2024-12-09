package com.adri.pfm.transaction.controller;

import com.adri.pfm.commons.controller.PfmController;
import com.adri.pfm.commons.controller.ResponseMessage;
import com.adri.pfm.transaction.dto.transaction.CreateTransactionDTO;
import com.adri.pfm.transaction.dto.transaction.TransactionDTO;
import com.adri.pfm.transaction.dto.transaction.UpdateStatusTransactionDTO;
import com.adri.pfm.transaction.mapper.transaction.TransactionMapper;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.model.transaction.TransactionStatus;
import com.adri.pfm.transaction.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transactions")
public class TransactionController implements PfmController {

    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) {
        Transaction transaction = transactionMapper.toTransaction(createTransactionDTO);
        transaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(transactionMapper.toTransactionDTO(transaction));
    }

    @PatchMapping("/{transactionId}")
    public ResponseEntity<ResponseMessage> updateTransactionStatus(@PathVariable("transactionId") long transactionId,
                                                                   @RequestBody UpdateStatusTransactionDTO updateStatusTransactionDTO) {
        transactionService.updateTransactionStatus(transactionId, updateStatusTransactionDTO.status());
        return generateSuccessResponse(String.format("Transaction with id %s updated to status %s",
                transactionId, updateStatusTransactionDTO.status()));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> findTransaction(@PathVariable("transactionId") long transactionId,
                                                      @RequestParam(value = "accountId") long accountId,
                                                      @RequestParam("userAccountId") long userAccountId) {
        Transaction transaction = transactionService.findTransaction(transactionId, accountId, userAccountId);
        return ResponseEntity.ok(transactionMapper.toTransactionDTO(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findTransactions(@RequestParam("accountId") long accountId,
                                                                @RequestParam("userAccountId") long userAccountId,
                                                                 @RequestParam(value = "status", required = false) TransactionStatus status) {
        return ResponseEntity.ok(transactionService.findTransactions(accountId, userAccountId, status)
                .stream().map(transactionMapper::toTransactionDTO).toList());
    }
}
