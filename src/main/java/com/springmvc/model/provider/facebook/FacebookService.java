package com.springmvc.model.provider.facebook;

import com.springmvc.model.dispatching.SupportedProvider;
import com.springmvc.service.AccountService;
import com.springmvc.model.provider.AbstractProviderService;
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
