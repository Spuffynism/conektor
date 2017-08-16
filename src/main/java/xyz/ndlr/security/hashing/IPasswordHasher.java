package xyz.ndlr.security.hashing;

public interface IPasswordHasher {
    /**
     * Vérifie un mot de passe contre un hash.
     *
     * @param hash le hash
     * @param password le mot de passe
     * @return si le mot de passe match le hash
     */
    boolean verify(String hash, String password);

    /**
     * Hashes a password
     *
     * @param password le mot de passe à hasher
     * @return une string du mot de passe hashé
     */
    String hash(String password);
}
