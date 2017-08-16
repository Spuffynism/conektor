package xyz.ndlr.model.provider.facebook.sendAPI.recipient;

import com.fasterxml.jackson.annotation.JsonProperty;

class Name {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
}
