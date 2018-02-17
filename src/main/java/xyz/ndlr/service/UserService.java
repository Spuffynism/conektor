package xyz.ndlr.service;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.ndlr.exception.EmailTakenException;
import xyz.ndlr.exception.InvalidPasswordException;
import xyz.ndlr.exception.UsernameTakenException;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.security.auth.PasswordChange;
import xyz.ndlr.security.hashing.Argon2Hasher;
import xyz.ndlr.security.hashing.IPasswordHasher;
import xyz.ndlr.service.database_util.AbstractService;
import xyz.ndlr.service.database_util.QueryExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
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
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("username"), username));

            return session.createQuery(criteria).getSingleResult();
        }).execute();
    }

    private User getByEmail(String email) {
        return new QueryExecutor<>(session -> {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get("email"), email));

            return session.createQuery(criteria).uniqueResult();
        }).execute();
    }

    public void tryCreateNewUser(User dirtyUser) throws UsernameTakenException, EmailTakenException,
            InvalidPasswordException {
        if (getByUsername(dirtyUser.getUsername()) != null)
            throw new UsernameTakenException("This username is already taken.");

        if (getByEmail(dirtyUser.getEmail()) != null)
            throw new EmailTakenException("This email is already taken.");

        if (!PasswordChange.matchesPolicy(dirtyUser.getPassword()))
            throw new InvalidPasswordException("This password is invalid.");

        User cleanUser = new User();
        cleanUser.setUsername(dirtyUser.getUsername());
        cleanUser.setEmail(dirtyUser.getUsername());

        IPasswordHasher argon2 = new Argon2Hasher();
        cleanUser.setPassword(argon2.hash(dirtyUser.getPassword()));

        add(cleanUser);
    }
}
