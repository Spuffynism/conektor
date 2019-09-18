package xyz.ndlr.domain.password;

public interface PasswordHasher {
    /**
     * Verifies a password against a hash
     *
     * @param hash     password hash
     * @param password password
     * @return if the password matches the hash
     */
    boolean verify(HashedPassword hash, Password password);

    /**
     * Hashes a password
     *
     * @param password password to hash
     * @return the hashed password
     */
    HashedPassword hash(Password password);
}
