package xyz.ndlr.repository;

import org.hibernate.Query;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import xyz.ndlr.domain.IPasswordChangeRepository;
import xyz.ndlr.domain.password.HashedPassword;
import xyz.ndlr.domain.password.Password;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.repository.database_util.QueryExecutor;

import java.math.BigInteger;

@Repository
public class PasswordChangeRepository implements IPasswordChangeRepository {

    @Override
    public void updatePassword(UserId userId, HashedPassword hashedPassword) {
        String sql = "update User set password = :newPasswordHash where id = :userId";

        new QueryExecutor<Void>(session -> {
            Query query = session.createQuery(sql)
                    .setParameter("newPasswordHash", hashedPassword.getValue())
                    .setParameter("userId", userId.getValue());

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    @Override
    public int getUserPasswordChangeAttempts(UserId userId) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void addPasswordChangeAttempt(UserId userId) {
        String sql = "update User set attemptedPasswordChanges = attemptedPasswordChanges + 1" +
                " where id = :UserId";

        executeForUser(userId, sql);
    }

    @Override
    public void resetPasswordChangeAttempts(UserId userId) {
        String sql = "update User set attemptedPasswordChanges = 0 where id = :UserId";

        executeForUser(userId, sql);
    }

    private void executeForUser(UserId userId, String sql) {
        new QueryExecutor<Void>(session -> {
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId.getValue());

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    @Override
    public boolean isCommonPassword(Password password) {
        String sql = "SELECT COUNT(*) FROM common_passwords_cpa WHERE cpa_password = " +
                ":password LIMIT 1";

        return new QueryExecutor<>(session -> {
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("password", password.getValue());

            // if we get more than 0 results, the password's common
            return ((BigInteger) query.uniqueResult()).longValue() > 0;
        }).execute();
    }
}
