package xyz.ndlr.model.provider.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.model.dispatching.SupportedProvider;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.AbstractProviderService;
import xyz.ndlr.service.AccountService;
import xyz.ndlr.service.database_util.QueryExecutor;

@Service
public class FacebookService extends AbstractProviderService {
    private final AccountService accountService;

    @Autowired
    public FacebookService(AccountService accountService) {
        super(SupportedProvider.FACEBOOK.getProviderId());
        this.accountService = accountService;
    }

    public boolean userIsRegistered(String userId) {
        return accountService.existsByDetails(userId, providerId);
    }

    public User getUserByPSID(String psid) {
        String sql = "SELECT user_usr.* FROM user_usr JOIN account_acc ON usr_id = acc_user_id " +
                "AND acc_details = :acc_details AND acc_provider_id = :provider_id";

        return new QueryExecutor<>(session -> {
            return session.createNativeQuery(sql, User.class)
                    .setParameter("acc_token", psid)
                    .setParameter("provider_id", providerId)
                    .uniqueResult();
        }).execute();
    }
}
