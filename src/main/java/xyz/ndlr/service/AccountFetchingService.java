package xyz.ndlr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.IAuthHolder;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.IAccountRepository;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;

import java.util.List;

@Service
public class AccountFetchingService {
    private final IAccountRepository accountRepository;
    private final IAuthHolder authHolder;

    @Autowired
    public AccountFetchingService(IAccountRepository accountRepository,
                                  IAuthHolder authHolder) {
        this.accountRepository = accountRepository;
        this.authHolder = authHolder;
    }

    Account fetchByToken(String token, ProviderId providerId) {
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

    public boolean existsByToken(String token, ProviderId providerId) {
        return accountRepository.existsByToken(token, providerId);
    }
}
