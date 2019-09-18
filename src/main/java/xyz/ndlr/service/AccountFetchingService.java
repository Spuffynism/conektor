package xyz.ndlr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.AuthenticationHolder;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.AccountRepository;
import xyz.ndlr.domain.account.AccountToken;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;

import java.util.List;

@Service
public class AccountFetchingService {
    private final AccountRepository accountRepository;
    private final AuthenticationHolder authHolder;

    @Autowired
    public AccountFetchingService(AccountRepository accountRepository,
                                  AuthenticationHolder authHolder) {
        this.accountRepository = accountRepository;
        this.authHolder = authHolder;
    }

    Account fetchByToken(AccountFetchingRequest accountFetchingRequest) {
        AccountToken token = accountFetchingRequest.getAccountToken();
        ProviderId providerId = accountFetchingRequest.getProviderId();

        return accountRepository.getByToken(token, providerId);
    }

    /**
     * Gets a user's accounts.
     *
     * @param userId user's id
     * @return user's providers
     */
    public List<Account> fetchByUserId(UserId userId) {
        return accountRepository.getByUserId(userId);
    }

    public List<Account> fetchCurrentUserAccounts() {
        UserId userId = authHolder.getUser().getId();

        return this.fetchByUserId(userId);
    }

    public boolean existsByToken(AccountFetchingRequest accountFetchingRequest) {
        AccountToken token = accountFetchingRequest.getAccountToken();
        ProviderId providerId = accountFetchingRequest.getProviderId();

        return accountRepository.existsByToken(token, providerId);
    }
}
