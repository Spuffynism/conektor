package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.button;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.TemplatePayload;
import xyz.ndlr.model.provider.facebook.sendAPI.message.attachment.template.TemplateType;
import xyz.ndlr.model.provider.facebook.sendAPI.message.button.AbstractButton;

import java.util.List;

public class ButtonTemplatePayload extends TemplatePayload {
    @JsonProperty("text")
    private String text;
    @JsonProperty("buttons")
    private List<AbstractButton> buttons;

    public ButtonTemplatePayload() {
        super(TemplateType.BUTTON);
    }
}
