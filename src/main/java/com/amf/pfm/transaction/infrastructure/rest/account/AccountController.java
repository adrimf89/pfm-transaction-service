package com.amf.pfm.transaction.infrastructure.rest.account;

import com.amf.pfm.transaction.application.account.AccountService;
import com.amf.pfm.transaction.application.account.CreateAccountRequest;
import com.amf.pfm.transaction.domain.model.Account;
import com.amf.pfm.transaction.infrastructure.rest.account.request.AccountRestRequest;
import com.amf.pfm.transaction.infrastructure.rest.account.response.AccountRestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountRestResponse> createAccount(@RequestBody AccountRestRequest request) {
        CreateAccountRequest createAccountRequest = toCreateAccountRequest(request);
        Account account = accountService.createAccount(createAccountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAccountRestResponse(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountRestResponse> getAccountById(@PathVariable UUID id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(toAccountRestResponse(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountRestResponse>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts.stream()
                .map(this::toAccountRestResponse)
                .collect(Collectors.toList()));
    }

    private CreateAccountRequest toCreateAccountRequest(AccountRestRequest request) {
        return new CreateAccountRequest(request.iban(), request.initialBalance());
    }

    private AccountRestResponse toAccountRestResponse(Account account) {
        return new AccountRestResponse(account.getId(), account.getIban(), account.getBalance(), account.getUpdatedAt());
    }
}
