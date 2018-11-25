package xyz.ndlr.domain.provider.facebook.sendAPI.message.attachment.template.button;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;

public class PostbackButton extends AbstractButton {
    // required
    @JsonProperty("title")
    @Max(20)
    private String title;
    // payload
    @JsonProperty("payload")
    @Max(1000)
    private String payload;

    public PostbackButton() {
        super(ButtonType.POSTBACK);
    }
}
