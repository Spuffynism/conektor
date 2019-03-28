package xyz.ndlr.domain.password;

public class PasswordChange {
    public static final int MAX_ALLOWED_PASSWORD_CHANGE_ATTEMPTS = 3;

    private Password newPassword;
    private Password newPasswordConfirmation;
    private Password currentPassword;

    public PasswordChange(Password newPassword, Password newPasswordConfirmation,
                          Password currentPassword) {
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
        this.currentPassword = currentPassword;
    }

    public Password getNewPassword() {
        return newPassword;
    }

    public Password getCurrentPassword() {
        return currentPassword;
    }

    public boolean newPasswordMatchesConfirmation() {
        return newPassword.equals(newPasswordConfirmation);
    }

    public boolean newPasswordMatchesPolicyRequirements() {
        return newPassword.meetsPolicyRequirements();
    }

    public int getNewPasswordLength() {
        return this.newPassword.length();
    }

    public HashedPassword hash(IPasswordHasher passwordHasher) {
        return passwordHasher.hash(this.newPassword);
    }
}
