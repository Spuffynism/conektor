package com.springmvc.security.auth;

import com.springmvc.service.database_util.QueryExecutor;
import org.hibernate.Query;

import java.math.BigInteger;

public class NewPassword {
    public static final int NB_MAX_TENTATIVES_CHANGEMENT_MOT_DE_PASSE = 3;

    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirmation;

    public NewPassword(){}

    public NewPassword(String currentPassword, String newPassword, String newPasswordConfirmation) {
        this.setCurrentPassword(currentPassword);
        this.setNewPassword(newPassword);
        this.setNewPasswordConfirmation(newPasswordConfirmation);
    }

    /**
     * Indique si le mot de passe et la confirmation correspondent et si le nouveau mot de passe
     * correspond aux exigences de la police de mots de passe.
     *
     * @return si le mot de passe est conforme
     */
    public boolean complies() {
        // Valider que les mots de passe correspondent
        boolean passwordAndConfirmationAreTheSame = newPassword.equals(newPasswordConfirmation);

        return passwordAndConfirmationAreTheSame && newPasswordMatchesPolicy();
    }

    /**
     * Selon les recommandations du NIST:
     * https://pages.nist.gov/800-63-3/sp800-63b.html
     *
     * @return si le nouveau mot de passe correspont à notre police de mots de passe
     */
    private boolean newPasswordMatchesPolicy() {
        if(newPassword.length() < 8) return false;
        if(newPassword.length() > 255) return false;
        if(isCommonPassword()) return false;
        return true;
    }

    /**
     * Retourne si le mot de passe est un mot de passe commun en le comparant aux mots de passe
     * de la base de données.
     *
     * @return si le mot de passe est commun
     */
    private boolean isCommonPassword() {
        return new QueryExecutor<>(session -> {
            String sql = "SELECT COUNT(*) FROM common_passwords_cpa WHERE cpa_password = :password LIMIT 1";
            Query query = session.createSQLQuery(sql);
            query.setParameter("password", newPassword);

            // Si on a plus que 0 résultat, c'est que le mot de passe est commun
            return ((BigInteger)query.uniqueResult()).longValue() > 0;
        }).execute();
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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
}
