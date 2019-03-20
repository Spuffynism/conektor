package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.user.User;
import xyz.ndlr.repository.database_util.AbstractRepository;

public abstract class AbstractProviderRepository extends AbstractRepository<User> {
    protected ProviderId providerId;

    protected AbstractProviderRepository(ProviderId providerId) {
        super(User.class);
        this.providerId = providerId;
    }
}
