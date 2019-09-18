package xyz.ndlr.domain.account;

public class AccountId {
    private final long value;

    private AccountId(long value) {
        this.value = value;
    }

    public static AccountId from(long value) {
        return new AccountId(value);
    }

    public long getValue() {
        return this.value;
    }
}
