package xyz.ndlr;

import xyz.ndlr.security.hashing.Argon2Encoder;

public class Miscellanious {
    public static void main (String[] args) {
        Argon2Encoder encoder = new Argon2Encoder();

        StringBuilder s = new StringBuilder();
        s.append("user");
        System.out.println(encoder.encode(s));
    }
}
