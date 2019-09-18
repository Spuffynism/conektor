package xyz.ndlr.infrastructure.provider.facebook.sendAPI.recipient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.domain.account.AccountToken;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient {
    // id or phoneNumber
    @JsonProperty("id")
    @NotNull
    private AccountToken id;
    // id or phoneNumber
    @JsonProperty("phone_number")
    private String phoneNumber;
    // Optional
    @JsonProperty("name")
    private Name name;

    public Recipient() {
    }

    // TODO(nich): Fix the AccountToken/Id relationship
    public Recipient(AccountToken id) {
        this.id = id;
    }

    public AccountToken getId() {
        return id;
    }

    public void setId(AccountToken id) {
        this.id = id;
    }
}
