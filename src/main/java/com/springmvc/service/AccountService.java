package com.springmvc.service;

import com.springmvc.model.Account;
import com.springmvc.service.database_util.AbstractService;
import org.springframework.stereotype.Component;

@Component
public class AccountService extends AbstractService<Account> {
    public AccountService() {
        super(Account.class);
    }
}
