package xyz.ndlr.domain.account;

import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;

import java.util.List;

public interface IAccountRepository {
    Account getByToken(String token, ProviderId providerId);

    /**
     * Gets a user's accounts.
     *
     * @param userId user's id
     * @return user's providers
     */
    List<Account> getByUserId(UserId userId);

    boolean existsByToken(String token, ProviderId providerId);
}
