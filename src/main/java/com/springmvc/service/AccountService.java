package com.springmvc.service;

import com.springmvc.model.entity.Account;
import com.springmvc.service.database_util.AbstractService;
import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends AbstractService<Account> {
    public AccountService() {
        super(Account.class);
    }

    public Account getByDetails(String details, int providerId) {
        return new QueryExecutor<>(session -> {
            return (Account) session.createCriteria(Account.class)
                    .add(Restrictions.eq("details", details))
                    .add(Restrictions.eq("providerId", providerId))
                    .uniqueResult();
        }).execute();
    }

    public boolean existsByDetails(String details, int providerId) {
        return new QueryExecutor<>(session -> getByDetails(details, providerId) != null).execute();
    }
}
