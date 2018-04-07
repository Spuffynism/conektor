package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.model.entity.Account;
import xyz.ndlr.service.database_util.AbstractService;
import xyz.ndlr.service.database_util.QueryExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class AccountService extends AbstractService<Account> {
    public AccountService() {
        super(Account.class);
    }

    public Account getByToken(String token, int providerId) {
        return new QueryExecutor<>(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
            Root<Account> root = criteria.from(Account.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("token"), token));
            criteria.where(builder.equal(root.get("providerId"), providerId));

            return session.createQuery(criteria).uniqueResult();
        }).execute();
    }

    /**
     * Gets a user's accounts.
     *
     * @param userId user's id
     * @return user's providers
     */
    public List<Account> getByUserId(int userId) {
        return new QueryExecutor<>(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
            Root<Account> root = criteria.from(Account.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("userId"), userId));

            return session.createQuery(criteria).getResultList();
        }).execute();
    }

    public boolean existsByToken(String token, int providerId) {
        return new QueryExecutor<>(session -> getByToken(token, providerId) != null).execute();
    }
}
