package xyz.ndlr.domain;

public interface IPasswordChangeRepository {
    void updatePassword(int userId, String newPasswordHash);

    void addPasswordChangeAttempt(int userId);

    void resetPasswordChangeAttempts(int userId);
}
