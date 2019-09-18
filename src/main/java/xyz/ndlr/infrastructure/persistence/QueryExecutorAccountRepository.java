package xyz.ndlr.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.AccountRepository;
import xyz.ndlr.domain.account.AccountToken;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.infrastructure.persistence.database_util.AbstractQueryExecutorRepository;

import java.util.List;

@Repository
public class QueryExecutorAccountRepository extends AbstractQueryExecutorRepository<Account>
        implements AccountRepository {
    protected QueryExecutorAccountRepository() {
        super(Account.class);
    }

    public Account getByToken(AccountToken token, ProviderId providerId) {
        return findOne((builder, root) ->
                        builder.equal(root.get("token"), token.getValue()),
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

    public boolean existsByToken(AccountToken token, ProviderId providerId) {
        return getByToken(token, providerId) != null;
    }
}
