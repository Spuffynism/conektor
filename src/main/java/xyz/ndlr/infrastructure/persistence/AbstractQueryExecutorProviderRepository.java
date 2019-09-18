package xyz.ndlr.infrastructure.persistence;

import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.persistence.database_util.AbstractQueryExecutorRepository;

public abstract class AbstractQueryExecutorProviderRepository
        extends AbstractQueryExecutorRepository<User> {
    protected ProviderId providerId;

    protected AbstractQueryExecutorProviderRepository(ProviderId providerId) {
        super(User.class);
        this.providerId = providerId;
    }
}
