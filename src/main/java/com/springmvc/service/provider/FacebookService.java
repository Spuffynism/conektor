package com.springmvc.service.provider;

import com.springmvc.service.AccountService;
import com.springmvc.service.database_util.QueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {
    private static int providerId = 1;
    private final AccountService accountService;

    @Autowired
    public FacebookService(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean userIsRegistered(String userId) {
        return accountService.existsByDetails(userId, providerId);
    }
}
