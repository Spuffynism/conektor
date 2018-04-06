package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractButton {
    // required
    @JsonProperty("type")
    private ButtonType type;

    AbstractButton(ButtonType type) {
        this.type = type;
    }

    public ButtonType getType() {
        return type;
    }

    public void setType(ButtonType type) {
        this.type = type;
    }
}
