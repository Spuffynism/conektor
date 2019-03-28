package xyz.ndlr.domain.password;

import org.springframework.stereotype.Component;
import xyz.ndlr.domain.IPasswordChangeRepository;
import xyz.ndlr.domain.password.exception.*;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;

@Component
public class PasswordProducingService {

    private final IPasswordChangeRepository passwordChangeRepository;
    private final IPasswordHasher passwordHasher;

    public PasswordProducingService(IPasswordChangeRepository passwordChangeRepository,
                                    IPasswordHasher passwordHasher) {
        this.passwordChangeRepository = passwordChangeRepository;
        this.passwordHasher = passwordHasher;
    }

    public HashedPassword produceHashedPassword(User currentUser, PasswordChange passwordChange)
            throws PasswordChangeAttemptCountReachedException,
            NonCompliantPasswordException, NonMatchingCurrentPasswordException,
            InvalidPasswordLengthException {
        if (userExceedsPasswordChangeAttempts(currentUser.getId()))
            throw new PasswordChangeAttemptCountReachedException();

        if (!passwordChange.newPasswordMatchesConfirmation())
            throw new NonMatchingNewPasswordAndConfirmationException();

        // TODO(nich): Duplicated logic
        if (!passwordChange.newPasswordMatchesPolicyRequirements())
            throw new InvalidPasswordLengthException(passwordChange.getNewPasswordLength());

        if (passwordChangeRepository.isCommonPassword(passwordChange.getNewPassword()))
            throw new PasswordTooCommonException();

        // Checks that the user knows their password (protection against session token theft)
        if (!currentPasswordIsRight(currentUser.getPassword(), passwordChange.getCurrentPassword()))
            throw new NonMatchingCurrentPasswordException();

        // If all the checks pass, we change the password
        return passwordChange.hash(passwordHasher);
    }

    private boolean userExceedsPasswordChangeAttempts(UserId userId) {
        return passwordChangeRepository.getUserPasswordChangeAttempts(userId) >
                PasswordChange.MAX_ALLOWED_PASSWORD_CHANGE_ATTEMPTS;
    }

    private boolean currentPasswordIsRight(HashedPassword userPassword, Password currentPassword) {
        return passwordHasher.verify(userPassword, currentPassword);
    }
}
