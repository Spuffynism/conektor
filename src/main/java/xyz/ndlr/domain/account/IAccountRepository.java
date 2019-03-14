package xyz.ndlr.domain.account;

import java.util.List;

public interface IAccountRepository {
    Account getByToken(String token, int providerId);

    /**
     * Gets a user's accounts.
     *
     * @param userId user's id
     * @return user's providers
     */
    List<Account> getByUserId(int userId);

    boolean existsByToken(String token, int providerId);
}
