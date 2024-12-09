package com.adri.pfm.transaction.service.transaction.importstrategy;

import com.adri.pfm.transaction.model.account.Account;
import com.adri.pfm.transaction.model.transaction.Transaction;
import com.adri.pfm.transaction.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(ImportFileType.CSV)
public class TransactionCsvImport extends BaseTransactionImport implements TransactionImportStrategy {

    public TransactionCsvImport(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    @Transactional
    public void importFile(MultipartFile file) {
        logInfo("CSV file received to import Transactions");
        processFile(file);
    }

    protected List<Transaction> getTransactionsFromFile(InputStream is) throws IOException, ParseException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        List<Transaction> transactions = new ArrayList<>();
        for (CSVRecord csvRecord : csvParser.getRecords()) {
            Transaction transaction = new Transaction();
            transaction.setConcept(csvRecord.get("Concept"));
            transaction.setDate(parseIsoDate(csvRecord.get("Date")));
            transaction.setAmount(new BigDecimal(csvRecord.get("Amount")));

            Account account = new Account();
            account.setAccountId(Long.parseLong(csvRecord.get("AccountId")));
            transaction.setAccount(account);

            transactions.add(transaction);
        }

        return transactions;
    }
}
