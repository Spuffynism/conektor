package xyz.ndlr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.IAccountRepository;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;

import java.util.List;

@Service
public class AccountFetchingService {
    private final IAccountRepository accountRepository;

    @Autowired
    public AccountFetchingService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

    public boolean existsByToken(String token, ProviderId providerId) {
        return accountRepository.existsByToken(token, providerId);
    }
}
