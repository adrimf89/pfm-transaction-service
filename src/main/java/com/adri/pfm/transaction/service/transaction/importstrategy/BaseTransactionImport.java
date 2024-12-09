package com.adri.pfm.transaction.service.transaction.importstrategy;

import com.adri.pfm.transaction.exception.TransactionImportException;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseTransactionImport {

    protected static final String LOG_HEADER = "IMPORT PROCESS: {}";
    protected static final String LOG_HEADER_ERROR = "IMPORT PROCESS ERROR: {}";
    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    protected final TransactionService transactionService;

    protected void processFile(MultipartFile file) {
        try {
            List<Transaction> transactionsToImport = getTransactionsFromFile(file.getInputStream());
            saveTransactions(transactionsToImport);
        } catch (IOException e) {
            String message = "Error parsing file.";
            logError(message, e);
            throw new TransactionImportException(message);
        } catch (Exception e) {
            String message = "Error importing file.";
            logError(message, e);
            throw new TransactionImportException(message);
        }
    }

    protected void saveTransactions(List<Transaction> importedTransactions) {
        logInfo("Starting to persist imported transactions from file");
        importedTransactions.forEach(transactionService::createTransaction);
        logInfo(String.format("%s transactions imported successfully", importedTransactions.size()));
    }

    protected void logInfo(String message) {
        log.info(LOG_HEADER, message);
    }

    protected void logError(String message, Exception e) {
        log.error(LOG_HEADER_ERROR, message, e);
    }

    protected Date parseIsoDate(String strDate) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat(ISO_DATE_FORMAT);
        return parser.parse(strDate);
    }

    protected abstract List<Transaction> getTransactionsFromFile(InputStream is) throws IOException, ParseException;
}
