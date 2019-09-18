package xyz.ndlr.domain.user;

public class Username {

    private final String value;

    private Username(String value) {
        this.value = value;
    }

    public static Username from(String value) {
        return new Username(value);
    }

    public String getValue() {
        return this.value;
    }
}
