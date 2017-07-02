package com.springmvc.service.provider;

import com.springmvc.model.entity.User;
import com.springmvc.service.AccountService;
import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacebookService {
    private static int providerId = 1;
    private final AccountService accountService;

    @Autowired
    public FacebookService(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean userIsRegistered(String userId) {
        return accountService.existsByDetails(userId, providerId);
    }

    /**
     * Put this in an AbstractProviderService - which has a getByUniquerProviderIdentifier(?) or
     * getByUserAccountDetailsIdAndProviderId method.
     *
     * @param senderId
     * @return
     */
    public User getBySenderId(String senderId) {
        return new QueryExecutor<>(session -> {
            String sql = "SELECT user_usr.* FROM user_usr JOIN account_acc ON usr_id = " +
                    "acc_user_id AND acc_details = :acc_details AND acc_provider_id = :provider_id";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(User.class);
            query.setParameter("acc_details", senderId);
            query.setParameter("provider_id", providerId);

            return (User) query.uniqueResult();
        }).execute();
    }
}
