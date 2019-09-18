package xyz.ndlr.infrastructure.provider.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.account.AccountToken;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.dispatching.SupportedProvider;
import xyz.ndlr.infrastructure.persistence.AbstractQueryExecutorProviderRepository;
import xyz.ndlr.infrastructure.persistence.database_util.QueryExecutor;
import xyz.ndlr.service.AccountFetchingRequest;
import xyz.ndlr.service.AccountFetchingService;

@Service
// TODO(nich): This service should use a FacebookUserRepository which implements
// AbstractQueryExecutorProviderRepository
public class FacebookUserService extends AbstractQueryExecutorProviderRepository {
    private final AccountFetchingService accountFetchingService;

    @Autowired
    public FacebookUserService(AccountFetchingService accountFetchingService) {
        super(SupportedProvider.FACEBOOK.getProviderId());
        this.accountFetchingService = accountFetchingService;
    }

    public boolean userIsRegistered(String userId) {
        //TODO(nich): Remove Repo -> Service usage
        // TODO(nich): Remove confusion between userId and token
        //return accountFetchingService.existsByToken(userId, providerId);
        return accountFetchingService
                .existsByToken(new AccountFetchingRequest(AccountToken.from(userId), providerId));
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
