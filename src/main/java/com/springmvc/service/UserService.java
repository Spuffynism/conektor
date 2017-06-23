package com.springmvc.service;

import com.springmvc.model.User;
import com.springmvc.service.database_util.AbstractService;
import com.springmvc.service.database_util.QueryExecutor;
import com.springmvc.security.auth.NewPassword;
import com.springmvc.security.auth.exception.InvalidPasswordException;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
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
    private final AuthHolder authHolder;

    @Autowired
    public UserService(AuthHolder authHolder) {
        super(User.class);
        this.authHolder = authHolder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("utilisateur non trouvé");
        }

        detailsChecker.check(user);
        return user;
    }

    private User getByUsername(String username) {
        return new QueryExecutor<>(session -> {
            User user =  (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("username", username))
                    .uniqueResult();

            session.getTransaction().commit();

            return user;
        }).execute();
    }

    /**
     * Get l'utilisateur authentifié
     *
     * @return l'utilisateur authentifié
     */
    public User getAuthenticatedUser() {
        return (User) authHolder.getUser();
    }

    public void changerMotDePasse(NewPassword newPassword) throws InvalidPasswordException {
        User userCourant = getAuthenticatedUser();
        String hashMotDePasseCourant = userCourant.getPassword();
        IPasswordHasher argon2 = new Argon2Hasher();

        // Vérifie qu'on n'excède pas le nombre de tentatives de changement de mot de passe
        if (userCourant.getAttemptedPasswordChanges()
                > NewPassword.NB_MAX_TENTATIVES_CHANGEMENT_MOT_DE_PASSE)
            throw new InvalidPasswordException("Le nombre de tentatives de changement de mot de " +
                    "passe a été excédé.");

        // Vérifie que le nouveau mot de passe correspond à nos exigences en matière de sécurité
        if (!newPassword.complies())
            throw new InvalidPasswordException("Le nouveau mot de passe est non valide.");

        // Vérifie que l'user connaît son mot de passe (protection contre le vol de token de
        // session)
        boolean currentPasswordIsRight = argon2.verify(hashMotDePasseCourant,
                newPassword.getCurrentPassword());
        if (!currentPasswordIsRight)
            throw new InvalidPasswordException("Le mot de passe courant n'est pas le bon");

        userCourant.setPassword(argon2.hash(newPassword.getNewPassword()));
        modifier(userCourant);
    }

    private Void updatePassword(User user, String hash) {
        return new QueryExecutor<Void>(session -> {
            Query query = session.createQuery("update User set password = :hash where id " +
                    "= :userId")
                    .setParameter("hash", hash)
                    .setParameter("userId", user.getId());

            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    public Void AddAttemptedPasswordChanges(int userId) {
        return new QueryExecutor<Void>(session -> {
            String sql = "update User set attemptedPasswordChanges " +
                    "= attemptedPasswordChanges + 1 where id = :userId";
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId);

            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    public Void resetAttemptedPasswordChanges(int userId) {
        return new QueryExecutor<Void>(session -> {
            String sql = "update User set attemptedPasswordChanges = 0 where id = :userId";
            Query query = session.createQuery(sql)
                    .setParameter("userId", userId);

            query.executeUpdate();
            session.getTransaction().commit();

            return null;
        }).execute();
    }
}
