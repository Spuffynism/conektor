package xyz.ndlr.presentation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.service.AccountFetchingService;

import java.util.List;

@RestController
@RequestMapping(path = "/accounts",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountResource {
    private final AccountFetchingService accountFetchingService;

    @Autowired
    AccountResource(AccountFetchingService accountFetchingService) {
        this.accountFetchingService = accountFetchingService;
    }

    @GetMapping(Route.ME)
    public ResponseEntity<List<Account>> getMyAccounts() {
        List<Account> accounts = accountFetchingService.fetchCurrentUserAccounts();

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
