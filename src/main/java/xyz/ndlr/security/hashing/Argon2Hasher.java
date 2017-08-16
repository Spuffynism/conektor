package xyz.ndlr.security.hashing;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.nio.charset.StandardCharsets;

public class Argon2Hasher implements IPasswordHasher {
    private static final int SALT_LENGTH = 32;
    private static final int HASH_OUTPUT_LENGTH = 64;
    private static final int HASHING_ITERATIONS = 10; // takes ~ 1 second to hash
    private static final int KIBIBYTES_MEMORY_USAGE = 65536;
    private static final int NB_THREADS_AND_COMPUTES_LANES = 4;

    private final Argon2 argon2Instance;

    public Argon2Hasher() {
        argon2Instance = Argon2Factory.create(SALT_LENGTH, HASH_OUTPUT_LENGTH);
    }

    @Override
    public boolean verify(String hash, String password) {
        return argon2Instance.verify(hash, password, StandardCharsets.UTF_8);
    }

    @Override
    public String hash(String password) {
        return argon2Instance.hash(HASHING_ITERATIONS, KIBIBYTES_MEMORY_USAGE,
                NB_THREADS_AND_COMPUTES_LANES, password);
    }
}
