package xyz.ndlr.security.auth;

import org.hibernate.Query;
import xyz.ndlr.repository.database_util.QueryExecutor;

import java.math.BigInteger;

public class PasswordChange {
    public static final int MAX_ALLOWED_PASSWORD_CHANGE_ATTEMPTS = 3;

    private String newPassword;
    private String newPasswordConfirmation;
    private String currentPassword;

    public PasswordChange(String newPassword) {
        this.setNewPassword(newPassword);
    }

    public PasswordChange(String newPassword, String newPasswordConfirmation) {
        this(newPassword);
        this.setNewPasswordConfirmation(newPasswordConfirmation);
    }

    public PasswordChange(String newPassword, String newPasswordConfirmation,
                          String currentPassword) {
        this(newPassword, newPasswordConfirmation);
        this.setCurrentPassword(currentPassword);
    }

    /**
     * Indique si le mot de passe et la confirmation correspondent et si le nouveau mot de passe
     * correspond aux exigences de la police de mots de passe.
     *
     * @return if the password complies
     */
    public boolean complies() {
        return newPassword.equals(newPasswordConfirmation) && matchesPolicy(newPassword);
    }

    /**
     * Selon les recommandations du NIST:
     * https://pages.nist.gov/800-63-3/sp800-63b.html
     *
     * @return if the password matches our password policy
     */
    public static boolean matchesPolicy(String password) {
        if (password.length() < 8) return false;
        if (password.length() > 255) return false;
        if (isCommonPassword(password)) return false;
        return true;
    }

    /**
     * Indicates if the password is a common password by comparing it to a common passwords table
     * in the database.
     *
     * @return si le mot de passe est commun
     */
    private static boolean isCommonPassword(String password) {
        return new QueryExecutor<>(session -> {
            String sql = "SELECT COUNT(*) FROM common_passwords_cpa WHERE cpa_password = " +
                    ":password LIMIT 1";
            Query query = session.createSQLQuery(sql);
            query.setParameter("password", password);

            // Si on a plus que 0 résultat, c'est que le mot de passe est commun
            return ((BigInteger) query.uniqueResult()).longValue() > 0;
        }).execute();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
