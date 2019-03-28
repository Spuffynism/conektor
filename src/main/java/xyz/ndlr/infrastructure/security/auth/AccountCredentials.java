package xyz.ndlr.infrastructure.security.auth;

public class AccountCredentials {
    private String identifier;
    private String password;

    // necessary for login
    public AccountCredentials(){}

    public AccountCredentials(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
