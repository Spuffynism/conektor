package xyz.ndlr.domain.provider;

public class ProviderId {
    private int value;

    private ProviderId(int value) {
        this.value = value;
    }

    public static ProviderId from(int i) {
        return new ProviderId(i);
    }

    public int getValue() {
        return this.value;
    }
}
