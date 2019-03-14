package xyz.ndlr.service;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.Email;
import xyz.ndlr.domain.Limit;
import xyz.ndlr.domain.exception.EmailTakenException;
import xyz.ndlr.domain.exception.UserNotFoundException;
import xyz.ndlr.domain.exception.UsernameTakenException;
import xyz.ndlr.domain.exception.password.InvalidPasswordException;
import xyz.ndlr.domain.user.IUserRepository;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.domain.user.Username;
import xyz.ndlr.security.auth.PasswordChange;
import xyz.ndlr.security.hashing.Argon2Hasher;
import xyz.ndlr.security.hashing.IPasswordHasher;

import java.util.List;

@Service
public class UserService {
    private final AccountStatusUserDetailsChecker detailsChecker
            = new AccountStatusUserDetailsChecker();
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> fetchAll(Limit limit) {
        //TODO(nich): Make use of limit
        return userRepository.getAll();
    }

    private User fetchByUsername(Username username) {
        return userRepository.get(username);
    }

    private User fetchByEmail(Email email) {
        return userRepository.get(email);
    }

    public User fetchById(UserId userId) throws UserNotFoundException {
        User user = userRepository.get(userId);

        if(user == null)
            throw new UserNotFoundException(userId);

        return user;
    }

    public void createNewUser(User dirtyUser) throws UsernameTakenException, EmailTakenException,
            InvalidPasswordException {
        if (fetchByUsername(dirtyUser.getUsername()) != null)
            throw new UsernameTakenException(dirtyUser.getUsername());

        if (fetchByEmail(dirtyUser.getEmail()) != null)
            throw new EmailTakenException(dirtyUser.getEmail());

        if (!PasswordChange.matchesPolicy(dirtyUser.getPassword()))
            throw new InvalidPasswordException();

        User cleanUser = new User();
        cleanUser.setUsername(dirtyUser.getUsername());
        cleanUser.setEmail(dirtyUser.getEmail());

        IPasswordHasher argon2 = new Argon2Hasher();
        cleanUser.setPassword(argon2.hash(dirtyUser.getPassword()));

        userRepository.add(cleanUser);
    }

    public void delete(UserId userid) throws UserNotFoundException {
        if(!userRepository.exists(userid))
            throw new UserNotFoundException(userid);

        userRepository.delete(userid);
    }
}
