package com.springmvc.service;

import com.springmvc.exception.InvalidPasswordException;
import com.springmvc.model.User;
import com.springmvc.security.auth.NewPassword;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeService {

    public void tryChangePassword(User user, NewPassword newPassword)
            throws InvalidPasswordException {
        int userId = user.getId();

        addPasswordChangeAttempt(userId);
        changePassword(user, newPassword);
        resetPasswordChangeAttempts(userId);
    }

    private void changePassword(User currentUser, NewPassword newPassword)
            throws InvalidPasswordException {
        IPasswordHasher argon2 = new Argon2Hasher();

        // Checks that we haven't exceeded the maximum allowed password change attempts
        if (currentUser.getAttemptedPasswordChanges() > NewPassword
                .MAX_ALLOWED_PASSWORD_CHANGE_ATTEMPS)
            throw new InvalidPasswordException("The maximum allowed password change attempts has " +
                    "been reached.");

        // Checks that the new password complies to our security requirements
        if (!newPassword.complies())
            throw new InvalidPasswordException("New password is invalid.");

        // Checks that the user knows their password (protection against session token theft)
        boolean currentPasswordIsRight = argon2.verify(currentUser.getPassword(),
                newPassword.getCurrentPassword());
        if (!currentPasswordIsRight)
            throw new InvalidPasswordException("The current password is not right.");

        // If all the checks pass, we change the password
        updatePassword(currentUser.getId(), argon2.hash(newPassword.getNewPassword()));
    }

    private void updatePassword(int userId, String newPasswordHash) {
        new QueryExecutor<Void>(session -> {
            Query query = session.createQuery("update User set password = :newPasswordHash where " +
                    "id = :userId")
                    .setParameter("newPasswordHash", newPasswordHash)
                    .setParameter("userId", userId);

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    private void addPasswordChangeAttempt(int userId) {
        new QueryExecutor<Void>(session -> {
            String sql = "update User set attemptedPasswordChanges = attemptedPasswordChanges + 1" +
                    " where id = :userId";
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId);

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    private void resetPasswordChangeAttempts(int userId) {
        new QueryExecutor<Void>(session -> {
            String sql = "update User set attemptedPasswordChanges = 0 where id = :userId";
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId);

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }
}