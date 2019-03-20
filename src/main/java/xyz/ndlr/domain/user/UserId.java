package xyz.ndlr.domain.user;

public class UserId {

    private final int value;

    private UserId(int value) {
        this.value = value;
    }

    public static UserId from(int i) {
        return new UserId(i);
    }

    public int getValue() {
        return this.value;
    }
}
