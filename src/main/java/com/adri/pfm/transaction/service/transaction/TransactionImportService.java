package com.adri.pfm.transaction.service.transaction;

import com.adri.pfm.transaction.service.transaction.importstrategy.ImportFileType;
import com.adri.pfm.transaction.service.transaction.importstrategy.TransactionImportStrategy;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionImportService {

    private final Map<String, TransactionImportStrategy> importStrategies;

    public void importFile(MultipartFile file) {
        Preconditions.checkArgument(nonNull(file), "File cannot be null");
        Preconditions.checkArgument(isNotEmpty(file.getContentType()), "File content type cannot be null");

        String fileType = getFileType(file.getContentType());
        importStrategies.get(fileType).importFile(file);
    }

    private String getFileType(String fileContentType) {
        return switch (fileContentType) {
            case "text/csv" -> ImportFileType.CSV;
            case "text/xlsx" -> ImportFileType.XLSX;
            default -> null;
        };
    }
}
