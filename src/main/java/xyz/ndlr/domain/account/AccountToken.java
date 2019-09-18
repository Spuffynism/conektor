package xyz.ndlr.domain.account;

public class AccountToken {
    private final String token;

    private AccountToken(String token) {
        this.token = token;
    }

    public static AccountToken from(String token) {
        return new AccountToken(token);
    }

    public String getValue() {
        return token;
    }
}
