package xyz.ndlr.repository;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.IPasswordChangeRepository;
import xyz.ndlr.repository.database_util.QueryExecutor;

@Component
public class PasswordChangeRepository implements IPasswordChangeRepository {

    @Override
    public void updatePassword(int userId, String newPasswordHash) {
        String sql = "update User set password = :newPasswordHash where id = :userId";
        new QueryExecutor<Void>(session -> {
            Query query = session.createQuery(sql)
                    .setParameter("newPasswordHash", newPasswordHash)
                    .setParameter("userId", userId);

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    @Override
    public void addPasswordChangeAttempt(int userId) {
        String sql = "update User set attemptedPasswordChanges = attemptedPasswordChanges + 1" +
                " where id = :UserId";

        executeForUser(userId, sql);
    }

    @Override
    public void resetPasswordChangeAttempts(int userId) {
        String sql = "update User set attemptedPasswordChanges = 0 where id = :UserId";

        executeForUser(userId, sql);
    }

    private void executeForUser(int userId, String sql) {
        new QueryExecutor<Void>(session -> {
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId);

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }
}
