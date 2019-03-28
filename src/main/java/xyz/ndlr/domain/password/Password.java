package xyz.ndlr.domain.password;

public class Password {

    private String value;

    public Password(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int length() {
        return this.value.length();
    }

    public boolean meetsPolicyRequirements() {
        return value.length() >= 8 && value.length() <= 255;
    }
}
