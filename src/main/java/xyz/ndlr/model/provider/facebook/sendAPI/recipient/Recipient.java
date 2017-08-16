package xyz.ndlr.model.provider.facebook.sendAPI.recipient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient {
    // id or phoneNumber
    @JsonProperty("id")
    @NotNull
    private String id;
    // id or phoneNumber
    @JsonProperty("phone_number")
    private String phoneNumber;
    // Optional
    @JsonProperty("name")
    private Name name;

    public Recipient(){}

    public Recipient(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
