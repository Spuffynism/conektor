package com.springmvc.service;

import com.springmvc.exception.EmailTakenException;
import com.springmvc.exception.InvalidPasswordException;
import com.springmvc.exception.UserNotFoundException;
import com.springmvc.exception.UsernameTakenException;
import com.springmvc.model.User;
import com.springmvc.security.auth.NewPassword;
import com.springmvc.security.hashing.Argon2Hasher;
import com.springmvc.security.hashing.IPasswordHasher;
import com.springmvc.service.database_util.AbstractService;
import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public User loadByIdentifier(String identifier) throws UserNotFoundException {
        User userByUsername = getByUsername(identifier);
        User userByEmail = getByEmail(identifier);

        if(userByUsername == null && userByEmail == null) {
            throw new UserNotFoundException("user not found");
        }

        User user = userByUsername == null ? userByEmail : userByUsername;
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

    private User getByEmail(String email) {
        return new QueryExecutor<>(session -> {
            return (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("email", email))
                    .uniqueResult();
        }).execute();
    }

    public void tryCreateNewUser(User dirtyUser) throws UsernameTakenException, EmailTakenException,
            InvalidPasswordException {
        if (getByUsername(dirtyUser.getUsername()) != null)
            throw new UsernameTakenException("This username is already taken.");

        if (getByEmail(dirtyUser.getEmail()) != null)
            throw new EmailTakenException("This email is already taken.");

        if (!NewPassword.matchesPolicy(dirtyUser.getPassword()))
            throw new InvalidPasswordException("This password is invalid.");

        User cleanUser = new User();
        cleanUser.setUsername(dirtyUser.getUsername());
        cleanUser.setEmail(dirtyUser.getUsername());

        IPasswordHasher argon2 = new Argon2Hasher();
        cleanUser.setPassword(argon2.hash(dirtyUser.getPassword()));

        add(cleanUser);
    }
}
