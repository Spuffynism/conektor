package xyz.ndlr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.model.entity.Account;
import xyz.ndlr.service.AccountService;
import xyz.ndlr.service.AuthHolder;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController extends DefaultController {
    private final AccountService accountService;

    @Autowired
    AccountController(AuthHolder authHolder,
                      AccountService accountService) {
        super(authHolder);
        this.accountService = accountService;
    }

    @GetMapping(Route.ME)
    public ResponseEntity<List<Account>> getMyAccounts() {
        //int userId = authHolder.getUser().getId();
        List<Account> accounts = accountService.getByUserId(1);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
