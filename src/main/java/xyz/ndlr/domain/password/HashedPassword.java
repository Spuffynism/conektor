package xyz.ndlr.domain.password;

public class HashedPassword {

    private String value;

    public HashedPassword(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
