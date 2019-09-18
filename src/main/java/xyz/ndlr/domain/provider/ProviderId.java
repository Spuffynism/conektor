package xyz.ndlr.domain.provider;

public class ProviderId {
    private final long value;

    private ProviderId(long value) {
        this.value = value;
    }

    public static ProviderId from(long value) {
        return new ProviderId(value);
    }

    public long getValue() {
        return this.value;
    }
}
