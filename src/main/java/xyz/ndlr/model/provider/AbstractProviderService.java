package xyz.ndlr.model.provider;

import xyz.ndlr.model.entity.User;
import xyz.ndlr.service.database_util.AbstractService;
import xyz.ndlr.service.database_util.QueryExecutor;

public abstract class AbstractProviderService extends AbstractService<User> {
    protected int providerId;

    protected AbstractProviderService(int providerId) {
        super(User.class);
        this.providerId = providerId;
    }

    public User getUserByIdentifier(String identifier) {
        String sql = "SELECT user_usr.* FROM user_usr JOIN account_acc ON usr_id = acc_user_id " +
                "AND acc_details = :acc_details AND acc_provider_id = :provider_id";

        return new QueryExecutor<>(session -> {
            return session.createNativeQuery(sql, User.class)
                    .addEntity(User.class)
                    .setParameter("acc_details", identifier)
                    .setParameter("provider_id", providerId)
                    .uniqueResult();
        }).execute();
    }
}
