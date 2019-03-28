package xyz.ndlr.domain;

import xyz.ndlr.domain.password.HashedPassword;
import xyz.ndlr.domain.password.Password;
import xyz.ndlr.domain.user.UserId;

public interface IPasswordChangeRepository {
    void updatePassword(UserId userId, HashedPassword hashedPassword);

    int getUserPasswordChangeAttempts(UserId userId);

    void addPasswordChangeAttempt(UserId userId);

    void resetPasswordChangeAttempts(UserId userId);

    boolean isCommonPassword(Password password);
}
