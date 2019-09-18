package xyz.ndlr.service;

import xyz.ndlr.domain.account.AccountToken;
import xyz.ndlr.domain.provider.ProviderId;

public class AccountFetchingRequest {
    private AccountToken accountToken;
    private ProviderId providerId;

    public AccountFetchingRequest(AccountToken accountToken,
                                  ProviderId providerId) {
        this.accountToken = accountToken;
        this.providerId = providerId;
    }

    public AccountToken getAccountToken() {
        return this.accountToken;
    }

    public ProviderId getProviderId() {
        return this.providerId;
    }
}
