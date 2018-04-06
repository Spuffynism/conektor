package xyz.ndlr.model.provider;

import xyz.ndlr.model.entity.User;
import xyz.ndlr.service.database_util.AbstractService;

public abstract class AbstractProviderService extends AbstractService<User> {
    protected int providerId;

    protected AbstractProviderService(int providerId) {
        super(User.class);
        this.providerId = providerId;
    }
}
