package com.adri.pfm.transaction.controller;

import com.adri.pfm.commons.controller.PfmController;
import com.adri.pfm.transaction.dto.account.AccountDTO;
import com.adri.pfm.transaction.dto.account.CreateAccountDTO;
import com.adri.pfm.transaction.mapper.account.AccountMapper;
import com.adri.pfm.transaction.model.account.Account;
import com.adri.pfm.transaction.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountController implements PfmController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        Account account = accountMapper.toAccount(createAccountDTO);
        account = accountService.createAccount(account);
        return ResponseEntity.ok(accountMapper.toAccountDTO(account));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> findById(@PathVariable("accountId") long accountId) {
        Account account = accountService.findByAccountId(accountId);
        return ResponseEntity.ok(accountMapper.toAccountDTO(account));
    }

}
