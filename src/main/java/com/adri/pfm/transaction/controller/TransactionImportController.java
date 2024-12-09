package com.adri.pfm.transaction.controller;

import com.adri.pfm.commons.controller.PfmController;
import com.adri.pfm.commons.controller.ResponseMessage;
import com.adri.pfm.transaction.service.transaction.TransactionImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transactions-import")
public class TransactionImportController implements PfmController {

    private final TransactionImportService transactionImportService;

    @PostMapping
    public ResponseEntity<ResponseMessage> importTransactions(@RequestParam("file") MultipartFile file) {
        log.info("New file received to import '{}' with type '{}'", file.getName(), file.getContentType());
        transactionImportService.importFile(file);
        return generateSuccessResponse("File imported succesfully");
    }
}
