package xyz.ndlr.domain.user;

public class UserId {
    private final long value;

    private UserId(long value) {
        this.value = value;
    }

    public static UserId from(long value) {
        return new UserId(value);
    }

    public long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return value == userId.value;
    }
}
