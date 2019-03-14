package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.Email;
import xyz.ndlr.domain.IAuthHolder;
import xyz.ndlr.domain.Limit;
import xyz.ndlr.domain.exception.EmailTakenException;
import xyz.ndlr.domain.exception.UnauthorizedException;
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
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final IAuthHolder authHolder;

    public UserService(IAuthHolder authHolder, IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.authHolder = authHolder;
    }

    public List<User> fetchAll(Optional<Limit> limit) throws UnauthorizedException {
        if (!authHolder.getUser().isAdmin())
            throw new UnauthorizedException();

        return userRepository.getAll(limit.orElse(Limit.DEFAULT));
    }

    public User fetchById(UserId userId) throws UserNotFoundException, UnauthorizedException {
        if (!authHolder.isMe(userId) && !authHolder.getUser().isAdmin())
            throw new UnauthorizedException();

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

    private User fetchByUsername(Username username) {
        return userRepository.get(username);
    }

    private User fetchByEmail(Email email) {
        return userRepository.get(email);
    }

    public void delete(UserId userid) throws UserNotFoundException, UnauthorizedException {
        if (!authHolder.getUser().isAdmin())
            throw new UnauthorizedException();

        if(!userRepository.exists(userid))
            throw new UserNotFoundException(userid);

        userRepository.delete(userid);
    }
}
