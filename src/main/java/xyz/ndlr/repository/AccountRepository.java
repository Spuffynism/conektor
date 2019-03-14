package xyz.ndlr.repository;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.account.IAccountRepository;
import xyz.ndlr.repository.database_util.AbstractRepository;
import xyz.ndlr.repository.database_util.QueryExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class AccountRepository extends AbstractRepository<Account> implements IAccountRepository {
    protected AccountRepository() {
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
            criteria.where(builder.equal(root.get("UserId"), userId));

            return session.createQuery(criteria).getResultList();
        }).execute();
    }

    public boolean existsByToken(String token, int providerId) {
        return new QueryExecutor<>(session -> getByToken(token, providerId) != null).execute();
    }
}
