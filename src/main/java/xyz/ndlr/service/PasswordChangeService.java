package xyz.ndlr.service;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.exception.password.NonCompliantPasswordException;
import xyz.ndlr.domain.exception.password.NonMatchingPasswordConfirmationException;
import xyz.ndlr.domain.exception.password.PasswordChangeAttemptCountReachedException;
import xyz.ndlr.repository.database_util.QueryExecutor;
import xyz.ndlr.security.auth.PasswordChange;
import xyz.ndlr.security.hashing.Argon2Hasher;
import xyz.ndlr.security.hashing.IPasswordHasher;

@Service
public class PasswordChangeService {

    public void tryChangePassword(User user, PasswordChange passwordChange)
            throws NonCompliantPasswordException,
            PasswordChangeAttemptCountReachedException, NonMatchingPasswordConfirmationException {
        int userId = user.getId();

        addPasswordChangeAttempt(userId);
        changePassword(user, passwordChange);
        resetPasswordChangeAttempts(userId);
    }

    private void changePassword(User currentUser, PasswordChange passwordChange)
            throws PasswordChangeAttemptCountReachedException,
            NonCompliantPasswordException, NonMatchingPasswordConfirmationException {
        // Checks that we haven't exceeded the maximum allowed password change attempts
        if (currentUser.getAttemptedPasswordChanges() > PasswordChange
                .MAX_ALLOWED_PASSWORD_CHANGE_ATTEMPTS)
            throw new PasswordChangeAttemptCountReachedException();

        // Checks that the new password complies to our security requirements
        if (!passwordChange.complies())
            throw new NonCompliantPasswordException();

        IPasswordHasher argon2 = new Argon2Hasher();
        // Checks that the user knows their password (protection against session token theft)
        boolean currentPasswordIsRight = argon2.verify(currentUser.getPassword(),
                passwordChange.getCurrentPassword());
        if (!currentPasswordIsRight)
            throw new NonMatchingPasswordConfirmationException();

        // If all the checks pass, we change the password
        updatePassword(currentUser.getId(), argon2.hash(passwordChange.getNewPassword()));
    }

    private void updatePassword(int userId, String newPasswordHash) {
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

    private void addPasswordChangeAttempt(int userId) {
        String sql = "update User set attemptedPasswordChanges = attemptedPasswordChanges + 1" +
                " where id = :userId";

        executeForUser(userId, sql);
    }

    private void resetPasswordChangeAttempts(int userId) {
        String sql = "update User set attemptedPasswordChanges = 0 where id = :userId";

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
