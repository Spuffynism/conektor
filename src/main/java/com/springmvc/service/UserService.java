package com.springmvc.service;

import com.springmvc.model.User;
import com.springmvc.security.auth.NewPassword;
import com.springmvc.security.auth.exception.InvalidPasswordException;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
import com.springmvc.service.database_util.AbstractService;
import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserService extends AbstractService<User> implements UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker
            = new AccountStatusUserDetailsChecker();

    public UserService() {
        super(User.class);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        detailsChecker.check(user);
        return user;
    }

    private User getByUsername(String username) {
        return new QueryExecutor<>(session -> {
            return (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("username", username))
                    .uniqueResult();
        }).execute();
    }

    public void changePassword(User currentUser, NewPassword newPassword) throws InvalidPasswordException {
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
        currentUser.setPassword(argon2.hash(newPassword.getNewPassword()));
        update(currentUser);
    }

    private void updatePassword(User user, String hash) {
        new QueryExecutor<Void>(session -> {
            Query query = session.createQuery("update User set password = :hash where id " +
                    "= :userId")
                    .setParameter("hash", hash)
                    .setParameter("userId", user.getId());

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    public void addAttemptedPasswordChanges(int userId) {
        new QueryExecutor<Void>(session -> {
            String sql = "update User set attemptedPasswordChanges " +
                    "= attemptedPasswordChanges + 1 where id = :userId";
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId);

            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    public void resetAttemptedPasswordChanges(int userId) {
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
