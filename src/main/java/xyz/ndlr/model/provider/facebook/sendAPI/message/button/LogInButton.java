package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogInButton extends AbstractButton {

    @JsonProperty("url")
    private String url = "localhost:8443/login";

    LogInButton() {
        super(ButtonType.LOG_IN);
    }
}
