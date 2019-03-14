package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.user.User;
import xyz.ndlr.repository.database_util.AbstractRepository;

public abstract class AbstractProviderRepository extends AbstractRepository<User> {
    protected int providerId;

    protected AbstractProviderRepository(int providerId) {
        super(User.class);
        this.providerId = providerId;
    }
}
