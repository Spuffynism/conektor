package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.IPasswordChangeRepository;
import xyz.ndlr.domain.exception.password.NonCompliantPasswordException;
import xyz.ndlr.domain.exception.password.NonMatchingPasswordConfirmationException;
import xyz.ndlr.domain.exception.password.PasswordChangeAttemptCountReachedException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.security.auth.PasswordChange;
import xyz.ndlr.security.hashing.IPasswordHasher;

@Service
public class PasswordChangeService {

    private final IPasswordChangeRepository passwordChangeRepository;
    private final IPasswordHasher passwordHasher;

    public PasswordChangeService(IPasswordChangeRepository passwordChangeRepository,
                                 IPasswordHasher passwordHasher) {
        this.passwordChangeRepository = passwordChangeRepository;
        this.passwordHasher = passwordHasher;
    }

    public void changePassword(User user, PasswordChange passwordChange)
            throws NonCompliantPasswordException,
            PasswordChangeAttemptCountReachedException, NonMatchingPasswordConfirmationException {
        int userId = user.getId();

        passwordChangeRepository.addPasswordChangeAttempt(userId);
        actuallyChangePassword(user, passwordChange);
        passwordChangeRepository.resetPasswordChangeAttempts(userId);
    }

    private void actuallyChangePassword(User currentUser, PasswordChange passwordChange)
            throws PasswordChangeAttemptCountReachedException,
            NonCompliantPasswordException, NonMatchingPasswordConfirmationException {
        // Checks that we haven't exceeded the maximum allowed password change attempts
        if (currentUser.getAttemptedPasswordChanges() > PasswordChange
                .MAX_ALLOWED_PASSWORD_CHANGE_ATTEMPTS)
            throw new PasswordChangeAttemptCountReachedException();

        // Checks that the new password complies to our security requirements
        if (!passwordChange.complies())
            throw new NonCompliantPasswordException();

        // Checks that the user knows their password (protection against session token theft)
        boolean currentPasswordIsRight = passwordHasher.verify(currentUser.getPassword(),
                passwordChange.getCurrentPassword());
        if (!currentPasswordIsRight)
            throw new NonMatchingPasswordConfirmationException();

        // If all the checks pass, we change the password
        String hashedPassword = passwordHasher.hash(passwordChange.getNewPassword());
        passwordChangeRepository.updatePassword(currentUser.getId(), hashedPassword);
    }
}
