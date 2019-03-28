package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.Email;
import xyz.ndlr.domain.IAuthHolder;
import xyz.ndlr.domain.Limit;
import xyz.ndlr.domain.exception.EmailTakenException;
import xyz.ndlr.domain.exception.UnauthorizedException;
import xyz.ndlr.domain.exception.UserNotFoundException;
import xyz.ndlr.domain.exception.UsernameTakenException;
import xyz.ndlr.domain.password.HashedPassword;
import xyz.ndlr.domain.password.IPasswordHasher;
import xyz.ndlr.domain.password.exception.InvalidPasswordLengthException;
import xyz.ndlr.domain.password.exception.NonCompliantPasswordException;
import xyz.ndlr.domain.user.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final IAuthHolder authHolder;
    private final UserFactory userFactory;
    private final IPasswordHasher passwordHasher;
    private final PasswordChangeService passwordChangeService;

    public UserService(IAuthHolder authHolder, IUserRepository userRepository,
                       UserFactory userFactory, IPasswordHasher passwordHasher,
                       PasswordChangeService passwordChangeService) {
        this.userRepository = userRepository;
        this.authHolder = authHolder;
        this.userFactory = userFactory;
        this.passwordHasher = passwordHasher;
        this.passwordChangeService = passwordChangeService;
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

        if (user == null)
            throw new UserNotFoundException(userId);

        return user;
    }

    public User createNewUser(UserCreationRequest userCreationRequest)
            throws UsernameTakenException,
            EmailTakenException, NonCompliantPasswordException {
        if (fetchByUsername(userCreationRequest.getUsername()) != null)
            throw new UsernameTakenException(userCreationRequest.getUsername());

        if (fetchByEmail(userCreationRequest.getEmail()) != null)
            throw new EmailTakenException(userCreationRequest.getEmail());

        // TODO(nich): Duplicated logic
        if (!userCreationRequest.getPassword().meetsPolicyRequirements())
            throw new InvalidPasswordLengthException(userCreationRequest.getPassword().length());

        HashedPassword hashedPassword = passwordHasher.hash(userCreationRequest.getPassword());
        User createdUser = userFactory.create(userCreationRequest, hashedPassword);

        userRepository.add(createdUser);

        return createdUser;
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

        if (!userRepository.exists(userid))
            throw new UserNotFoundException(userid);

        userRepository.delete(userid);
    }
}
