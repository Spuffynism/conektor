package xyz.ndlr.infrastructure.security.hashing;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

public class Argon2Encoder implements PasswordEncoder {
    private static final int SALT_LENGTH = 32;
    private static final int HASH_OUTPUT_LENGTH = 64;
    private static final int HASHING_ITERATIONS = 10; // takes ~ 1 second to hash
    private static final int KIBIBYTES_MEMORY_USAGE = 65536;
    private static final int NB_THREADS_AND_COMPUTES_LANES = 4;

    private final Argon2 argon2Instance;

    public Argon2Encoder() {
        argon2Instance = Argon2Factory.create(SALT_LENGTH, HASH_OUTPUT_LENGTH);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return argon2Instance.hash(HASHING_ITERATIONS, KIBIBYTES_MEMORY_USAGE,
                NB_THREADS_AND_COMPUTES_LANES, rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return argon2Instance.verify(encodedPassword, rawPassword.toString(), StandardCharsets.UTF_8);
    }
}
