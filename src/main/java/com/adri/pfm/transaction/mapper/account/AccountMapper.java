package com.adri.pfm.transaction.mapper.account;

import com.adri.pfm.transaction.dto.account.AccountDTO;
import com.adri.pfm.transaction.dto.account.CreateAccountDTO;
import com.adri.pfm.transaction.model.account.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(CreateAccountDTO createAccountDTO);
    AccountDTO toAccountDTO(Account account);
}
