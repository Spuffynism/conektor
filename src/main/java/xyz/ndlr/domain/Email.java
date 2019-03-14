package xyz.ndlr.domain;

public class Email {

    private final String value;

    public Email(String email) {
        this.value = email;
    }

    public String getValue() {
        return this.value;
    }
}
