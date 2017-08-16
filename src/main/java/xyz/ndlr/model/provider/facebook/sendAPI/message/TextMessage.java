package xyz.ndlr.model.provider.facebook.sendAPI.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class TextMessage extends Message {
    @JsonProperty("text")
    @Max(640)
    @NotNull
    private String text;

    public TextMessage(String text) {
        this.text = text;
    }
}
