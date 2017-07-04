package com.springmvc.service.provider;

import com.springmvc.model.SupportedProvider;
import com.springmvc.model.entity.User;
import com.springmvc.service.AccountService;
import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacebookService extends AbstractProviderService {
    private final AccountService accountService;

    @Autowired
    public FacebookService(AccountService accountService) {
        super(SupportedProvider.FACEBOOK.getProviderId());
        this.accountService = accountService;
    }

    public boolean userIsRegistered(String userId) {
        return accountService.existsByDetails(userId, providerId);
    }
}
