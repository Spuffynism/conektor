package xyz.ndlr.repository;

import org.springframework.stereotype.Repository;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.IAccountRepository;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.repository.database_util.AbstractRepository;
import xyz.ndlr.repository.database_util.QueryExecutor;

import java.util.List;

@Repository
public class AccountRepository extends AbstractRepository<Account> implements IAccountRepository {
    protected AccountRepository() {
        super(Account.class);
    }

    public Account getByToken(String token, ProviderId providerId) {
        return findOne((builder, root) ->
                        builder.equal(root.get("token"), token),
                (builder, root) -> builder.equal(
                        root.get("providerId"), providerId.getValue())
        );
    }

    /**
     * Gets a user's accounts.
     *
     * @param userId user's id
     * @return user's providers
     */
    public List<Account> getByUserId(UserId userId) {
        return findMany((builder, root) ->
                builder.equal(root.get("userId"), userId.getValue()));
    }

    public boolean existsByToken(String token, ProviderId providerId) {
        return new QueryExecutor<>(session -> getByToken(token, providerId) != null).execute();
    }
}
