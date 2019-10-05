package xyz.ndlr.infrastructure.provider.facebook;

public class Challenge {
    private final String value;

    private Challenge(String value) {
        this.value = value;
    }

    public static Challenge fromString(String challenge) {
        return new Challenge(challenge);
    }

    public String getValue() {
        return value;
    }
}
