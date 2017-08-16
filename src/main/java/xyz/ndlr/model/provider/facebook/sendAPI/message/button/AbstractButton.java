package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractButton {
    // required
    @JsonProperty("type")
    private ButtonType buttonType;

    public AbstractButton(ButtonType buttonType) {
        this.buttonType = buttonType;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public void setButtonType(ButtonType buttonType) {
        this.buttonType = buttonType;
    }
}
