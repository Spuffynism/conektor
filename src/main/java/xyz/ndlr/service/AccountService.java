package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.model.entity.Account;
import xyz.ndlr.service.database_util.AbstractService;
import xyz.ndlr.service.database_util.QueryExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class AccountService extends AbstractService<Account> {
    public AccountService() {
        super(Account.class);
    }

    public Account getByDetails(String details, int providerId) {
        return new QueryExecutor<>(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
            Root<Account> root = criteria.from(Account.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("details"), details));
            criteria.where(builder.equal(root.get("providerId"), providerId));

            return session.createQuery(criteria).uniqueResult();
        }).execute();
    }

    public boolean existsByDetails(String details, int providerId) {
        return new QueryExecutor<>(session -> getByDetails(details, providerId) != null).execute();
    }
}
