package xyz.ndlr.domain.provider.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.dispatching.SupportedProvider;
import xyz.ndlr.domain.provider.AbstractProviderRepository;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.repository.database_util.QueryExecutor;
import xyz.ndlr.service.AccountFetchingService;

@Service
public class FacebookRepository extends AbstractProviderRepository {
    private final AccountFetchingService accountFetchingService;

    @Autowired
    public FacebookRepository(AccountFetchingService accountFetchingService) {
        super(SupportedProvider.FACEBOOK.getProviderId());
        this.accountFetchingService = accountFetchingService;
    }

    public boolean userIsRegistered(String userId) {
        return accountFetchingService.existsByToken(userId, providerId);
    }

    public User getUserByPSID(String PSID) {
        String sql = "SELECT user_usr.* FROM user_usr JOIN account_acc ON usr_id = acc_user_id " +
                "AND acc_token = :acc_token AND acc_provider_id = :provider_id";

        return new QueryExecutor<>(session -> {
            return session.createNativeQuery(sql, User.class)
                    .setParameter("acc_token", PSID)
                    .setParameter("provider_id", providerId)
                    .uniqueResult();
        }).execute();
    }
}
