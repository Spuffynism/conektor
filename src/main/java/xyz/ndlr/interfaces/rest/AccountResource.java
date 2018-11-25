package xyz.ndlr.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.domain.entity.Account;
import xyz.ndlr.service.AccountService;

import java.util.List;

@RestController
@RequestMapping(path = "/accounts",
        consumes = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_UTF8_VALUE},
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountResource {
    private final AccountService accountService;

    @Autowired
    AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(Route.ME)
    public ResponseEntity<List<Account>> getMyAccounts() {
        //int userId = authHolder.getUser().getId();
        List<Account> accounts = accountService.getByUserId(1);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
