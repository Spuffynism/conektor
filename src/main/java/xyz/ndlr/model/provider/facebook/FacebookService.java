package xyz.ndlr.model.provider.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.model.dispatching.SupportedProvider;
import xyz.ndlr.model.provider.AbstractProviderService;
import xyz.ndlr.service.AccountService;

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
