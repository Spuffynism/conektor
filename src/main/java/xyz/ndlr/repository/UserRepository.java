package xyz.ndlr.repository;

import org.springframework.stereotype.Repository;
import xyz.ndlr.domain.Email;
import xyz.ndlr.domain.user.IUserRepository;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.domain.user.Username;
import xyz.ndlr.repository.database_util.AbstractRepository;
import xyz.ndlr.repository.database_util.QueryExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserRepository extends AbstractRepository<User> implements IUserRepository {

    public UserRepository() {
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
