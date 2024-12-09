package com.adri.pfm.transaction.mapper.transaction;

import com.adri.pfm.commons.jms.message.TransactionMessage;
import com.adri.pfm.transaction.dto.transaction.CreateTransactionDTO;
import com.adri.pfm.transaction.dto.transaction.TransactionDTO;
import com.adri.pfm.transaction.model.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toTransaction(CreateTransactionDTO createTransactionDTO);
    @Mapping(source = "currentStatus", target = "status")
    @Mapping(source = "account.accountId", target = "accountId")
    TransactionDTO toTransactionDTO(Transaction transaction);

    @Mapping(source = "currentStatus", target = "status")
    @Mapping(source = "account.accountId", target = "accountId")
    TransactionMessage toTransactionMessage(Transaction transaction);
}
