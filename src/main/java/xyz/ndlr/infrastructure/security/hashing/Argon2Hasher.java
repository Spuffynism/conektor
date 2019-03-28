package xyz.ndlr.infrastructure.security.hashing;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;
import xyz.ndlr.domain.password.HashedPassword;
import xyz.ndlr.domain.password.IPasswordHasher;
import xyz.ndlr.domain.password.Password;

import java.nio.charset.StandardCharsets;

@Service
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
    public boolean verify(HashedPassword hash, Password password) {
        return argon2Instance.verify(hash.getValue(), password.getValue(), StandardCharsets.UTF_8);
    }

    @Override
    public HashedPassword hash(Password password) {
        String hashedPassword = argon2Instance.hash(HASHING_ITERATIONS, KIBIBYTES_MEMORY_USAGE,
                NB_THREADS_AND_COMPUTES_LANES, password.getValue());
        return new HashedPassword(hashedPassword);
    }
}
