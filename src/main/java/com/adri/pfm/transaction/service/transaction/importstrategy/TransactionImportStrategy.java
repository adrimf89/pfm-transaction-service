package com.adri.pfm.transaction.service.transaction.importstrategy;

import org.springframework.web.multipart.MultipartFile;

public interface TransactionImportStrategy {

    void importFile(MultipartFile file);
}
