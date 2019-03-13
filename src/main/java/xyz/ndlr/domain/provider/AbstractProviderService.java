package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.entity.User;
import xyz.ndlr.repository.database_util.AbstractService;

public abstract class AbstractProviderService extends AbstractService<User> {
    protected int providerId;

    protected AbstractProviderService(int providerId) {
        super(User.class);
        this.providerId = providerId;
    }
}
