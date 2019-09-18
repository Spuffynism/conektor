package xyz.ndlr.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import xyz.ndlr.domain.user.*;
import xyz.ndlr.infrastructure.persistence.database_util.AbstractQueryExecutorRepository;
import xyz.ndlr.infrastructure.persistence.database_util.QueryExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class QueryExecutorUserRepository extends AbstractQueryExecutorRepository<User>
        implements UserRepository {

    public QueryExecutorUserRepository() {
        super(User.class);
    }

    public User get(Username username) {
        return new QueryExecutor<>(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("username"), username.getValue()));

            return session.createQuery(criteria).getSingleResult();
        }).execute();
    }

    @Override
    public User get(Email email) {
        return new QueryExecutor<>(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("email"), email));

            return session.createQuery(criteria).uniqueResult();
        }).execute();
    }

    @Override
    public User get(UserId userId) {
        return this.get(userId.getValue());
    }

    @Override
    public void delete(UserId userId) {
        this.delete(userId.getValue());
    }

    @Override
    public Boolean exists(UserId userId) {
        return this.exists(userId.getValue());
    }
}
