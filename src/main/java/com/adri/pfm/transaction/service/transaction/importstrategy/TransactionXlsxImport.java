package com.adri.pfm.transaction.service.transaction.importstrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service(ImportFileType.XLSX)
@RequiredArgsConstructor
public class TransactionXlsxImport implements TransactionImportStrategy {
    @Override
    public void importFile(MultipartFile file) {
        log.info("Not implemented yet");
    }
}
