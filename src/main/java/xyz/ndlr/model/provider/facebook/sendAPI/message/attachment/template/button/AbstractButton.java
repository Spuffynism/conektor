package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.button;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://developers.facebook.com/docs/messenger-platform/send-messages/buttons
 */
public class AbstractButton {
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
