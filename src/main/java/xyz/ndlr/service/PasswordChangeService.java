package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.AuthenticationHolder;
import xyz.ndlr.domain.PasswordChangeRepository;
import xyz.ndlr.domain.password.HashedPassword;
import xyz.ndlr.domain.password.PasswordChange;
import xyz.ndlr.domain.password.PasswordProducingService;
import xyz.ndlr.domain.password.exception.NonCompliantPasswordException;
import xyz.ndlr.domain.password.exception.NonMatchingCurrentPasswordException;
import xyz.ndlr.domain.password.exception.PasswordChangeAttemptCountReachedException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;

@Service
public class PasswordChangeService {

    private final PasswordChangeRepository passwordChangeRepository;
    private final PasswordProducingService passwordProducingService;
    private final AuthenticationHolder authHolder;

    public PasswordChangeService(PasswordChangeRepository passwordChangeRepository,
                                 PasswordProducingService passwordProducingService,
                                 AuthenticationHolder authHolder) {
        this.passwordChangeRepository = passwordChangeRepository;
        this.passwordProducingService = passwordProducingService;
        this.authHolder = authHolder;
    }

    public void changeCurrentUserPassword(PasswordChange passwordChange)
            throws NonCompliantPasswordException,
            PasswordChangeAttemptCountReachedException, NonMatchingCurrentPasswordException {
        User user = authHolder.getUser();
        UserId userId = user.getId();

        passwordChangeRepository.addPasswordChangeAttempt(userId);
        HashedPassword hashedPassword = passwordProducingService
                .produceHashedPassword(user, passwordChange);
        passwordChangeRepository.updatePassword(userId, hashedPassword);
        passwordChangeRepository.resetPasswordChangeAttempts(userId);
    }
}
